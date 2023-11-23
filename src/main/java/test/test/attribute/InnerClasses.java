package test.test.attribute;

import test.test.*;
import test.test.constant.Meta;
import test.test.constant.Quoter;
import test.test.util.BytesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjy
 * 2023/4/12
 */
public class InnerClasses extends AttributeInfo {

    private byte[] classCountBytes = new byte[2];
    private List<InnerClass> innerClasses = new ArrayList<>();

    public InnerClasses(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        super(nameIndexBytes, nameIndex, constantName);
    }

    @Override
    public AttributeInfo constructValue(Resource resource) {
        resource.copyAndOffset(classCountBytes, 0, 2);
        for (int i = 0; i < BytesUtil.parseInt(classCountBytes); i++) {
            innerClasses.add(new InnerClass().construct(resource));
        }
        return null;
    }

    @Override
    public void update(Meta meta) {

    }
}
class InnerClass implements ClassStruct, Quoter {

    private byte[] innerClassIndex = new byte[2];
    private byte[] outerClassIndex = new byte[2];
    private byte[] innerNameIndex = new byte[2];
    private AccessFlag accessFlag;
    private Meta innerClass;
    private Meta outerClass;
    private Meta innerName;

    @Override
    public InnerClass construct(Resource resource) {
        resource.copyAndOffset(innerClassIndex, 0, 2)
                .copyAndOffset(outerClassIndex, 0, 2)
                .copyAndOffset(innerNameIndex, 0, 2);
        accessFlag = new AccessFlag().construct(resource);
        link(resource);
        return null;
    }

    @Override
    public void update(Meta meta) {

    }

    @Override
    public void link(Resource resource) {
        final ConstantPool pool = resource.getClassFile().getConstantPool();
        innerClass = pool.get(BytesUtil.parseInt(innerClassIndex));
        outerClass = pool.get(BytesUtil.parseInt(outerClassIndex));
        innerName = pool.get(BytesUtil.parseInt(innerNameIndex));
    }
}
