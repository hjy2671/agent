package org.nature.net.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hjy
 * 2023/3/26
 */
public class RequestParamHandlerFactory {

    private final Map<String, RequestParamHandler> map = new HashMap<>();

    public RequestParamHandler create(String method) {
        if (!map.containsKey(method)) {
            map.put(method, createNew(method));
        }
        return map.get(method);
    }

    private RequestParamHandler createNew(String method) {
        switch (method.toUpperCase()) {
            case RequestParamHandler.GET:
                return new GetMethodHandler();
            case RequestParamHandler.POST:
                return new PostMethodHandler();
            default:
                return null;
        }
    }
}
