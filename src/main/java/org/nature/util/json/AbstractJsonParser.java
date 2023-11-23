package org.nature.util.json;

import org.nature.util.json.exception.JsonParseException;

import java.util.Stack;

public abstract class AbstractJsonParser {

    protected final Stack<Object> nameStack = new Stack<>();
    protected final Stack<Object> dataStack = new Stack<>();
    protected final Stack<AbstractType> typeStack = new Stack<>();
    protected final Stack<Character> symbolStack = new Stack<>();

    protected Stack<Object> currentStack;
    protected AbstractTypeFactory typeFactory;

    public final static char LEFT_BRACE = '{';
    public final static char RIGHT_BRACE = '}';
    public final static char LEFT_BRACKET = '[';
    public final static char RIGHT_BRACKET = ']';
    public final static char DOUBLE_QUOTE = '"';
    public final static char COLON = ':';
    public final static char COMMA = ',';

    protected final String json;
    protected int index = -1;
    protected final int len;

    public AbstractJsonParser(String json, Class<?> target, AbstractTypeFactory typeFactory) {
        this(json, typeFactory);
        typeStack.push(typeFactory.createType(target));
    }

    public AbstractJsonParser(String json, AbstractTypeFactory typeFactory) {
        this.json = json;
        this.len = json.length() - 1;
        this.typeFactory = typeFactory;
    }

    protected void putType(AbstractType type) {
        typeStack.push(type);
    }

    protected void putName(Object val) {
        nameStack.push(val);
    }

    protected void putSymbol(char symbol) {
        symbolStack.push(symbol);
    }

    protected void putData(Object val) {
        dataStack.push(val);
    }

    protected void putCurrent(Object val) {
        currentStack.push(val);
    }

    protected AbstractType getType() {
        return typeStack.pop();
    }

    protected String getName() {
        return nameStack.pop().toString();
    }

    protected char getSymbol() {
        return symbolStack.pop();
    }

    protected Object getData() {
        return dataStack.pop();
    }

    public final Object parse() {
        while (index++ < len) {
            char current = json.charAt(index);

            current = parsePrimary(current);

            switch (current) {
                case LEFT_BRACE:
                case LEFT_BRACKET:
                    parseLeft(current);
                    break;
                case RIGHT_BRACE:
                case RIGHT_BRACKET:
                    return parseRight();
                case COLON:
                    parseColon();
                    break;
                case COMMA:
                    parseComa();
            }
        }
        throw new JsonParseException("json 解析异常：".concat(json));
    }

    protected abstract char parsePrimary(char current);

    protected abstract void parseColon();

    protected abstract void parseComa();

    protected abstract void parseLeft(char symbol);

    protected abstract Object parseRight();

    protected boolean isNumOrDotOrMinus(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == '-';
    }

    protected boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z');
    }

    protected boolean isDoubleQuote(char c) {
        return c == DOUBLE_QUOTE;
    }




}

