package org.nature.util.json;

import org.nature.util.ObjUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过匹配的name去解析json，需要传入类型和泛型组合的一个map，将需要的对象解析出来，不需要的会跳过
 * @author hjy
 * 2023/3/25
 */
public class JsonFinder extends JsonParser{


    private final Map<String, GenericType> types;
    private int depth = 0;

    /**
     * @param json        json
     * @param typeFactory type的工厂类
     */
    public JsonFinder(String json, AbstractTypeFactory typeFactory, Map<String, GenericType> types) {
        super(json, typeFactory);
        this.types = types;
        typeStack.push(new MixtureMapType(types));
    }

    public Map<String, Object> find() {
        return ObjUtil.convert(new HashMap<>(), parse());
    }

    @Override
    protected void parseLeft(char symbol) {
        if (isInnerObject()){
            depth++;
            super.parseLeft(symbol);
            return;
        }
        depth++;
        selectStack(symbol);
        dataStack.push(symbol);
        if (symbolStack.isEmpty()) {
            putSymbol(symbol);
            return;
        }
        putSymbol(symbol);
        final GenericType genericType = types.get(getNameFromStack());
        putType(typeFactory.createType(genericType.getType()));
        if (genericType.isGeneric) {
            dataStack.push(parse(genericType.getGeneric()));
            return;
        }
        dataStack.push(parse());
    }

    @Override
    protected Object parseRight() {
        depth--;
        return super.parseRight();
    }

    @Override
    protected void putCurrent(Object val) {
        if (currentStack == nameStack) {
            putName(val);
        } else {
            putData(val);
        }
    }
    @Override
    protected void putName(Object val) {
        if (types.containsKey(val.toString()) || isInnerObject()) {
            super.putName(val);
        } else {
            jumpToNextStart();
        }
    }

    private boolean isInnerObject() {
        return depth > 1;
    }

    /**
     * 将index移动到当前对象的末尾
     */
    protected void next(char symbol) {
        char endSymbol = 0;
        int deep = -1;

        if (symbol == LEFT_BRACE) {
            endSymbol = RIGHT_BRACE;
        }
        if (symbol == LEFT_BRACKET) {
            endSymbol = RIGHT_BRACKET;
        }

        while (index++ < len) {
            final char t = json.charAt(index);
            if (t == symbol) {
                deep--;
                continue;
            }
            if (t == endSymbol) {
                deep++;
                if (deep == 0)return;
            }

        }
    }

    protected void jumpToNextStart() {
        while (index++ < len) {
            final char t = json.charAt(index);
            if (t == COMMA) {
                index--;
                break;
            }
            if (t == LEFT_BRACKET) {
                next(LEFT_BRACKET);
                break;
            }
            if (t == LEFT_BRACE) {
                next(LEFT_BRACE);
                break;
            }
            if (index + 1 == len && json.charAt(index + 1) == RIGHT_BRACE)
                break;
        }
    }

    public static class GenericType {

        private final Class<?> type;
        private final String generic;
        private boolean isGeneric = false;

        public GenericType(Class<?> type, String generic) {
            this.type = type;
            this.generic = generic;
            //if (generic != null && generic.contains("<")) {
                this.isGeneric = generic != null && generic.contains("<");
            //}
        }

        public GenericType(Class<?> type) {
            this(type, null);
        }

        public Class<?> getType() {
            return type;
        }

        public String getGeneric() {
            return generic;
        }

        public boolean isGeneric() {
            return isGeneric;
        }
    }

}
