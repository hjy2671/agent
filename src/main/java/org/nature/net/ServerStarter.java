package org.nature.net;

import com.sun.jdi.InternalException;
import com.sun.net.httpserver.HttpServer;
import org.nature.util.ObjectHolder;
import org.nature.util.ScannerUtil;
import java.io.IOException;
import java.net.InetSocketAddress;

public final class ServerStarter {

    public static void start() {
        final Configuration config = new Configuration();
        ObjectHolder.put(Configuration.CACHE_KEY, config);
        //先装载文件
        ScannerUtil.scan(config.getScanPath());
        //启动服务
        run(config.getPort(), config.getBacklog());
        //装载httpHandler

    }

    private static void run(int port, int backlog) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), backlog);
            server.createContext("/", new MajorHttpHandler());
        } catch (IOException | IllegalArgumentException e) {
            throw new InternalException("服务启动失败 " + e.getMessage());
        }
        server.start();
        System.out.println("server run");
    }


}
