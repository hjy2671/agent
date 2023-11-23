package org.nature.net;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.nature.net.annotation.StartHttpHandler;
import org.nature.net.interceptor.PostInterceptorHolder;
import org.nature.net.interceptor.PreInterceptorHolder;
import org.nature.shadow.ProxyFunction;
import org.nature.util.ObjectHolder;

@StartHttpHandler
public final class MajorHttpHandler extends AdviceHttpHandler implements HttpHandler {
    @Override
    public HttpPackage handleBefore(HttpExchange httpExchange) {
        try {
            return PreInterceptorHolder.doIntercept(httpExchange);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpPackage doHandle(HttpPackage httpPackage) {
        final ProxyFunction proxyFunction = ObjectHolder.get(httpPackage.getKey(), ProxyFunction.class);
        try {
            return httpPackage.setResult(proxyFunction.invoke(httpPackage.getArgs()));
        } catch (NullPointerException e) {
            System.out.println("没有找到对应的请求：".concat(httpPackage.getUrl()));
        }
        return httpPackage;
    }

    @Override
    public void handleAfter(HttpPackage httpPackage) {
        PostInterceptorHolder.doIntercept(httpPackage);
    }
}
