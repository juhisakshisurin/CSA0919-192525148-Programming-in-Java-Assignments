import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScannerExecutorAlternative {
    public static void main(String[] args) throws Exception {
        int poolSize = 4;
        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        List<Future<Integer>> results = new ArrayList<>();

        for (int station = 1; station <= 4; station++) {
            final int id = station;
            Callable<Integer> scanBatch = () -> {
                System.out.println("Pool station " + id + " scanning 10 scripts.");
                Thread.sleep(50);
                return 10;
            };
            results.add(pool.submit(scanBatch));
        }

        int total = 0;
        for (Future<Integer> result : results) {
            total += result.get();
        }
        pool.shutdown();
        System.out.println("Executor total scripts: " + total);
    }
}
