import java.rmi.Naming;
import java.rmi.UnmarshalException;

public class Client {
    private static final int PORT = 6666;

    private static boolean isPrime(long n) {
        for (long i = 2L; i * i < n; ++i) {
            if (n % i == 0L) {
                return false;
            }
        }
        return true;
    }

    private static boolean test(long l, long r) {
        for (long n = l; n <= r; n += 2) {
            for (long i = 2L; i + i <= n; ++i) {
                if (isPrime(i) && isPrime(n - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String arg[]) {
        try {
            GoldbachConjectureInterface s = (GoldbachConjectureInterface) Naming.lookup("//localhost:" + PORT + "/server");

            Pair<Long, Long> testNumber;
            boolean pass = true;
            while (pass) {
                testNumber = s.getTestNumber();
                long testL = testNumber.getLeft();
                long testR = testNumber.getRight();
                if (testNumber.getLeft() < 0L) {
                    System.out.println("The number is " + (-testNumber.getLeft()));
                    break;
                }
                System.out.print("Testing " + testL + " to " + testR + " ...");
                pass = test(testL, testR);
                if (pass) {
                    System.out.println(" Passed!");
                }
            }
            s.setSuccess(true);
        } catch (UnmarshalException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}