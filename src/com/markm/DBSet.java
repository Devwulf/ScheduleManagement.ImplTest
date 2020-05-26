package com.markm;

import com.markm.Annotations.*;
import com.markm.Exceptions.IllegalEntityKeyFormatException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBSet<TEntity>
{
    private Class<TEntity> classType;

    private String tableName;

    private ArrayList<FieldEntry> fields;

    public DBSet(Class<TEntity> clazz)
    {
        classType = clazz;
        fields = new ArrayList<>();

        // TODO: Check if the entity class model matches the entity model in the database

        Table tableAttr = classType.getAnnotation(Table.class);
        if (tableAttr == null)
        {
            // All entity classes need a Table annotation to specify the
            // table name in the database that this entity goes to
            throw new IllegalArgumentException("The given entity class needs a Table annotation specifying a table name in the database.");
        }

        tableName = tableAttr.name();

        boolean hasKey = false;
        Field[] declaredFields = classType.getDeclaredFields();
        for (Field field : declaredFields)
        {
            Exclude excludeAttr = field.getAnnotation(Exclude.class);
            if (excludeAttr != null)
                continue;

            FieldEntry entry = new FieldEntry();

            String fieldName = field.getName();
            entry.setFieldName(fieldName);
            entry.setFieldType(ReflectionUtils.wrapPrimitive(field.getType()));

            Column columnAttr = field.getAnnotation(Column.class);
            if (columnAttr != null)
                entry.setDbName(columnAttr.name());
            else
                entry.setDbName(fieldName);

            Key keyAttr = field.getAnnotation(Key.class);
            if (keyAttr != null)
            {
                entry.setPrimaryKey(true);
                entry.setAutoGen(keyAttr.isAutoGen());

                if (!hasKey)
                    hasKey = true;
            }
            else
                entry.setPrimaryKey(false);

            ForeignKey foreignKeyAttr = field.getAnnotation(ForeignKey.class);
            if (foreignKeyAttr != null)
            {
                entry.setForeignKey(true);
                // TODO: Check if the entity referenced here is also a valid entity (has Table attr)
                entry.setForeignFieldName(foreignKeyAttr.fieldName());
            }
            else
            {
                entry.setForeignKey(false);
                entry.setForeignFieldName("");
            }

            fields.add(entry);
        }

        if (!hasKey)
        {
            // This entity class has no primary key, and is therefore invalid
            throw new IllegalArgumentException("The given entity class does not have a set primary key.");
        }
    }

    public TEntity createEntity(TEntity entity)
    {
        try
        {
            Connection conn = ConnectionManager.instance()
                                               .getConnection();

            // Build the SQL query for inserting the entity instance into the database
            StringBuilder query = new StringBuilder("insert into " + tableName + " values (");
            for (int i = 0; i < fields.size() - 1; i++)
            {
                query.append("?, ");
            }
            query.append("?)");

            try (PreparedStatement statement = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS))
            {
                StringBuilder values = new StringBuilder("(");
                for (int i = 0; i < fields.size(); i++)
                {
                    // This will read all the fields of the given object
                    // and replace the ? in the SQL query.
                    FieldEntry entry = fields.get(i);
                    Field field = entity.getClass()
                                        .getDeclaredField(entry.getFieldName());
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    statement.setObject(i + 1, value);

                    // I thought PreparedStatement didn't have an all-purpose setValue
                    //PreparedStatementExtension.setValue(statement, i + 1, value);

                    // For logging the values used in the query
                    if (i >= fields.size() - 1)
                        values.append("'")
                              .append(value.toString())
                              .append("')");
                    else
                        values.append("'")
                              .append(value.toString())
                              .append("', ");
                }

                statement.executeUpdate();

                // There should be only one identity key
                ResultSet result = statement.getGeneratedKeys();
                int count = result.getMetaData()
                                  .getColumnCount();

                if (count == 1 && result.next())
                {
                    // Get the autogenerated primary key fields in the object
                    ArrayList<FieldEntry> entries = fields.stream()
                                                          .filter(fieldEntry -> fieldEntry.isPrimaryKey() && fieldEntry.isAutoGen())
                                                          .collect(Collectors.toCollection(ArrayList::new));
                    if (entries.size() != 1)
                        throw new IllegalEntityKeyFormatException("The entity class '" + classType.getName() + "' has either multiple auto generated keys or no auto generated keys when the database has one autogenerated key.");

                    // Set the autogenerated key from the database to the object's primary key
                    Object resultObj = result.getObject(1);
                    FieldEntry entry = entries.get(0);
                    Field field = entity.getClass()
                                        .getDeclaredField(entry.getFieldName());
                    field.setAccessible(true);
                    field.set(entity, ReflectionUtils.convertPrimitives(resultObj, entry.getFieldType()));
                }

                // TODO: Also print out the new autogenerated key
                System.out.println("Entity of type '" + classType.getName() + "' created in database successfully!\nQuery values: " + values.toString());

                return entity;
            }
        }
        catch (SQLException | NoSuchFieldException | IllegalAccessException | IllegalEntityKeyFormatException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    // Takes in column name/column value pairs
    public List<TEntity> readEntity(List<NameValuePair> columnValues)
    {
        if (columnValues == null || columnValues.isEmpty())
            throw new IllegalArgumentException("The columnValues list parameter cannot be null or empty.");

        // TODO: Check each given column name against the field entries above

        Connection conn = ConnectionManager.instance()
                                           .getConnection();

        StringBuilder query = new StringBuilder("select * from " + tableName + " where ");
        for (int i = 0; i < columnValues.size(); i++)
        {
            NameValuePair pair = columnValues.get(i);

            // For the first element
            if (i <= 0)
                query.append(pair.getName())
                     .append("=? ");
            else
                query.append("and ")
                     .append(pair.getName())
                     .append("=? ");
        }

        System.out.println(query.toString());
        try (PreparedStatement statement = conn.prepareStatement(query.toString()))
        {
            StringBuilder values = new StringBuilder("{ ");
            for (int i = 0; i < columnValues.size(); i++)
            {
                NameValuePair pair = columnValues.get(i);
                statement.setObject(i + 1, pair.getValue());

                // For logging purposes
                if (i >= columnValues.size() - 1)
                    values.append(pair.getName())
                          .append(" = '")
                          .append(pair.getValue()
                                      .toString())
                          .append("' }");
                else
                    values.append(pair.getName())
                          .append(" = '")
                          .append(pair.getValue()
                                      .toString())
                          .append("', ");
            }

            ResultSet result = statement.executeQuery();
            List<TEntity> entities = new ArrayList<>();
            while (result != null && result.next())
            {
                // TODO: Use reflection to assign the read values to the entity

                TEntity entity = classType.newInstance();
                for (FieldEntry entry : fields)
                {
                    Field field = entity.getClass().getDeclaredField(entry.getFieldName());
                    field.setAccessible(true);
                    field.set(entity, ReflectionUtils.convertPrimitives(result.getObject(entry.getDbName()), entry.getFieldType()));
                }

                entities.add(entity);
            }

            System.out.println("Entity of type '" + classType.getName() + "' read from database successfully!\nQuery values: " + values.toString());

            return entities;
        }
        catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
