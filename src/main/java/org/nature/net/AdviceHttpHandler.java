package org.nature.net;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class AdviceHttpHandler implements HttpHandler {

    public abstract HttpPackage handleBefore(HttpExchange httpExchange);

    @Override
    public final void handle(HttpExchange httpExchange) {
        handleAfter(doHandle(handleBefore(httpExchange)));
    }

    public abstract HttpPackage doHandle(HttpPackage httpPackage);

    public abstract void handleAfter(HttpPackage httpPackage);
}
