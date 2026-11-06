import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

// Version 1: a scanner station is represented by extending Thread.
class ScannerStationThread extends Thread {
    private final int stationId;
    private final int scripts;
    private final AtomicInteger total;

    ScannerStationThread(int stationId, int scripts, AtomicInteger total) {
        super("Station-" + stationId);
        this.stationId = stationId;
        this.scripts = scripts;
        this.total = total;
    }

    @Override
    public void run() {
        try {
            // Simulate scanning work.
            Thread.sleep(ThreadLocalRandom.current().nextInt(20, 100));
            total.addAndGet(scripts);
            System.out.println(getName() + " finished scanning " + scripts + " scripts.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(getName() + " was interrupted.");
        }
    }
}

// Version 2: scanning work is a Runnable task, separate from the Thread object.
class ScannerStationTask implements Runnable {
    private final int stationId;
    private final int scripts;
    private final AtomicInteger total;

    ScannerStationTask(int stationId, int scripts, AtomicInteger total) {
        this.stationId = stationId;
        this.scripts = scripts;
        this.total = total;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(20, 100));
            total.addAndGet(scripts);
            System.out.println("Runnable Station-" + stationId
                    + " finished scanning " + scripts + " scripts.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Runnable Station-" + stationId + " was interrupted.");
        }
    }
}

public class ScannerLifecycleDemo {
    private static void runThreadSubclassVersion() throws InterruptedException {
        System.out.println("\n=== EXTENDING THREAD ===");
        AtomicInteger total = new AtomicInteger();
        ScannerStationThread[] stations = new ScannerStationThread[4];

        for (int i = 0; i < 4; i++) {
            stations[i] = new ScannerStationThread(i + 1, 10, total);
            stations[i].start();
        }

        System.out.println("Station-1 alive immediately after start: "
                + stations[0].isAlive());

        // sleep() pauses the main thread; it does not stop the worker threads.
        Thread.sleep(15);

        for (ScannerStationThread station : stations) {
            station.join();
        }
        System.out.println("All Thread stations completed. Total scripts: " + total.get());
        System.out.println("Station-1 alive after join: " + stations[0].isAlive());
    }

    private static void runRunnableVersion() throws InterruptedException {
        System.out.println("\n=== IMPLEMENTING RUNNABLE ===");
        AtomicInteger total = new AtomicInteger();
        Thread[] stations = new Thread[4];

        for (int i = 0; i < 4; i++) {
            Runnable task = new ScannerStationTask(i + 1, 10, total);
            stations[i] = new Thread(task, "Runnable-Station-" + (i + 1));
            stations[i].start();
        }

        for (Thread station : stations) {
            station.join();
        }
        System.out.println("All Runnable stations completed. Total scripts: " + total.get());
    }

    private static String oneCompletionOrder(int run) throws InterruptedException {
        List<String> finishOrder = Collections.synchronizedList(new ArrayList<>());
        Thread[] stations = new Thread[4];

        for (int i = 0; i < 4; i++) {
            final int id = i + 1;
            stations[i] = new Thread(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(5, 90));
                    finishOrder.add("S" + id);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Order-Station-" + id);
            stations[i].start();
        }
        for (Thread station : stations) station.join();
        return "Run " + run + ": " + String.join(" -> ", finishOrder);
    }

    private static void runTenOrderExperiments() throws InterruptedException {
        System.out.println("\n=== COMPLETION ORDER: 10 RUNS ===");
        for (int i = 1; i <= 10; i++) {
            System.out.println(oneCompletionOrder(i));
        }
    }

    private static void runPriorityExperiment() throws InterruptedException {
        System.out.println("\n=== THREAD PRIORITY EXPERIMENT ===");
        for (int run = 1; run <= 3; run++) {
            List<String> order = Collections.synchronizedList(new ArrayList<>());
            Thread[] stations = new Thread[4];
            for (int i = 0; i < 4; i++) {
                final int id = i + 1;
                stations[i] = new Thread(() -> {
                    try {
                        Thread.sleep(20);
                        order.add("S" + id);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                stations[i].setPriority(run == 1 ? Thread.NORM_PRIORITY
                        : (i == 0 ? Thread.MAX_PRIORITY
                        : (i == 3 ? Thread.MIN_PRIORITY : Thread.NORM_PRIORITY)));
                stations[i].start();
            }
            for (Thread station : stations) station.join();
            System.out.println("Priority run " + run + ": " + String.join(" -> ", order));
        }
        System.out.println("Priority is only a scheduling hint; it does not guarantee finish order.");
    }

    private static void demonstrateRunVersusStart() throws InterruptedException {
        System.out.println("\n=== run() VERSUS start() ===");
        Thread direct = new Thread(() ->
                System.out.println("run() executes on: " + Thread.currentThread().getName()),
                "Worker-Direct");
        direct.run(); // Executes synchronously on main; no new thread is created.

        Thread started = new Thread(() ->
                System.out.println("start() executes on: " + Thread.currentThread().getName()),
                "Worker-Started");
        started.start();
        started.join();
    }

    private static void demonstrateDaemon() throws InterruptedException {
        System.out.println("\n=== DAEMON THREAD DEMONSTRATION ===");
        Thread daemon = new Thread(() -> {
            try {
                System.out.println("Daemon station started.");
                Thread.sleep(2000);
                System.out.println("Daemon station completed its batch.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Daemon-Station");
        daemon.setDaemon(true); // Must be set before start().
        daemon.start();
        Thread.sleep(100);
        System.out.println("Main thread ends without joining the daemon.");
        // No join: once main and all other non-daemon threads end, JVM may exit.
    }

    public static void main(String[] args) throws InterruptedException {
        runThreadSubclassVersion();
        runRunnableVersion();
        runTenOrderExperiments();
        runPriorityExperiment();
        demonstrateRunVersusStart();
        demonstrateDaemon();
    }
}
