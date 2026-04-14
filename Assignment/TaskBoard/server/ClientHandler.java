package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import shared.Message;
import java.util.List;

public class ClientHandler implements Runnable{
    private Socket socket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private ConnectionPool pool; // for broadcast message
    private String user_name;
    private TaskBoard taskBoard;

    public ClientHandler(Socket socket, ConnectionPool pool, TaskBoard taskBoard){
        this.socket = socket;
        this.pool = pool;
        this.taskBoard = taskBoard;
        try {
            this.inStream = new ObjectInputStream(socket.getInputStream());
            this.outStream = new
                    ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // run Thread
        try {
            while (true) {

                // pass message data into Message
                Message message = (Message) inStream.readObject();
                String msg_boday = message.getMessageBody();
                this.user_name = message.getUser();
                System.out.println(message.toString());

                if (msg_boday.equalsIgnoreCase("exit")) {
                    // this user should be removed from the list
                    pool.broadcastToAll(new Message(user_name + " has left the board", "[TaskBoard]", "EXIT"));
                    pool.removeUser(this);
                    socket.close();
                    break;
                }
                // custom commands
                String command = message.getCommand();
                if(command.equals("REGISTER")){
                    this.user_name = message.getMessageBody();
                    pool.broadcastToAll(new Message("[TaskBoard] Welcome " + user_name + "!", "[TaskBoard]", "REGISTER"));
                }

                if(command.equals("CREATE")){
                    String description = message.getMessageBody();
                    Task newTask = taskBoard.createTask(description, user_name);
                    pool.broadcastToAll(new Message("[TaskBoard] Task #" + newTask.getId() + " created by " + user_name + ": " + description, "[TaskBoard]", "CREATE"));
                }

                if(command.equals("CREATE_LIST")){
                    String listName = message.getMessageBody();
                    String result = taskBoard.createList(listName);
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "CREATE_LIST"));
                }

                if(command.equals("LIST")){
                    String result = taskBoard.getAllTasks();
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "LIST"));
                }

                if(command.equals("VIEW_LISTS")){
                    String result = taskBoard.getAllLists();
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "VIEW_LISTS"));
                }

                if(command.equals("ADD")){
                    String[] parts = message.getMessageBody().split(" ");
                    int taskId = Integer.parseInt(parts[0]);
                    String listName = parts[1];

                    String result = taskBoard.addTaskToList(taskId, listName);
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "ADD"));
                }

                if(command.equals("MOVE")){
                    String[] parts = message.getMessageBody().split(" ");
                    int taskId = Integer.parseInt(parts[0]);
                    String listName = parts[1];

                    String result = taskBoard.moveTask(taskId, listName);
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "MOVE"));
                }

                if(command.equals("DELETE")){
                    String[] parts = message.getMessageBody().split(" ");
                    String listName = parts[0];

                    String result = taskBoard.deleteList(listName);
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "DELETE"));
                }

                if (command.equals("VIEW")){
                    String[] parts = message.getMessageBody().split(" ");
                    String result = "";
                    if(parts.length == 1){
                        result = taskBoard.viewList(parts[0]);
                    }
                    if (parts.length == 2){
                        result = taskBoard.viewTask(Integer.parseInt(parts[1]));
                    }

                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "VIEW"));
                }

                if (command.equals("PRIORITY")){
                    String[] parts = message.getMessageBody().split(" ");
                    int taskId = Integer.parseInt(parts[0]);
                    String priority = parts[1];

                    Task task = taskBoard.getTask(taskId);
                    task.setPriority(priority);
                    pool.broadcastToAll(new Message("[TaskBoard] Successfuly set priority " + priority + "!", "[TaskBoard]", "PRIORITY"));
                }

                if(command.equals("LABEL")){
                    String[] parts = message.getMessageBody().split(" ");
                    int taskId = Integer.parseInt(parts[0]);
                    String labelName = parts[1];

                    taskBoard.getTask(taskId).addLabel(labelName);
                    String result = taskBoard.addLabel(labelName);
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "LABEL"));
                }

                if(command.equals("LABELS")){
                    String result = taskBoard.getAllLabels();
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "LABELS"));
                }

                if(command.equals("SUBSCRIBE")){
                    String labelName = message.getMessageBody();
                    String result = taskBoard.subscribe(labelName, user_name);
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "SUBSCRIBE"));
                }

                if(command.equals("UNSUBSCRIBE")){
                    String labelName = message.getMessageBody();
                    String result = taskBoard.unsubscribe(labelName, user_name);
                    pool.broadcastToAll(new Message(result, "[TaskBoard]", "UNSUBSCRIBE"));
                }
            }
        }
        catch (Exception e) {
        }
    }
    public void sendMessageToClients(Message msg){
        // output message object
        try {
            outStream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getClientName() {
        return this.user_name;
    }
}