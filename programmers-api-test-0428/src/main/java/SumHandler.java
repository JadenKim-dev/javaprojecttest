import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class SumHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        JSONArray users = getUsers();
        int postCntSum = 0;
        for (int i = 0; i < users.size(); i++) {
            JSONObject user = (JSONObject) users.get(i);
            int postCount = Integer.parseInt((String) user.get("post_count"));
            postCntSum += postCount;
        }

        HashMap<String, Integer> responseObject = new HashMap<>();
        responseObject.put("sum", postCntSum);
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

    private static JSONArray getUsers() {
        try {
            FileReader fileReader = new FileReader("/home/programmers/project/data/input/user.json");
            JSONArray users = (JSONArray) new JSONParser().parse(fileReader);
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일을 읽어오는데 실패했습니다.");
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("JSON 파싱에 실패했습니다.");
        }
    }
}
