package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantString extends MetaConstant implements Quoter{
    public static final int TAG = 8;

    private final byte[] stringIndexBytes = new byte[2];
    private int stringIndex;

    public ConstantString(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public void update(Meta meta) {
        stringIndex = meta.getIndex();
    }

    @Override
    public void link(Resource resource) {
        resource.getClassFile().getConstantPool().get(stringIndex).addQuoter(this);
    }

    @Override
    public Meta construct(Resource resource) {

        resource.copyNextAndOffset(stringIndexBytes, 0, 2);
        stringIndex = BytesUtil.parseInt(stringIndexBytes);
        return this;
    }

    @Override
    public String getValue() {
        return pool.get(stringIndex).getValue();
    }
}
