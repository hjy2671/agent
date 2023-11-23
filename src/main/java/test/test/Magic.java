package test.test;

/**
 * @author hjy
 * 2023/3/30
 */
public class Magic implements ClassStruct{

    private byte[] value = new byte[MAGIC_SIZE];

    @Override
    public Magic construct(Resource resource) {
        System.arraycopy(resource.resource, resource.current, value, 0, MAGIC_SIZE);
        resource.current += MAGIC_SIZE;
        return this;
    }
}
