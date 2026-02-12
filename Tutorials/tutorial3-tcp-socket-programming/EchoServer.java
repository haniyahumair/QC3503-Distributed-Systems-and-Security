import java.io.IOException; import
java.io.ObjectInputStream; import
java.io.ObjectOutputStream; import
java.net.ServerSocket; import
java.net.Socket;

public class EchoServer {
    public void start(){
        System.out.println("Starting Server....");

        try {
            // create a Server Socket listening to the port 50000
            ServerSocket serverSocket = new ServerSocket(50000);
            while (true){
                // Call accept() method to wait for connections
                Socket socket = serverSocket.accept();
                // after connection to client is made, the following code will run
                System.out.println("Client connected.");
                // Read and write to socket IP streams
                // read socket input stream that client sent
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                // output stream data to client
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                // read data from client
                String read_str = (String) inputStream.readObject();
                System.out.println("Received from client: "+read_str);

                // do something about the data
                String result = read_str.toUpperCase();
                // output results to client
                outputStream.writeObject(result);
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
