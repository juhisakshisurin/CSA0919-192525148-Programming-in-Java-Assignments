import java.util.concurrent.CountDownLatch;

class Wallet {
    final int id;
    private int balance;
    Wallet(int id, int balance) { this.id = id; this.balance = balance; }
    int getBalance() { return balance; }
    void debit(int amount) {
        if (amount <= 0 || balance < amount)
            throw new IllegalArgumentException("Invalid amount or insufficient balance");
        balance -= amount;
    }
    void credit(int amount) { balance += amount; }
}

public class WalletTransferDemo {
    // Deliberately unsafe: both threads hold one lock before requesting the other.
    static void unsafeTransfer(Wallet from, Wallet to, int amount,
                               CountDownLatch bothHaveFirstLock) {
        synchronized (from) {
            bothHaveFirstLock.countDown();
            try { bothHaveFirstLock.await(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
            synchronized (to) {
                from.debit(amount);
                to.credit(amount);
            }
        }
    }

    // Safe: all transfers acquire wallet monitors in ascending ID order.
    static void safeTransfer(Wallet from, Wallet to, int amount) {
        Wallet first = from.id < to.id ? from : to;
        Wallet second = from.id < to.id ? to : from;
        synchronized (first) {
            synchronized (second) {
                from.debit(amount);
                to.credit(amount);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("PART A - UNSAFE OPPOSITE-ORDER TRANSFERS");
        Wallet a = new Wallet(1, 1000), b = new Wallet(2, 1000);
        CountDownLatch ready = new CountDownLatch(2);
        Thread t1 = new Thread(() -> unsafeTransfer(a, b, 10, ready), "A-to-B");
        Thread t2 = new Thread(() -> unsafeTransfer(b, a, 10, ready), "B-to-A");
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start(); t2.start();
        t1.join(1000); t2.join(1000);
        System.out.println("Both transfer threads still waiting: " +
                (t1.isAlive() && t2.isAlive()));
        System.out.println("Thread states: " + t1.getState() + ", " + t2.getState());

        System.out.println("\\nPART A - CONSISTENT LOCK ORDERING");
        Wallet x = new Wallet(1, 1000), y = new Wallet(2, 1000);
        Thread s1 = new Thread(() -> safeTransfer(x, y, 100));
        Thread s2 = new Thread(() -> safeTransfer(y, x, 50));
        s1.start(); s2.start(); s1.join(); s2.join();
        System.out.println("Safe transfers completed: " + (!s1.isAlive() && !s2.isAlive()));
        System.out.println("Total balance preserved: " + (x.getBalance() + y.getBalance()));
    }
}
