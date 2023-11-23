package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantMethodHandle extends MetaConstant implements Quoter{
    public static final int TAG = 15;

    private final byte[] referenceKindBytes = new byte[1];
    private final byte[] referenceIndexBytes = new byte[2];

    private Integer referenceKind;
    private int referenceIndex;

    public ConstantMethodHandle(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public void update(Meta meta) {
        referenceIndex = meta.getIndex();
    }

    @Override
    public void link(Resource resource) {
        resource.getClassFile().getConstantPool().get(referenceIndex).addQuoter(this);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(referenceKindBytes, 0, 1)
                .copyAndOffset(referenceIndexBytes, 0, 2);
        referenceKind = BytesUtil.parseInt(referenceKindBytes);
        referenceIndex = BytesUtil.parseInt(referenceIndexBytes);
        return this;
    }

    @Override
    public String getValue() {
        return referenceKind.toString().concat(Meta.SEPARATOR).concat(pool.get(referenceIndex).getValue());
    }
}
