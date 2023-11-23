package org.nature.util;

import com.sun.jdi.InternalException;
import org.nature.net.interceptor.PreInterceptor;
import org.nature.util.json.exception.GenericClassNotFoundException;

import java.lang.reflect.*;
import java.util.Locale;

public final class ReflectUtil {

    private static final String SET_METHOD_PREFIX = "set";

    public static  <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, null);
    }

    public static  <T> T newInstance(Class<T> clazz, Object[] args) {
        Constructor<T> constructor = null;
        T val = null;
        try {
            constructor  = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            val = constructor.newInstance(args);
        } catch (NoSuchMethodException e) {
            throw new InternalException("没有构造方法 " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new InternalException("调用方法异常 " + e.getMessage());
        } catch (InstantiationException e) {
            throw new InternalException("该对象无法实例化 " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new InternalException("非法访问 " + e.getMessage());
        }
        return val;
    }

    public static Field getField(Class<?> c, String name) {
        try {
            return c.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("没有字段");
    }

    public static void setValue(Object target, String paramName, Class<?>[] paramType, Object... val) {
        try {
            final Method method = target.getClass()
                    .getDeclaredMethod(SET_METHOD_PREFIX.concat(StrUtil.firstToUp(paramName)), paramType);
            method.setAccessible(true);
            method.invoke(target, val);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setValue(Object target, String paramName, Class<?> paramType, Object... val) {
        setValue(target, paramName, new Class[]{paramType}, val);
    }

    public static boolean isAbstract(Class<?> clazz) {
        return clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers());
    }

    public static Class<?> getFirstGenericClass(String genericString) {
        final String name = genericString.substring(0, genericString.indexOf('<'));
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new GenericClassNotFoundException("找不到泛型类: ".concat(name));
        }
    }
}
