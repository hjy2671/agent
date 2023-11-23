package test.test;

/**
 * @author hjy
 * 2023/3/30
 */
public class MinorVersion implements ClassStruct {

    private byte[] version = new byte[MINOR_VERSION_SIZE];

    @Override
    public MinorVersion construct(Resource resource) {
        System.arraycopy(resource.resource, resource.current, version, 0, MINOR_VERSION_SIZE);
        resource.current += MINOR_VERSION_SIZE;
        return this;
    }
}
