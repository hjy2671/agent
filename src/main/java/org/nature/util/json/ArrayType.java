package org.nature.util.json;

import org.nature.util.ObjUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Stack;

import static org.nature.util.json.AbstractJsonParser.*;

public class ArrayType extends AbstractType{

    public ArrayType(Class<?> type, Field field) {
        super(type, field, null);
    }

    @Override
    public Object parse(Stack<AbstractType> typeStack, Stack<Object> nameStack, Stack<Object> dataStack, Stack<Character> symbolStack) {
        final Class<?> componentType = type.getComponentType();
        final int depth = dataStack.size() - dataStack.lastIndexOf(LEFT_BRACKET) - 1;
        final Object array = Array.newInstance(componentType, depth);
        for (int i = depth - 1; i >= 0; i--) {
            Array.set(array, i, ObjUtil.convert(componentType, dataStack.pop()));
        }
        dataStack.pop();
        symbolStack.pop();
        return array;
    }

    @Override
    public Class<?> getInnerType(String name) {
        return type.getComponentType();
    }

    @Override
    public Field getInnerField(String name) {
        return null;
    }
}
