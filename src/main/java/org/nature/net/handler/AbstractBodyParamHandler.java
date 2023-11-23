package org.nature.net.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author hjy
 * 2023/3/26
 */
public abstract class AbstractBodyParamHandler implements RequestParamHandler{

    @Override
    public String getParam(HttpExchange http) {
        if (!http.getRequestHeaders().containsKey(CONTENT_TYPE_KEY))
            return null;
        String line = "";
        final StringBuilder builder = new StringBuilder();
        final InputStream requestBody = http.getRequestBody();
        final InputStreamReader streamReader = new InputStreamReader(requestBody, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(streamReader)){
            while ((line = reader.readLine()) != null)
                builder.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


}
