import java.io.*;
import java.nio.file.*;

public class LineCounterBenchmark {
    static long countUnbuffered(Path path) throws IOException {
        long lines=0; boolean any=false; int b;
        try (FileInputStream in=new FileInputStream(path.toFile())) {
            while ((b=in.read())!=-1) { any=true; if (b=='\n') lines++; }
        }
        if (any && !endsNewline(path)) lines++;
        return lines;
    }
    static long countBuffered(Path path) throws IOException {
        long lines=0; boolean any=false; int b;
        try (BufferedInputStream in=new BufferedInputStream(new FileInputStream(path.toFile()),8192)) {
            while ((b=in.read())!=-1) { any=true; if (b=='\n') lines++; }
        }
        if (any && !endsNewline(path)) lines++;
        return lines;
    }
    static boolean endsNewline(Path p) throws IOException {
        long size=Files.size(p); if(size==0) return false;
        try(RandomAccessFile f=new RandomAccessFile(p.toFile(),"r")) {
            f.seek(size-1); return f.read()=='\n';
        }
    }
    static void generate(Path p, long bytes) throws IOException {
        Files.createDirectories(p.getParent());
        byte[] row=("attendance-record-012345678901234567890123456789012345678901234567890123456789\n").getBytes();
        try(OutputStream out=new BufferedOutputStream(Files.newOutputStream(p))) {
            long written=0;
            while(written+row.length<=bytes){out.write(row);written+=row.length;}
            int remain=(int)(bytes-written); if(remain>0) out.write(row,0,remain);
        }
    }
    static long timed(boolean buffered, Path p) throws IOException {
        long start=System.nanoTime();
        if(buffered) countBuffered(p); else countUnbuffered(p);
        return System.nanoTime()-start;
    }
    public static void main(String[] args) throws Exception {
        Path dir=Path.of("input","line_benchmarks");
        for(int mb:new int[]{1,10,50}) {
            Path p=dir.resolve("attendance_"+mb+"MB.log");
            long bytes=mb*1024L*1024L;
            if(!Files.exists(p)||Files.size(p)!=bytes) generate(p,bytes);
            countUnbuffered(p); countBuffered(p); // warm-up
            long[] u=new long[3], v=new long[3];
            for(int i=0;i<3;i++){u[i]=timed(false,p);v[i]=timed(true,p);}
            System.out.printf("%d MB | %d bytes | unbuffered median %.3f ms | buffered median %.3f ms | lines %d%n",
                mb,Files.size(p),median(u)/1e6,median(v)/1e6,countBuffered(p));
        }
    }
    static long median(long[] a){java.util.Arrays.sort(a);return a[a.length/2];}
}
