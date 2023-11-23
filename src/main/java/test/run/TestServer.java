package test.run;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class TestServer {

    public static void main(String[] args) throws IOException {
        run();
    }

    public static void run() throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);

        server.createContext("/test", new MyHandler());
        //server.createContext("/pla", new MyHandler2());
        server.createContext("/", new MyHandler2());
        server.start();
        System.out.println("server run");
    }

}
class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("收到了请求");
        final String method = httpExchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            System.out.println("get");
            httpExchange.getResponseHeaders().set("Content-Type", "text/html;charset=utf-8");
            final String uri = httpExchange.getRequestURI().toString();
            String result = "";
            final String strings = uri.substring(uri.indexOf("?") + 1);
            if (uri.startsWith("/test/change")) {

                try {
                    result = AgentStarter.change("AgentTest", strings);
                } catch (AttachNotSupportedException | AgentLoadException | AgentInitializationException e) {
                    e.printStackTrace();
                }
            }
//            final InputStream inputStream1 = MyHandler.class.getClassLoader().getResourceAsStream("test/run/TestServer.class");
//            assert inputStream1 != null;
//            final InputStreamReader streamReader = new InputStreamReader(inputStream1);
//            final BufferedReader reader = new BufferedReader(streamReader);
//
//            String temp;
//            final StringBuilder builder = new StringBuilder();
//            while ((temp = reader.readLine()) != null)
//                builder.append(temp);
            final String response = result + " " + strings;

            //reader.close();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream responseBody = httpExchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(responseBody, StandardCharsets.UTF_8);
            writer.write(response);
            writer.flush();
            writer.close();
        }
    }
}
class MyHandler2 implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("收到了请求22");
        final String method = httpExchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {

            final String response = "请求成功";

            //reader.close();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream responseBody = httpExchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(responseBody, StandardCharsets.UTF_8);
            writer.write(response);
            writer.flush();
            writer.close();
        }
    }
}
