package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import shared.Message;

public class TaskBoardClient {
    private ObjectInputStream inStream;
    public void startClient(){
        try {
            Socket socket = new Socket("localhost", 50000);
            //Input Output stream to recieve messages
            ObjectOutputStream outStream = new
                    ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());

            // make a new thread to keep reading from server and print out message
            Thread thread = new Thread(this::listenToServer);
            thread.setDaemon(true); // Daemon Thread: if this is the last thread running, it terminate itself.
            thread.start();

            // Asks for name of the user
            Scanner scanner_user = new Scanner(System.in);
            //username comes from the REGISTER command

            // Ask user to wrote some input message- make it running all the time
            Scanner scanner = new Scanner(System.in);
            System.out.println("A client starts...");
            System.out.println("Welcome to Taskboard! Start by creating a username by using the REGISTER command.");
            String user_name = "";
            while (true){
                String str_msg = scanner.nextLine();
                // This message will be put into Message's
                String[] parts = str_msg.split(" ", 2);
                String command = parts[0].toUpperCase();
                String body = parts.length > 1 ? parts[1] : "";
                Message instanceMessage = new Message(body, user_name, command);

                // store the name locally
                if(command.equals("REGISTER")){
                    user_name = body;
                }

                // Now send the message to the server
                outStream.writeObject(instanceMessage);

                // Break the loop when input is "exit"
                if (str_msg.equalsIgnoreCase("exit")){
                    outStream.writeObject(new Message("exit", user_name, "EXIT"));
                    socket.close();
                    break;
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void listenToServer() {
        // Then read objects from the server
        try {
            // Keep reading from server and print out.
            while (true){
                Message inMessage = (Message) inStream.readObject();
                System.out.println(inMessage.getMessageBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}