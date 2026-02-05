import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// this calls implements the server interface
public class ServerImpl implements UpperCaseServer {
    //using the constructure
    //the server must export objects to be remotely available
    public ServerImpl() throws RemoteException{
        // to export this remote object to make it available
        // to receive incoming calls,
        //using the particular supplied port.
        // port: the port to export the object on
        UnicastRemoteObject.exportObject(this, 0);
    }

    //The method defined in the interface must be implemented.
    @Override
    public String toUpperCase(String str) throws RemoteException {
        return str.toUpperCase();
    }

    @Override
    public String toLower(String str) throws RemoteException{
        return str.toLowerCase();
    }
    public int sum( int x, int y) {
        return x+y;
    }
}