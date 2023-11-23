package org.nature.net.handler;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author hjy
 * 2023/3/26
 */
public interface RequestParamHandler {
    String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    String CONTENT_TYPE_JSON = "application/json";
    String CONTENT_TYPE_FORM_DATA = "multipart/form-data";
    String CONTENT_TYPE_KEY = "Content-type";
    String GET = "GET";
    String POST = "POST";

    String getParam(HttpExchange http);
}
