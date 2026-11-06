import java.nio.file.*;
public class SearchGrowthBenchmark {
    public static void main(String[] args) throws Exception {
        Path p=Path.of("input","growth_passes.csv");
        for(int n:new int[]{1000,10000,50000,100000}) {
            Files.deleteIfExists(p);
            PassStore s=new PassStore(p);
            try(var w=Files.newBufferedWriter(p)) {
                for(int i=0;i<n;i++) {
                    w.write(String.format("P%06d,192300001,Student%d,%d,4500.0%n",i,i,(i%15)+1));
                }
            }
            String target=String.format("P%06d",n-1); // worst-case hit at end of file
            long[] samples=new long[5];
            for(int k=0;k<5;k++){
                long t=System.nanoTime(); s.find(target); samples[k]=System.nanoTime()-t;
            }
            java.util.Arrays.sort(samples);
            System.out.printf("%d records | target at EOF | median %.3f ms%n",n,samples[2]/1e6);
        }
    }
}
