import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static final int PORT = 5678;

    public static void main(String[] args) {
        HttpServer server = createHttpServer(PORT);

        server.createContext("/", new RootHandler());
        server.createContext("/sum", new SumHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("서버가 실행중입니다: 포트 번호 " + PORT);
    }

    private static HttpServer createHttpServer(int port) {
        try {
            return HttpServer.create(new InetSocketAddress(port), 0);
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("서버 실행에 실패했습니다.");
        }
    }
}
