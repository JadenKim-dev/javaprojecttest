import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class RootHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HashMap<String, String> responseObject = new HashMap<>();
        responseObject.put("message", "server check");
        JSONObject jsonObject = new JSONObject(responseObject);
        String responseContent = jsonObject.toJSONString();
        System.out.println(responseContent);

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        OutputStream outputStream = exchange.getResponseBody();
        try {
            exchange.sendResponseHeaders(200, responseContent.getBytes().length);
            outputStream.write(responseContent.getBytes());
            outputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("응답 전송에 실패했습니다.");
        }
    }
}
