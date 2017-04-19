import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;

/**
 * Created by michaelberber on 4/12/17.
 */

@WebSocket
public class ChatWebSocketHandler {
    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String username = "User" + Chat.nextUserNumber++;
        Chat.userUsenameMap.put(user, username);
        Chat.broadcastMessage(sender = "Server", msg = (username + " joined the chat."));
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = Chat.userUsenameMap.get(user);
        Chat.userUsenameMap.remove(user);
        Chat.broadcastMessage(sender = "Server", msg = (username + " has left the chat."));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        Chat.broadcastMessage(sender = Chat.userUsenameMap.get(user), msg = message);
    }
}
