package test.test;

import test.test.constant.Meta;

/**
 * @author hjy
 * 2023/3/30
 */
public class ClassFile {

    private Magic magic; //4 字节
    private MinorVersion minorVersion; //2 字节
    private MajorVersion majorVersion; //2
    private ConstantPool constantPool;
    private AccessFlag accessFlag;//2
    private ThisClass thisClass;//2
    private SuperClass superClass;//2
    private InterfacePool interfacePool;
    private FieldPool fieldPool;
    private MethodPool methodPool;
    private AttributePool attributePool;

    private final byte[] bytes;

    public ClassFile(byte[] bytes) {
        this.bytes = bytes;
    }

    public void resolve() {
        final Resource resource = new Resource(bytes, this);
        new Builder(resource)
                .buildMagic()
                .buildMinorVersion()
                .buildMajorVersion()
                .buildConstantPool()
                .buildAccessFlag()
                .buildThisClass()
                .buildSuperClass()
                .buildInterfacePool()
                .buildFieldPool()
                .buildMethodPool()
                .buildAttributePool();

        for (int i = 1; i <= constantPool.size(); i++) {
            final Meta meta = constantPool.get(i);
            if (meta == null)
                continue;
            System.out.println(i + "\t\t" + meta.getClass().getSimpleName().concat("\t\t\t").concat(meta.getValue()));
        }
        System.out.println(1);
    }

    private class Builder {

        Resource resource;

        public Builder(Resource resource) {
            this.resource = resource;
        }

        public Builder buildMagic() {
            magic = new Magic().construct(resource);
            return this;
        }

        public Builder buildMinorVersion() {
            minorVersion = new MinorVersion().construct(resource);
            return this;
        }

        public Builder buildMajorVersion() {
            majorVersion = new MajorVersion().construct(resource);
            return this;
        }

        public Builder buildConstantPool() {
            constantPool = new ConstantPool().construct(resource);
            constantPool.link(resource);
            return this;
        }

        public Builder buildAccessFlag() {
            accessFlag = new AccessFlag().construct(resource);
            return this;
        }

        public Builder buildThisClass() {
            thisClass = new ThisClass().construct(resource);
            return this;
        }

        public Builder buildSuperClass() {
            superClass = new SuperClass().construct(resource);
            return this;
        }

        public Builder buildInterfacePool() {
            interfacePool = new InterfacePool().construct(resource);
            return this;
        }

        public Builder buildFieldPool() {
            fieldPool = new FieldPool().construct(resource);
            return this;
        }

        public Builder buildMethodPool() {
            methodPool = new MethodPool().construct(resource);
            return this;
        }

        public Builder buildAttributePool() {
            attributePool = new AttributePool().construct(resource);
            return this;
        }

    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }
}
