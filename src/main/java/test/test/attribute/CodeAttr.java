package test.test.attribute;

import test.test.AttributeInfo;
import test.test.AttributePool;
import test.test.ClassStruct;
import test.test.Resource;
import test.test.constant.Meta;
import test.test.util.BytesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/4/12
 */
public class CodeAttr extends AttributeInfo {

    private byte[] maxStack = new byte[2];
    private byte[] maxLocals = new byte[2];

    //exception pool
    private CodePool codePool;
    private AttributePool attributePool;
    private ExceptionTable exceptionTable;

    public CodeAttr(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        super(nameIndexBytes, nameIndex, constantName);
    }

    @Override
    public AttributeInfo constructValue(Resource resource) {
        resource.copyAndOffset(maxStack, 0, 2)
                .copyAndOffset(maxLocals, 0, 2);
        codePool = new CodePool().construct(resource);
        attributePool = new AttributePool().construct(resource);
        exceptionTable = new ExceptionTable().construct(resource);
        return this;
    }

    @Override
    public void update(Meta meta) {

    }
}

class ExceptionTable implements ClassStruct {

    private byte[] tableLength = new byte[2];
    private List<ExceptionTab> table = new ArrayList<>();

    @Override
    public ExceptionTable construct(Resource resource) {
        resource.copyAndOffset(tableLength, 0, 2);
        for (int i = 0; i < BytesUtil.parseInt(tableLength); i++) {
            table.add(new ExceptionTab().construct(resource));
        }
        return this;
    }
}

class ExceptionTab implements ClassStruct{
    private byte[] startPc = new byte[2];
    private byte[] endPc = new byte[2];
    private byte[] handlerPc = new byte[2];
    private byte[] catchType = new byte[2];
    @Override
    public ExceptionTab construct(Resource resource) {
        resource.copyAndOffset(startPc, 0, 2)
                .copyAndOffset(endPc, 0, 2)
                .copyAndOffset(handlerPc, 0, 2)
                .copyAndOffset(catchType, 0, 2);
        return this;
    }
}

class CodePool implements ClassStruct{
    private byte[] codeLength = new byte[4];
    private List<Code> codes = new ArrayList<>();
    @Override
    public CodePool construct(Resource resource) {
        resource.copyAndOffset(codeLength, 0, 4);
        for (int i = 0; i < BytesUtil.parseInt(codeLength); i++) {
            codes.add(new Code().construct(resource));
        }
        return this;
    }
}

class Code implements ClassStruct {
    private byte[] code = new byte[1];

    @Override
    public Code construct(Resource resource) {
        resource.copyAndOffset(code, 0 ,1);
        return this;
    }
}
