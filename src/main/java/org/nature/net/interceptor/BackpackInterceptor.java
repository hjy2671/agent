package org.nature.net.interceptor;

import com.sun.net.httpserver.HttpExchange;
import org.nature.net.HttpPackage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public final class BackpackInterceptor implements PostInterceptor {

    @Override
    public HttpPackage intercept(HttpPackage httpPackage) {
        final HttpExchange httpExchange = httpPackage.getHttpExchange();
        try {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, httpPackage.getResult().toString().getBytes(StandardCharsets.UTF_8).length);
            OutputStream responseBody = httpExchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(responseBody, StandardCharsets.UTF_8);
            writer.write(httpPackage.getResult().toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
