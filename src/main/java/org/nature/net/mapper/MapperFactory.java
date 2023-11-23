package org.nature.net.mapper;

import org.nature.net.handler.RequestParamHandler;

/**
 * @author hjy
 * 2023/3/26
 */
public class MapperFactory extends AbstractMapperFactory{

    public MapperFactory(AbstractMapperFactory factory) {
        super(factory);
    }

    public MapperFactory() {
    }

    @Override
    public AbstractRequestParamMapper createMapper(String key) {
        final int index = key.indexOf(";");
        if (index > 0) {
            key = key.substring(0, index);
        }

        if (factory != null) {
            final AbstractRequestParamMapper mapper = factory.createMapper(key);
            if (mapper != null)
                return mapper;
        }

        if (!cache.containsKey(key)) {
            cache.put(key, createNew(key));
        }
        return cache.get(key);
    }

    private AbstractRequestParamMapper createNew(String key) {
        switch (key) {
            case RequestParamHandler.POST + RequestParamHandler.CONTENT_TYPE_FORM:
                return new FormUrlEncodedParamMapper();
            case RequestParamHandler.POST + RequestParamHandler.CONTENT_TYPE_FORM_DATA:
                return new FormDataParamMapper();
            case RequestParamHandler.POST + RequestParamHandler.CONTENT_TYPE_JSON:
                return new JsonParamMapper();
            default:
                return new UrlParamMapper();
        }
    }
}
