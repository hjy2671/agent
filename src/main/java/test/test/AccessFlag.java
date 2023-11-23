package test.test;

/**
 * @author hjy
 * 2023/3/30
 */
public class AccessFlag implements ClassStruct{

    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_PRIVATE = 0x0002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_VOLATILE = 0x0040;
    public static final int ACC_TRANSIENT = 0x0080;
    public static final int ACC_SYNTHETIC = 0x1000;
    public static final int ACC_ENUM = 0x4000;

    private byte[] flag = new byte[ACCESS_FLAG_SIZE];

    @Override
    public AccessFlag construct(Resource resource) {
        resource.copyAndOffset(flag, 0, ACCESS_FLAG_SIZE);
        return this;
    }
}
