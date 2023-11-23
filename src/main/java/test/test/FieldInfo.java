package test.test;

import test.test.constant.Meta;
import test.test.constant.Quoter;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class FieldInfo implements ClassStruct, Quoter {

    private AccessFlag flag;
    private byte[] nameIndexBytes = new byte[2];
    private byte[] descIndexBytes = new byte[2];

    private int nameIndex;
    private int descIndex;

    private AttributePool attributePool;

    @Override
    public FieldInfo construct(Resource resource) {
        flag = new AccessFlag().construct(resource);

        resource.copyAndOffset(nameIndexBytes, 0, 2);
        resource.copyAndOffset(descIndexBytes, 0, 2);

        nameIndex = BytesUtil.parseInt(nameIndexBytes);
        descIndex = BytesUtil.parseInt(descIndexBytes);

        link(resource);

        attributePool = new AttributePool().construct(resource);

        return this;
    }

    @Override
    public void link(Resource resource) {
        final ConstantPool constantPool = resource.getClassFile().getConstantPool();
        constantPool.get(nameIndex).addQuoter(this);
        constantPool.get(descIndex).addQuoter(this);
    }

    @Override
    public void update(Meta meta) {
        final int oldIndex = meta.getOldIndex();
        if (oldIndex == nameIndex)
            nameIndex = meta.getIndex();

        if (oldIndex == descIndex)
            descIndex = meta.getIndex();
    }


}
