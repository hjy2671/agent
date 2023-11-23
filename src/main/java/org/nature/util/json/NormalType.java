package org.nature.util.json;

import org.nature.util.ObjUtil;
import org.nature.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Stack;
import static org.nature.util.json.AbstractJsonParser.*;

public class NormalType extends AbstractType{
    public NormalType(Class<?> type, Field field) {
        super(type, field, null);
    }

    @Override
    public Object parse(Stack<AbstractType> typeStack, Stack<Object> nameStack, Stack<Object> dataStack, Stack<Character> symbolStack) {
        final Object instance = ReflectUtil.newInstance(type);
        while (!dataStack.peek().equals(LEFT_BRACE)) {
            final String name = nameStack.pop().toString();
            final Class<?> type = this.getInnerType(name);
            final Object data = dataStack.pop();
            ReflectUtil.setValue(instance, name, type, type.isPrimitive() ? ObjUtil.convert(type, data) : data);
        }
        dataStack.pop();
        symbolStack.pop();
        return instance;
    }


}
