package test.test;

/**
 * @author hjy
 * 2023/4/4
 */
public interface ClassStruct {

    int MAGIC_SIZE = 4;
    int MINOR_VERSION_SIZE = 2;
    int MAJOR_VERSION_SIZE = 2;
    int CONSTANT_POOL_COUNT_SIZE = 2;
    int ACCESS_FLAG_SIZE = 2;
    int THIS_CLASS_SIZE = 2;
    int SUPER_CLASS_SIZE = 2;
    int INTERFACES_COUNT_SIZE = 2;
    int FIELDS_COUNT_SIZE = 2;
    int METHOD_COUNT_SIZE = 2;
    int ATTRIBUTES_COUNT_SIZE = 2;

    /**
     * 构建类结构，将字节封装成对象
     * @param resource 各个结构构建时用于传递共享的资源
     */
    ClassStruct construct(Resource resource);

}
