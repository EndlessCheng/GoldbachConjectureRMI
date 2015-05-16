import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements GoldbachConjectureInterface {
    private static final int PORT = 6666;
    private static final long GAP = 10000L;

    private long testNumber = 4L;
    private boolean success = false;

    @Override
    public Pair<Long, Long> getTestNumber() {
        long temp = testNumber;
        if (isSuccess()) {
            return new Pair<Long, Long>(-testNumber, 0L);
        }
        testNumber = temp + GAP + 2L;
        return new Pair<Long, Long>(temp, temp + GAP);
    }

    @Override
    public void setSuccess(boolean success) throws RemoteException {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public Server() throws RemoteException {
        super();
    }

    public static void main(String args[]) {
        try {
            Server s = new Server();
            LocateRegistry.createRegistry(PORT);
            Naming.rebind("//localhost:" + PORT + "/server", s);
            System.out.println("Server is running...");
        } catch (ExportException e) {
            System.out.println("Port " + PORT + " is using now. (May this server is already running)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}