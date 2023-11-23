package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantMethodType extends MetaConstant implements Quoter{
    public static final int TAG = 16;

    private final byte[] descIndexBytes = new byte[2];
    private Integer descIndex;

    public ConstantMethodType(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public void update(Meta meta) {
        descIndex = meta.getIndex();
    }

    @Override
    public void link(Resource resource) {
        resource.getClassFile().getConstantPool().get(descIndex).addQuoter(this);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(descIndexBytes, 0, 2);
        descIndex = BytesUtil.parseInt(descIndexBytes);
        return this;
    }

    @Override
    public String getValue() {
        return pool.get(descIndex).getValue();
    }
}
