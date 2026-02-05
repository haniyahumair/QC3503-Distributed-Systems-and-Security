import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RunClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        // create a client object
        RMIClient client = new RMIClient();

        // the client object can connect to the server by using stub (reference)
        // this will retururn a Remote object
        client.startClient();

        // input something via keyboard
        Scanner in = new Scanner(System.in);
        while (true){
            System.out.println("Input >");
            String line = in.nextLine();
            if(line.equalsIgnoreCase("exit")) break;

            // this calls the remote method from client
            String result = null;
            try {
                result = client.toLower(line);
                System.out.println("Result >"+result);
            } catch (Exception e) {
                System.out.println("Error:"+e.getMessage());
            }
        }
    }
}