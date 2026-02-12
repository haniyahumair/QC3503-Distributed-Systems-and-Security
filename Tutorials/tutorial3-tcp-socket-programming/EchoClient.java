import java.io.IOException;import
java.io.ObjectInputStream; import
java.io.ObjectOutputStream; import
java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {
    public void runClient(){
        // connect to the server using port number
        // localhost = 127.0.0.1
        // to find another computer IP address:
        // In the Command Prompt, type 'ipconfig', find the IPv4 address.
        try {
            // for writing object to server
            while (true){
                Socket socket = new Socket("localhost",50000);
                ObjectOutputStream outputStreamToServer = new ObjectOutputStream(socket.getOutputStream());
                // for reading message from the server
                ObjectInputStream inputStreamFromServer = new ObjectInputStream(socket.getInputStream());

                Scanner scanner = new Scanner(System.in);
                System.out.println("Input your words:");
                String string = scanner.nextLine();
                // send message to the server
                outputStreamToServer.writeObject(string);
                // read results from the server
                try {
                    String result = (String) inputStreamFromServer.readObject();
                    System.out.println("Received from server: "+result);
                    socket.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (UnknownHostException e) {
            e.printStackTrace(); } catch
        (IOException e) {
            e.printStackTrace();
        }
    }
}