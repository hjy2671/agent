package org.nature.util.json;

import org.nature.util.ObjUtil;
import org.nature.util.ReflectUtil;
import org.nature.util.json.exception.GenericClassNotFoundException;

import java.lang.reflect.Field;
import java.util.*;

import static org.nature.util.json.AbstractJsonParser.LEFT_BRACKET;

public class ListType extends AbstractGenericType {
    private Class<?> cacheInnerType;
    public ListType(Class<?> type, Field field, AbstractTypePackage typePackage) {
        super(type, field, typePackage);
    }

    @Override
    public Object parse(Stack<AbstractType> typeStack, Stack<Object> nameStack, Stack<Object> dataStack, Stack<Character> symbolStack) {
        final int depth = dataStack.size() - dataStack.lastIndexOf(LEFT_BRACKET) - 1;

        List<Object> list = null;
        if (ReflectUtil.isAbstract(type)) {
            list = new ArrayList<>();
        } else {
            list = ObjUtil.convert(new ArrayList<>(), ReflectUtil.newInstance(type));
        }
        for (int i = depth - 1; i >= 0; i--) {
            list.add(ObjUtil.convert(cacheInnerType, dataStack.pop()));
        }
        dataStack.pop();
        symbolStack.pop();
        return list;
    }


    @Override
    public Class<?> getInnerType(String name) {
        final String genericType = typePackage.getGenericType();
        final int end = genericType.indexOf('<');

        try {
            if (end > 0) {
                return cacheInnerType = Class.forName(genericType.substring(0, end));
            } else {
                return cacheInnerType = Class.forName(genericType);
            }
        } catch (ClassNotFoundException e) {
            throw new GenericClassNotFoundException("找不到泛型类: ".concat(genericType));
        }
    }

    @Override
    public Field getInnerField(String name) {
        return null;
    }

    @Override
    public AbstractTypePackage getTypePackage() {
        return super.getTypePackage();
    }
}
