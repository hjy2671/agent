package org.nature.net.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hjy
 * 2023/3/26
 */
public abstract class AbstractMapperFactory {

    protected final Map<String, AbstractRequestParamMapper> cache = new HashMap<>();

    protected AbstractMapperFactory factory;

    public AbstractMapperFactory(AbstractMapperFactory factory) {
        this.factory = factory;
    }

    public AbstractMapperFactory() {
    }

    public abstract AbstractRequestParamMapper createMapper(String contentType);

}
