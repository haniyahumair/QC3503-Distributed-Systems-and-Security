import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class RMIClient {
    private UpperCaseServer server; // this is my stub
    public RMIClient() {}
    //public RMIClient() throws RemoteException, NotBoundException{
        // // to make connect to the server
        // // getRegistry returns a reference or a stub to
        // // the remote object registry
        // // host - host for the remote registry
        // // port - port on which the registry accepts requests
        // Registry registry = LocateRegistry.getRegistry("localhost",1099);
        // // name - the name for the remote reference to look up
        // // returns a reference to a remote object
        // // Registry returns a Remote object (UpperCaseServer extends
        // // from Remote)
        // server = (UpperCaseServer) registry.lookup("Server");
    // }

    public void startClient() throws RemoteException, NotBoundException{
        // to make connect to the server
        // getRegistry returns a reference or a stub to
        // the remote object registry
        // host - host for the remote registry
        // port - port on which the registry accepts requests
        Registry registry = LocateRegistry.getRegistry("localhost",1099);

        // name - the name for the remote reference to look up
        // returns a reference to a remote object
        // Registry returns a Remote object (UpperCaseServer extends
        // from Remote)
        server = (UpperCaseServer) registry.lookup("Server");
    }

    // the method to print upercase
    public String toUpperCase(String argument) throws RemoteException{
        // After the client connects to the server, we can call
        // the remote method.
        String result = server.toUpperCase(argument);

        return result;
    }

    public String toLower(String arg) throws RemoteException{
        String result = server.toLower(arg);

        return result;
    }
}