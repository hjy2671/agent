package test.test.attribute;

import test.test.AttributeInfo;
import test.test.ClassStruct;
import test.test.Resource;
import test.test.constant.Meta;
import test.test.constant.Quoter;
import test.test.util.BytesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/4/12
 */
public class Exceptions extends AttributeInfo {

    private byte[] exceptionCountBytes = new byte[2];
    private List<ExceptionIndex> exceptionIndexes = new ArrayList<>();

    public Exceptions(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        super(nameIndexBytes, nameIndex, constantName);
    }

    @Override
    public AttributeInfo constructValue(Resource resource) {
        resource.copyAndOffset(exceptionCountBytes, 0, 2);
        for (int i = 0; i < BytesUtil.parseInt(exceptionCountBytes); i++) {
            exceptionIndexes.add(new ExceptionIndex().construct(resource));
        }
        return this;
    }

    @Override
    public void update(Meta meta) {

    }
}

class ExceptionIndex implements ClassStruct, Quoter {

    private byte[] index = new byte[2];
    private Meta meta;

    @Override
    public ExceptionIndex construct(Resource resource) {
        resource.copyAndOffset(index, 0, 2);
        link(resource);
        return this;
    }


    @Override
    public void update(Meta meta) {

    }

    @Override
    public void link(Resource resource) {
        meta = resource.getClassFile().getConstantPool().get(BytesUtil.parseInt(index));
    }
}
