package org.nature.util;

import com.sun.jdi.InternalException;

import java.util.Hashtable;

public class ByteArrayClassLoader extends ClassLoader {
    public static final ByteArrayClassLoader loader = new ByteArrayClassLoader();
    private final Hashtable<String, Class<?>> cache = new Hashtable<>();
    private byte[] bytes;
    public ByteArrayClassLoader()
    {
        super(ByteArrayClassLoader.class.getClassLoader());
    }

    public Class<?> loadClass(String className, byte[] bytes) throws ClassNotFoundException {
        if (bytes == null)
            throw new InternalException("");
        this.bytes = bytes;
        return this.loadClass(className);
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return super.loadClass(className);
    }

    public Class<?> findClass(String className) {
        Class<?> result = cache.get(className);
        if (result != null) {
            return result;
        }
        result = defineClass(className, bytes, 0, bytes.length, null);
        cache.put(className, result);
        clear();
        return result;
    }

    private void clear(){
        this.bytes = null;
    }
}
