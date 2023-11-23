package org.nature.net.handler;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author hjy
 * 2023/3/26
 */
public abstract class AbstractUrlParamHandler implements RequestParamHandler{

    @Override
    public String getParam(HttpExchange http) {
        final String uri = http.getRequestURI().toString();
        final int index = uri.indexOf("?");
        if (index > 0) {
            return uri.substring(index + 1);
        }
        return null;
    }
}
