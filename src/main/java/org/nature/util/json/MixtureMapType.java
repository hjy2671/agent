package org.nature.util.json;

import org.nature.util.ObjUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.nature.util.json.AbstractJsonParser.LEFT_BRACE;

/**
 * @author hjy
 * 2023/3/25
 */
public class MixtureMapType extends AbstractType {

    private final Map<String, JsonFinder.GenericType> types;

    public MixtureMapType(Map<String, JsonFinder.GenericType> map){
        super(null, null, null);
        this.types = map;
    }

    @Override
    public Object parse(Stack<AbstractType> typeStack, Stack<Object> nameStack, Stack<Object> dataStack, Stack<Character> symbolStack) {
        final HashMap<String, Object> map = new HashMap<>();
        while (!dataStack.peek().equals(LEFT_BRACE)) {
            final String key = nameStack.pop().toString();
            map.put(key, ObjUtil.convert(types.get(key).getType(), dataStack.pop()));
        }
        dataStack.pop();
        symbolStack.pop();
        return map;
    }
}
