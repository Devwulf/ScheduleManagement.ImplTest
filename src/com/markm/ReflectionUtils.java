package com.markm;

public class ReflectionUtils
{
    public static Class<?> wrapPrimitive(Class<?> type)
    {
        if (type.isPrimitive())
        {
            if (boolean.class.equals(type))
            {
                return Boolean.class;
            }
            else if (byte.class.equals(type))
            {
                return Byte.class;
            }
            else if (char.class.equals(type))
            {
                return Character.class;
            }
            else if (double.class.equals(type))
            {
                return Double.class;
            }
            else if (float.class.equals(type))
            {
                return Float.class;
            }
            else if (int.class.equals(type))
            {
                return Integer.class;
            }
            else if (long.class.equals(type))
            {
                return Long.class;
            }
            else if (short.class.equals(type))
            {
                return Short.class;
            }
            else if (void.class.equals(type))
            {
                return Void.class;
            }
            else
            {
                return type;
            }
        }
        else
            return type;
    }

    public static Object convertPrimitives(Object objToConvert, Class<?> convertTo)
    {
        if (objToConvert == null)
            throw new IllegalArgumentException("The objToConvert parameter cannot be null.");
        if (convertTo == null)
            throw new IllegalArgumentException("The convertTo parameter cannot be null.");

        Class<?> objClass = objToConvert.getClass();
        Class<?> convertToClass = wrapPrimitive(convertTo);
        if (!isPrimitiveOrWrapper(convertToClass))
        {
            return objToConvert;
        }

        // It seems that converting the object to String first then to
        // the given class can be up to 2x slower, although it's less
        // code to type. However, the time it usually takes to do this
        // is still well below 1 millisecond (0.02 to 0.03 ms).
        if (convertToClass.equals(Integer.class))
        {
            return Integer.valueOf(objToConvert.toString());
            //return (Integer) ((Long) objToConvert).intValue();
        }
        else if (convertToClass.equals(Long.class))
        {
            return Long.valueOf(objToConvert.toString());
            //return (Long) ((Integer) objToConvert).longValue();
        }
        else if (convertToClass.equals(Boolean.class))
        {
            return Boolean.valueOf(objToConvert.toString());
        }
        else if (convertToClass.equals(String.class))
        {
            return objToConvert.toString();
        }
        else
        {
            System.out.println("The conversion from '" + objClass.getName() + "' to '" + convertToClass.getName() + "' is not implemented yet.");
            return objToConvert;
        }
    }

    public static boolean isPrimitiveOrWrapper(Class<?> type)
    {
        return type.isPrimitive() ||
                type.equals(Boolean.class) ||
                type.equals(Byte.class) ||
                type.equals(Character.class) ||
                type.equals(Double.class) ||
                type.equals(Float.class) ||
                type.equals(Integer.class) ||
                type.equals(Long.class) ||
                type.equals(Short.class) ||
                type.equals(Void.class);
    }
}
