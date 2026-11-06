import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBookingDemo {
    private final boolean[] booked;
    private int bookingsMade;

    SeatBookingDemo(int seats) {
        booked = new boolean[seats];
    }

    // Unsynchronized version: intentionally unsafe.
    boolean bookUnsafe(int seat, CountDownLatch ready, CountDownLatch release) {
        if (!booked[seat]) {
            ready.countDown();
            try {
                release.await(); // test barrier: all contenders read false before any writes
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
            booked[seat] = true;
            bookingsMade++;
            return true;
        }
        return false;
    }

    // Synchronized method: locks the complete method on this object.
    synchronized boolean bookSynchronizedMethod(int seat) {
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    // Synchronized block: only the check-then-book critical section is locked.
    boolean bookSynchronizedBlock(int seat) {
        if (seat < 0 || seat >= booked.length) return false; // validation outside lock
        synchronized (this) {
            if (booked[seat]) return false;
            booked[seat] = true;
            bookingsMade++;
            return true;
        }
    }

    int getBookingsMade() {
        return bookingsMade;
    }

    static int runUnsafeRaceOnce() throws InterruptedException {
        final int usersCount = 100;
        SeatBookingDemo screen = new SeatBookingDemo(10);
        CountDownLatch ready = new CountDownLatch(usersCount);
        CountDownLatch release = new CountDownLatch(1);
        Thread[] users = new Thread[usersCount];

        for (int i = 0; i < usersCount; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> screen.bookUnsafe(seat, ready, release));
            users[i].start();
        }
        ready.await();
        release.countDown();
        for (Thread t : users) t.join();
        return screen.getBookingsMade();
    }

    static int runSynchronizedMethodOnce() throws InterruptedException {
        SeatBookingDemo screen = new SeatBookingDemo(10);
        Thread[] users = new Thread[100];
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> screen.bookSynchronizedMethod(seat));
            users[i].start();
        }
        for (Thread t : users) t.join();
        return screen.getBookingsMade();
    }

    static int runSynchronizedBlockOnce() throws InterruptedException {
        SeatBookingDemo screen = new SeatBookingDemo(10);
        Thread[] users = new Thread[100];
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> screen.bookSynchronizedBlock(seat));
            users[i].start();
        }
        for (Thread t : users) t.join();
        return screen.getBookingsMade();
    }

    // Measures 10,000 calls using 10 worker threads and 10,000 distinct seats.
    static long measure(String version) throws InterruptedException {
        final int bookings = 10_000;
        final int workers = 10;
        SeatBookingDemo screen = new SeatBookingDemo(bookings);
        Thread[] threads = new Thread[workers];
        long start = System.nanoTime();

        for (int w = 0; w < workers; w++) {
            final int worker = w;
            threads[w] = new Thread(() -> {
                for (int seat = worker; seat < bookings; seat += workers) {
                    if (version.equals("method")) screen.bookSynchronizedMethod(seat);
                    else screen.bookSynchronizedBlock(seat);
                }
            });
            threads[w].start();
        }
        for (Thread t : threads) t.join();
        return (System.nanoTime() - start) / 1_000_000;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== UNSYNCHRONIZED RACE: 10 RUNS ===");
        for (int i = 1; i <= 10; i++) {
            System.out.println("Run " + i + " - Seats sold: " + runUnsafeRaceOnce());
        }

        System.out.println("\n=== SYNCHRONIZED METHOD ===");
        System.out.println("Seats sold: " + runSynchronizedMethodOnce());

        System.out.println("\n=== SYNCHRONIZED BLOCK ===");
        System.out.println("Seats sold: " + runSynchronizedBlockOnce());

        System.out.println("\n=== 10,000 BOOKING TIMING (milliseconds) ===");
        System.out.println("Synchronized method: " + measure("method") + " ms");
        System.out.println("Synchronized block: " + measure("block") + " ms");
        System.out.println("Timing varies by machine and JVM; repeat and report your own measurements.");
    }
}
