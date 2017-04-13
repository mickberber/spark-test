import org.json.JSONObject;
import spark.Session;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by michaelberber on 4/11/17.
 */
public class Chat {
    static Map<Session, String> userUsenameMap = new ConcurrentHashMap<>();
    static int nextUserNumber = 1;

    public static void main(String[] args) {
        staticFileLocation("/public");
        webSocket("/chat", ChatWebSocketHandler.class);
        init();
    }

    public static void broadcastMessage(String sender, String message) {
        userUsenameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userMessage", createHtmlMessageFromSender(sender, message))
                        .put("userlist", userUsenameMap.values())
                ));
            } catch Exception e {
                e.printStackTrace();
            }
        });
    }

    private static String createHtmlMessageFromSender(String sender, String message) {
        return article().with(
                b(sender + " says:"),
                p(message),
                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }
}
