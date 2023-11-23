package test.test;

import test.test.constant.Meta;
import test.test.constant.Quoter;
import test.test.util.BytesUtil;

/**
 * @author hjy
 * 2023/4/5
 */
public abstract class AttributeInfo implements ClassStruct, Quoter {

    protected final byte[] nameIndexBytes;
    protected final byte[] lenBytes = new byte[4];

    protected int len;
    protected int nameIndex;
    protected Meta constantName;

    public AttributeInfo(byte[] nameIndexBytes, int nameIndex, Meta constantName) {
        this.nameIndexBytes = nameIndexBytes;
        this.nameIndex = nameIndex;
        this.constantName = constantName;
    }

    @Override
    public final AttributeInfo construct(Resource resource) {
        resource.copyAndOffset(lenBytes, 0, 4);
        len = BytesUtil.parseInt(lenBytes);
        final AttributeInfo result = constructValue(resource);
        link(resource);
        return result;
    }

    public abstract AttributeInfo constructValue(Resource resource);

    @Override
    public void link(Resource resource) {
        nameIndex = BytesUtil.parseInt(nameIndexBytes);
        constantName = resource.getClassFile().getConstantPool().get(nameIndex);
        constantName.addQuoter(this);
    }

    @Override
    public abstract void update(Meta meta);

    public String getName() {
        return constantName.getValue();
    }
}
