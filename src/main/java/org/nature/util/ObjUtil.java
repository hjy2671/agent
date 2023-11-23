package org.nature.util;

import com.sun.jdi.InternalException;
import org.nature.net.exception.FormatObjectException;

import java.util.List;
import java.util.Map;


public class ObjUtil {

    private final static String INT_TYPE_NAME = "int";
    private final static String BYTE_TYPE_NAME = "byte";
    private final static String SHORT_TYPE_NAME = "short";
    private final static String LONG_TYPE_NAME = "long";
    private final static String BOOLEAN_TYPE_NAME = "boolean";
    private final static String FLOAT_TYPE_NAME = "float";
    private final static String DOUBLE_TYPE_NAME = "double";
    private final static String CHAR_TYPE_NAME = "char";


    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> c, Object val) {
        if (val == null)
            return null;
        if (isPrimeWrapperType(c) && val instanceof String) {
            return (T)convertPrimeToWrapValue(c, val.toString());
        }
        if (c.isPrimitive())
            return (T) convertPrimeType(c, val.toString());
        if (c.isAssignableFrom(val.getClass())) {
            return (T) val;
        }
        throw new FormatObjectException("类型转换异常: ".concat(val.toString()).concat(" to ").concat(c.getName()));
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> convert(List<T> c, Object val) {
        return ((List<T>) val);
    }

    @SuppressWarnings("unchecked")
    public static <K,V> Map<K,V> convert(Map<K,V> c, Object val) {
        return ((Map<K,V>) val);
    }

    public static boolean isPrimeWrapperType(Class<?> c) {
        return c == Integer.class || c == Double.class || c == Long.class || c == Byte.class || c == Boolean.class || c == Float.class || c == Character.class || c == Short.class;
    }

    public static Object convertPrimeToWrapValue(Class<?> c, String val) {
        if (Integer.class.equals(c)) {
            return new Integer(val);
        } else if (Byte.class.equals(c)) {
            return new Byte(val);
        } else if (Short.class.equals(c)) {
            return new Short(val);
        } else if (Long.class.equals(c)) {
            return new Long(val);
        } else if (Boolean.class.equals(c)) {
            return Boolean.valueOf(val);
        } else if (Float.class.equals(c)) {
            return Float.valueOf(val);
        } else if (Double.class.equals(c)) {
            return Double.valueOf(val);
        }
        return val.charAt(0);
    }

    public static Object convertPrimeType(String className, String val) {
        try {
            switch (className) {
                case INT_TYPE_NAME:
                    return Integer.parseInt(val);
                case BYTE_TYPE_NAME:
                    return Byte.parseByte(val);
                case SHORT_TYPE_NAME:
                    return Short.parseShort(val);
                case LONG_TYPE_NAME:
                    return Long.parseLong(val);
                case BOOLEAN_TYPE_NAME:
                    return Boolean.parseBoolean(val);
                case FLOAT_TYPE_NAME:
                    return Float.parseFloat(val);
                case DOUBLE_TYPE_NAME:
                    return Double.parseDouble(val);
                default:
                    return val.charAt(0);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("数据类型转换异常：".concat(val).concat(" to ").concat(className));
        }
    }

    public static Object convertPrimeType(Class<?> c, String val) {
        return convertPrimeType(c.getSimpleName(), val);
    }

    public static String getDescriptor(Class<?> c) {
        if (c.isArray()) {
            return StrUtil.packagePath2filePath(c.getName());
        }
        return "L".concat(StrUtil.packagePath2filePath(c.getName())).concat(";");
    }

    public static Class<?> getPrimitiveWrapClass(Class<?> c) {
        switch (c.getSimpleName()) {
            case INT_TYPE_NAME:
                return Integer.class;
            case BYTE_TYPE_NAME:
                return Byte.class;
            case SHORT_TYPE_NAME:
                return Short.class;
            case LONG_TYPE_NAME:
                return Long.class;
            case BOOLEAN_TYPE_NAME:
                return Boolean.class;
            case FLOAT_TYPE_NAME:
                return Float.class;
            case DOUBLE_TYPE_NAME:
                return Double.class;
            case CHAR_TYPE_NAME:
                return Character.class;
        }
        throw new InternalException("不是基础数据类型");
    }

    public static boolean isIntType(Class<?> c) {
        return INT_TYPE_NAME.equals(c.getSimpleName());
    }

    public static boolean isByteType(Class<?> c) {
        return BYTE_TYPE_NAME.equals(c.getSimpleName());
    }

    public static boolean isShortType(Class<?> c) {
        return SHORT_TYPE_NAME.equals(c.getSimpleName());
    }

    public static boolean isLongType(Class<?> c) {
        return LONG_TYPE_NAME.equals(c.getSimpleName());
    }

    public static boolean isBooleanType(Class<?> c) {
        return BOOLEAN_TYPE_NAME.equals(c.getSimpleName());
    }

    public static boolean isFloatType(Class<?> c) {
        return FLOAT_TYPE_NAME.equals(c.getSimpleName());
    }

    public static boolean isDoubleType(Class<?> c) {
        return DOUBLE_TYPE_NAME.equals(c.getSimpleName());
    }

    public static boolean isCharType(Class<?> c) {
        return CHAR_TYPE_NAME.equals(c.getSimpleName());
    }

}
