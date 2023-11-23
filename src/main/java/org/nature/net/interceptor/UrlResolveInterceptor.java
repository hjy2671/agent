package org.nature.net.interceptor;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.nature.net.*;
import org.nature.net.handler.RequestParamHandler;
import org.nature.net.handler.RequestParamHandlerFactory;
import org.nature.net.mapper.AbstractMapperFactory;
import org.nature.net.mapper.MapperFactory;
import org.nature.util.ObjectHolder;
import org.nature.util.handler.SolutionHandler;

public final class UrlResolveInterceptor implements PreInterceptor {

    private final AbstractMapperFactory mapperFactory = new MapperFactory();
    private final RequestParamHandlerFactory handlerFactory = new RequestParamHandlerFactory();

    @Override
    public HttpPackage intercept(HttpPackage httpPackage) {
        final HttpExchange httpExchange = httpPackage.getHttpExchange();
        final String uri = httpExchange.getRequestURI().toString();
        final String requestMethod = httpExchange.getRequestMethod().toUpperCase();

        final int index = uri.indexOf("?");
        String url = null;
        String key;

        if (index > 0) {
            url = uri.substring(0, index);
            key = url.concat(requestMethod);
        } else {
            key = uri.concat(requestMethod);
        }


        final SolutionHandler.ProxyParams proxyParams = ObjectHolder.get(key.concat(Configuration.PARAMETERS_CACHE_KEY_SUFFIX), SolutionHandler.ProxyParams.class);
        final Headers header = httpExchange.getRequestHeaders();
        String mapperKey = requestMethod.concat(header.containsKey(RequestParamHandler.CONTENT_TYPE_KEY) ? header.get(RequestParamHandler.CONTENT_TYPE_KEY).get(0) : "");


        final Object[] mapping = mapperFactory
                .createMapper(mapperKey)
                .mapping(proxyParams, handlerFactory.create(requestMethod).getParam(httpExchange));

        return httpPackage.setArgs(mapping).setKey(key).setUrl(url).setRequestMethod(requestMethod);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
