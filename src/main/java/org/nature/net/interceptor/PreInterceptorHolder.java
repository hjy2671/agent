package org.nature.net.interceptor;

import org.nature.net.HttpPackage;
import org.nature.net.HttpPackageImpl;
import com.sun.net.httpserver.HttpExchange;

public class PreInterceptorHolder {

    private final static InterceptorHolder holder = new InterceptorHolder();

    public static void putInterceptor(PreInterceptor interceptor) {
        holder.putInterceptor(interceptor);
    }

    public static HttpPackage doIntercept(HttpExchange httpExchange) {
        return holder.doIntercept(new HttpPackageImpl(httpExchange), false);
    }

}
