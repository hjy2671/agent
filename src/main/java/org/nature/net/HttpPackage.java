package org.nature.net;

import com.sun.net.httpserver.HttpExchange;

public abstract class HttpPackage {

    protected final HttpExchange httpExchange;
    protected String Url;
    protected Object[] args;
    protected Object result;
    protected String requestMethod;
    protected String key;

    public HttpPackage(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }


    public String getRequestMethod() {
        return requestMethod;
    }

    public HttpPackage setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public HttpExchange getHttpExchange() {
        return httpExchange;
    }

    public String getUrl() {
        return Url;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getResult() {
        return result;
    }

    public HttpPackage setUrl(String url) {
        Url = url;
        return this;
    }

    public String getKey() {
        return key;
    }

    public HttpPackage setKey(String key) {
        this.key = key;
        return this;
    }

    public HttpPackage setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public HttpPackage setResult(Object result) {
        this.result = result;
        return this;
    }

}
