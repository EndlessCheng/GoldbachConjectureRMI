import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GoldbachConjectureInterface extends Remote {
    public Pair<Long, Long> getTestNumber() throws RemoteException;

    public void setSuccess(boolean success) throws RemoteException;
}