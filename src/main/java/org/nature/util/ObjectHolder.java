package org.nature.util;

import java.util.concurrent.ConcurrentHashMap;

public class ObjectHolder {
    private static final ConcurrentHashMap<String, Object> pool = new ConcurrentHashMap<>();

    public static void put(String key, Object val) {
        pool.put(key, val);
    }

    public static <T> T get(String key, Class<T> clazz) {
        final Object val = pool.get(key);
        return ObjUtil.convert(clazz, val);
    }

    public static Object get(String key) {
        return pool.get(key);
    }

    public static boolean exists(String key) {
        return pool.containsKey(key);
    }
}
