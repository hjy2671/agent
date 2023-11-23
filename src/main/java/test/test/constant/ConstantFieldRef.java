package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantFieldRef extends MetaConstant implements Quoter{
    public static final int TAG = 9;
    private final byte[] classIndexBytes = new byte[2];
    private final byte[] nameAndTypeIndexBytes = new byte[2];

    private int classIndex;
    private int nameAndTypeIndex;

    public ConstantFieldRef(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public void update(Meta meta) {
        if (meta.getOldIndex() == classIndex)
            classIndex = meta.getIndex();
        else
            nameAndTypeIndex = meta.getIndex();
    }

    @Override
    public void link(Resource resource) {
        final ConstantPool pool = resource.getClassFile().getConstantPool();
        pool.get(classIndex).addQuoter(this);
        pool.get(nameAndTypeIndex).addQuoter(this);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(classIndexBytes, 0, 2)
                .copyAndOffset(nameAndTypeIndexBytes, 0, 2);
        classIndex = BytesUtil.parseInt(classIndexBytes);
        nameAndTypeIndex = BytesUtil.parseInt(nameAndTypeIndexBytes);
        return this;
    }

    @Override
    public String getValue() {
        return pool.get(classIndex).getValue().concat(Meta.SEPARATOR).concat(pool.get(nameAndTypeIndex).getValue());
    }

}
