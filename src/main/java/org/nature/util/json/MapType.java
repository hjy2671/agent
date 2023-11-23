package org.nature.util.json;

import org.nature.util.ObjUtil;
import org.nature.util.ReflectUtil;
import org.nature.util.json.exception.GenericClassNotFoundException;

import java.lang.reflect.Field;
import java.util.*;

import static org.nature.util.json.AbstractJsonParser.LEFT_BRACE;

public class MapType extends AbstractGenericType{

    private Class<?> cacheInnerType;
    private Class<?> cacheKeyType;

    public MapType(Class<?> type, Field field, AbstractTypePackage typePackage) {
        super(type, field, typePackage);
    }

    @Override
    public Object parse(Stack<AbstractType> typeStack, Stack<Object> nameStack, Stack<Object> dataStack, Stack<Character> symbolStack) {
        if (cacheKeyType == null)
            getInnerType(null);
        Map<Object, Object> map = null;
        if (ReflectUtil.isAbstract(type)) {
            map = new HashMap<>();
        } else {
            map = ObjUtil.convert(new HashMap<>(), ReflectUtil.newInstance(type));
        }
        while (!dataStack.peek().equals(LEFT_BRACE)) {
            map.put(ObjUtil.convert(cacheKeyType, nameStack.pop()), ObjUtil.convert(cacheInnerType, dataStack.pop()));
        }
        dataStack.pop();
        symbolStack.pop();
        return map;
    }


    @Override
    public Class<?> getInnerType(String name) {
        final String genericType = typePackage.getGenericType();
        final int end = genericType.indexOf('<');
        final int midStart = genericType.indexOf(',');

        try {
            if (midStart > 0) {
                String[] kv = null;
                if (end < 0) {
                    kv = genericType.split(",");
                } else {
                    kv = genericType.substring(0, end).split(",");
                }
                cacheKeyType = Class.forName(kv[0]);
                return cacheInnerType = Class.forName(kv[1].trim());
            } else {
                cacheKeyType = Class.forName(AbstractGenericType.DEFAULT_GENERIC);
                return cacheInnerType = Class.forName(AbstractGenericType.DEFAULT_GENERIC);
            }
        } catch (ClassNotFoundException e) {
            throw new GenericClassNotFoundException("找不到泛型类: ".concat(genericType));
        }
    }

    @Override
    public Field getInnerField(String name) {
        return null;
    }
}
