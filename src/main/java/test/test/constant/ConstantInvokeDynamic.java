package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantInvokeDynamic extends MetaConstant implements Quoter{

    public static final int TAG = 18;

    private final byte[] methodAttrIndexBytes = new byte[2];
    private final byte[] nameAndTypeIndexBytes = new byte[2];

    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    public ConstantInvokeDynamic(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public void update(Meta meta) {
        nameAndTypeIndex = meta.getIndex();
    }

    @Override
    public void link(Resource resource) {
        resource.getClassFile().getConstantPool().get(nameAndTypeIndex).addQuoter(this);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(methodAttrIndexBytes, 0, 2)
                .copyAndOffset(nameAndTypeIndexBytes, 0, 2);
        nameAndTypeIndex = BytesUtil.parseInt(nameAndTypeIndexBytes);
        return this;
    }

    @Override
    public String getValue() {
        return pool.get(nameAndTypeIndex).getValue();
    }
}
