package test.test.constant;

import test.test.ConstantPool;
import test.test.Resource;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/3/30
 */
public class ConstantNameAndType extends MetaConstant implements Quoter{
    public static final int TAG = 12;

    private final byte[] nameIndexBytes = new byte[2];
    private final byte[] descIndexBytes = new byte[2];

    private Integer nameIndex;
    private Integer descIndex;

    public ConstantNameAndType(int index, ConstantPool pool) {
        super(index, pool);
    }

    @Override
    public void update(Meta meta) {
        if (meta.getOldIndex() == nameIndex) {
            nameIndex = meta.getIndex();
        } else {
            descIndex = meta.getIndex();
        }
    }

    @Override
    public void link(Resource resource) {
        final ConstantPool pool = resource.getClassFile().getConstantPool();
        pool.get(nameIndex).addQuoter(this);
        pool.get(descIndex).addQuoter(this);
    }

    @Override
    public Meta construct(Resource resource) {

        resource.copyNextAndOffset(nameIndexBytes, 0, 2)
                .copyAndOffset(descIndexBytes, 0, 2);
        nameIndex = BytesUtil.parseInt(nameIndexBytes);
        descIndex = BytesUtil.parseInt(descIndexBytes);
        return this;
    }

    @Override
    public String getValue() {
        return pool.get(nameIndex).getValue().concat(Meta.SEPARATOR).concat(pool.get(descIndex).getValue());
    }
}
