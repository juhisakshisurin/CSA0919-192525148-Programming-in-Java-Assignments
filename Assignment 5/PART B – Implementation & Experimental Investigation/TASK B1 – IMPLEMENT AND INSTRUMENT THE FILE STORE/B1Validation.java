import java.nio.file.*;
import java.util.*;
public class B1Validation {
    public static void main(String[] args) throws Exception {
        Path f=Path.of("input","validation_passes.csv");
        Files.createDirectories(f.getParent()); Files.deleteIfExists(f);
        PassStore s=new PassStore(f);
        s.add("P1001","192311001","Arun",12,4500);
        s.add("P1003","192311003","Divya",7,3800);
        Files.writeString(f,"P1002,DAMAGED LINE\\n",StandardOpenOption.APPEND);
        System.out.println("FIND_EXISTING="+Arrays.toString(s.find("P1003")));
        System.out.println("FIND_MISSING="+Arrays.toString(s.find("P9999")));
        System.out.println("FIND_DAMAGED="+Arrays.toString(s.find("P1002")));
        var route=s.listByRoute(7);
        System.out.println("LIST_ROUTE_7_COUNT="+route.size());
        for(String[] row:route) System.out.println("LIST_ROUTE_7="+Arrays.toString(row));
    }
}
