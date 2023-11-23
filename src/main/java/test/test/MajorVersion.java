package test.test;

/**
 * @author hjy
 * 2023/3/30
 */
public class MajorVersion implements ClassStruct {

    private byte[] version = new byte[MAJOR_VERSION_SIZE];

    @Override
    public MajorVersion construct(Resource resource) {
        System.arraycopy(resource.resource, resource.current, version, 0, MAJOR_VERSION_SIZE);
        resource.current += MAJOR_VERSION_SIZE;
        return this;
    }
}
