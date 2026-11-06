import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueAlternative {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> counter = new ArrayBlockingQueue<>(5);
        int[] total = {0};
        Thread cook = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    counter.put(i);
                    System.out.println("Produced " + i + " | size: " + counter.size());
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread[] students = new Thread[2];
        for (int i = 0; i < 2; i++) {
            students[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        int value = counter.take();
                        synchronized (total) { total[0] += value; }
                        System.out.println("Consumed " + value + " | size: " + counter.size());
                    }
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }
        cook.start();
        for (Thread t : students) t.start();
        cook.join();
        for (Thread t : students) t.join();
        System.out.println("Sum of tokens collected: " + total[0] + " (expected 210)");
    }
}
