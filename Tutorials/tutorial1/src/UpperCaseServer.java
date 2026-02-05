import java.rmi.Remote;
import java.rmi.RemoteException;

//create the interface that extends from Remote;
//if no throws RemoteException, it will get invalid interface
public interface UpperCaseServer extends Remote  {
    String toUpperCase(String str) throws RemoteException;
    String toLower(String str) throws RemoteException;
}