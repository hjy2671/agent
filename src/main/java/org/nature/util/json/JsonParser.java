package org.nature.util.json;


import org.nature.util.ObjUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser extends AbstractJsonParser {


    /**
     *
     * @param json json
     * @param target 解析的目标类型，无法解析类的泛型，但支持字段上的泛型
     * @param typeFactory type的工厂类
     */
    public JsonParser(String json, Class<?> target, AbstractTypeFactory typeFactory) {
        super(json, target, typeFactory);
    }

    public JsonParser(String json, AbstractTypeFactory typeFactory) {
        super(json, typeFactory);
    }

    /**
     * 不支持复杂的泛型嵌套
     * @param listGenericType list的泛型类型
     */
    public <T> List<T> parseList(Class<T> listGenericType) {
        return ObjUtil.convert(new ArrayList<>(), parse(typeStack.peek().getType().getTypeName().concat("<").concat(listGenericType.getName()).concat(">")));
    }

    /**
     * 不支持复杂的泛型嵌套
     * @param mapGenericKeyType map的key的泛型类型
     * @param mapGenericValType map的value的泛型类型
     */
    public <K,V> Map<K,V> parseMap(Class<K> mapGenericKeyType, Class<V> mapGenericValType) {
        final Object parse = parse(typeStack.peek().getType().getTypeName()
                .concat("<")
                .concat(mapGenericKeyType.getName())
                .concat(",")
                .concat(mapGenericValType.getName())
                .concat(">"));
        return ObjUtil.convert(new HashMap<>(), parse);
    }

    /**
     *  此方法支持复杂的泛型嵌套
     * @param genericString 泛型字符串比如java.util.List<java.util.map<java.lang.String,java.util.List<java.lang.String>>>
     */
    public Object parse(String genericString) {
        final AbstractType type = typeStack.peek();
        final AbstractTypePackage typePackage = new TypePackage();
        typePackage.initGenericType(genericString);
        type.setTypePackage(typePackage);
        return parse();
    }

    /**
     *  此方法支持复杂的泛型嵌套
     * @param genericType 携带泛型信息的type
     */
    public Object parse(Type genericType) {
        return parse(genericType.getTypeName());
    }

    protected char parsePrimary(char current) {
        if (isNumOrDotOrMinus(current)) {
            final StringBuilder tempBuilder = new StringBuilder();
            tempBuilder.append(current);
            while (index < len && isNumOrDotOrMinus(current = json.charAt(++index)))
                tempBuilder.append(current);
            putData(tempBuilder.toString());
        }

        if (isLetter(current)) {
            final StringBuilder tempBuilder = new StringBuilder();
            tempBuilder.append(current);
            while (index < len && isLetter(current = json.charAt(++index)))
                tempBuilder.append(current);
            putData(tempBuilder.toString());
        }

        if (isDoubleQuote(current)) {
            final StringBuilder builder = new StringBuilder();
            while (index < len && !isDoubleQuote(current = json.charAt(++index)))
                builder.append(current);
            current = json.charAt(++index);
            putCurrent(builder.toString());
        }
        return current;
    }

    protected Object parseRight() {
        return getType().parse(typeStack, nameStack, dataStack, symbolStack);
    }

    protected void parseColon() {
        currentStack = dataStack;
    }

    protected void parseComa() {
        if (symbolStack.peek() == LEFT_BRACE) {
            currentStack = nameStack;
        } else if (symbolStack.peek() == LEFT_BRACKET) {
            currentStack = dataStack;
        }
    }

    protected void parseLeft(char symbol) {
        selectStack(symbol);
        putData(symbol);
        if (symbolStack.isEmpty()) {
            putSymbol(symbol);
            return;
        }
        putSymbol(symbol);
        final String name = getNameFromStack();
        final Field field = typeStack.peek().getInnerField(name);
        Class<?> type = typeStack.peek().getInnerType(name);
        putType(typeFactory.createType(type, field, typeStack.peek().getTypePackage()));
        putData(parse());
    }

    protected void selectStack(char symbol) {
        currentStack = symbol == LEFT_BRACE ? nameStack : dataStack;
    }

    protected String getNameFromStack() {
        if (nameStack.isEmpty())
            return null;
        return nameStack.peek().toString();
    }

}
