package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantClassInfo extends MetaConstant implements Quoter{
    private static final int SIZE = 2;
    public static final int TAG = 7;

    private final byte[] bytes = new byte[SIZE];
    private int point2Index;

    public ConstantClassInfo(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public void update(Meta meta) {
        updateIndex(meta.getIndex());
    }

    @Override
    public void link(Resource resource) {
        resource.getClassFile().getConstantPool().get(point2Index).addQuoter(this);
    }

    @Override
    public Meta construct(Resource resource) {
        resource.copyNextAndOffset(bytes, 0, SIZE);
        point2Index = BytesUtil.parseInt(bytes);

        return this;
    }

    @Override
    public String getValue() {
        return pool.get(point2Index).getValue();
    }

}
