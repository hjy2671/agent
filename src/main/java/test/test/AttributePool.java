package test.test;

import test.test.attribute.*;
import test.test.attribute.Deprecated;
import test.test.constant.Meta;
import test.test.util.BytesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/3/30
 */
public class AttributePool implements ClassStruct {

    public static final String CONSTANT_VALUE = "ConstantValue";
    public static final String ANNOTATION_DEFAULT = "AnnotationDefault";
    public static final String BOOTSTRAP_METHODS = "BootstrapMethods";
    public static final String CODE = "Code";
    public static final String DEPRECATED = "Deprecated";
    public static final String EXCEPTIONS = "Exceptions";
    public static final String LINE_NUMBER_TABLE = "LineNumberTable";
    public static final String LOCAL_VARIABLE_TABLE = "LocalVariableTable";
    public static final String LOCAL_VARIABLE_TYPE_TABLE = "LocalVariableTypeTable";
    public static final String METHOD_PARAMETERS = "MethodParameters";
    public static final String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
    public static final String RUNTIME_INVISIBLE_TYPE_ANNOTATIONS = "RuntimeInvisibleTypeAnnotations";
    public static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
    public static final String RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS = "RuntimeVisibleParameterAnnotations";
    public static final String RUNTIME_VISIBLE_TYPE_ANNOTATIONS = "RuntimeVisibleTypeAnnotations";
    public static final String SIGNATURE = "Signature";
    public static final String SOURCE_DEBUG_EXTENSION = "SourceDebugExtension";
    public static final String SOURCE_FILE = "SourceFile";
    public static final String STACK_MAP_TABLE = "StackMapTable";
    public static final String INNER_CLASSES = "InnerClasses";
    public static final String ENCLOSING_METHOD = "EnclosingMethod";
    public static final String SYNTHETIC = "Synthetic";

    private byte[] value = new byte[ATTRIBUTES_COUNT_SIZE];
    private int count;
    private List<AttributeInfo> attributeInfos = new ArrayList<>();

    @Override
    public AttributePool construct(Resource resource) {
        resource.copyAndOffset(value, 0, ATTRIBUTES_COUNT_SIZE);
        count = BytesUtil.parseInt(value);

        for (int i = 0; i < count; i++) {
            byte[] nameIndexBytes = new byte[2];
            resource.copyAndOffset(nameIndexBytes, 0, 2);
            int nameIndex = BytesUtil.parseInt(nameIndexBytes);
            Meta constantName = resource.getClassFile().getConstantPool().get(nameIndex);
            attributeInfos.add(constructAttribute(nameIndexBytes, nameIndex, constantName).construct(resource));
        }

        return this;
    }

    public AttributeInfo constructAttribute(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        switch (constantName.getValue()) {
            case CONSTANT_VALUE:
                return new ConstantValue(nameIndexBytes, nameIndex, constantName);
            case ANNOTATION_DEFAULT:
                return new AnnotationDefault(nameIndexBytes, nameIndex, constantName);
            case BOOTSTRAP_METHODS:
                return new BootstrapMethods(nameIndexBytes, nameIndex, constantName);
            case CODE:
                return new CodeAttr(nameIndexBytes, nameIndex, constantName);
            case DEPRECATED:
                return new Deprecated(nameIndexBytes, nameIndex, constantName);
            case EXCEPTIONS:
                return new Exceptions(nameIndexBytes, nameIndex, constantName);
            case LINE_NUMBER_TABLE:
                return new LineNumberTable(nameIndexBytes, nameIndex, constantName);
            case LOCAL_VARIABLE_TABLE:
                return new LocalVariableTable(nameIndexBytes, nameIndex, constantName);
            case LOCAL_VARIABLE_TYPE_TABLE:
                return new LocalVariableTypeTable(nameIndexBytes, nameIndex, constantName);
            case METHOD_PARAMETERS:
                return new MethodParameters(nameIndexBytes, nameIndex, constantName);
            case RUNTIME_INVISIBLE_ANNOTATIONS:
                return new RuntimeInvisibleAnnotations(nameIndexBytes, nameIndex, constantName);
            case RUNTIME_INVISIBLE_TYPE_ANNOTATIONS:
                return new RuntimeInvisibleTypeAnnotations(nameIndexBytes, nameIndex, constantName);
            case RUNTIME_VISIBLE_ANNOTATIONS:
                return new RuntimeVisibleAnnotations(nameIndexBytes, nameIndex, constantName);
            case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
                return new RuntimeVisibleParameterAnnotations(nameIndexBytes, nameIndex, constantName);
            case RUNTIME_VISIBLE_TYPE_ANNOTATIONS:
                return new RuntimeVisibleTypeAnnotations(nameIndexBytes, nameIndex, constantName);
            case SIGNATURE:
                return new Signature(nameIndexBytes, nameIndex, constantName);
            case SOURCE_DEBUG_EXTENSION:
                return new SourceDebugExtension(nameIndexBytes, nameIndex, constantName);
            case SOURCE_FILE:
                return new SourceFile(nameIndexBytes, nameIndex, constantName);
            case STACK_MAP_TABLE:
                return new StackMapTable(nameIndexBytes, nameIndex, constantName);
            case INNER_CLASSES:
                return new InnerClasses(nameIndexBytes, nameIndex, constantName);
            case ENCLOSING_METHOD:
                return new EnclosingMethod(nameIndexBytes, nameIndex, constantName);
            case SYNTHETIC:
                return new Synthetic(nameIndexBytes, nameIndex, constantName);
        }
        throw new RuntimeException("没有对应的Attribute:".concat(constantName.getValue()));
    }
}
