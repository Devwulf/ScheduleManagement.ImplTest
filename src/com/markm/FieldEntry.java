package com.markm;

public class FieldEntry
{
    private String fieldName;
    private Class<?> fieldType;
    private String columnName;

    private boolean isPrimaryKey;
    private boolean isAutoGen;

    private boolean isForeignKey;
    private String foreignFieldName;

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public Class<?> getFieldType()
    {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType)
    {
        this.fieldType = fieldType;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public boolean isPrimaryKey()
    {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey)
    {
        isPrimaryKey = primaryKey;
    }

    public boolean isAutoGen()
    {
        return isAutoGen;
    }

    public void setAutoGen(boolean autoGen)
    {
        isAutoGen = autoGen;
    }

    public boolean isForeignKey()
    {
        return isForeignKey;
    }

    public void setForeignKey(boolean foreignKey)
    {
        isForeignKey = foreignKey;
    }

    public String getForeignFieldName()
    {
        return foreignFieldName;
    }

    public void setForeignFieldName(String foreignFieldName)
    {
        this.foreignFieldName = foreignFieldName;
    }
}
