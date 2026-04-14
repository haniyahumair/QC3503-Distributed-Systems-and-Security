package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.ConnectionPool;
import server.TaskBoard;
import server.ClientHandler;

public class TaskBoardServer {
    public void start() {
        try {
            // Implement a server socket which waits for request to come in over the network. This creates a server socket bounded to the specified port.
            ServerSocket server_socket = new ServerSocket(50000);

            // Make a connection pool for all the comming clients. This makes sure there is a pool instance for this port
            ConnectionPool cp = new ConnectionPool();

            System.out.println("TaskBoard Server started on port 50000...");

            //add taskboard
            TaskBoard taskBoard = new TaskBoard();

            while (true){
                // Listens for a connection to be made to this socket and accepts it.
                Socket socket = server_socket.accept();

                // Create server_socket_handler and start it.
                ClientHandler csh = new ClientHandler(socket, cp, taskBoard);
                cp.addConnects(csh); // add ClientHandler into connection pool

                Thread th = new Thread(csh);
                th.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}