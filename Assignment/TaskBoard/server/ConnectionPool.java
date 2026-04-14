package server;
import java.util.List;
import java.util.ArrayList;
import shared.Message;

public class ConnectionPool {
    private List<ClientHandler> connects = new ArrayList<>();
    // add ChatServerHandler into a list
    public void addConnects(ClientHandler csh){
        connects.add(csh);
    }
    // broadcast messages to all users
    public void broadcastToOthers(Message msg) {
        for (ClientHandler cnn : connects) {
            if (!cnn.getClientName().equals(msg.getUser())) {
                cnn.sendMessageToClients(msg);
            }
        }
    }

    public void broadcastToAll(Message msg){
        for (ClientHandler cnn : connects) {
            cnn.sendMessageToClients(msg);
        }
    }

    public void sendToUser(String userName, Message msg){
        for (ClientHandler cnn : connects) {
            if (cnn.getClientName().equals(userName)) {
                cnn.sendMessageToClients(msg);
            }
        }
    }

    public void removeUser(ClientHandler csh) {
        connects.remove(csh); // remove a chatserverhandler
    }
}