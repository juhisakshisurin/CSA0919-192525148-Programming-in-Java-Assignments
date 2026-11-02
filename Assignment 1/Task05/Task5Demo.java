import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class Task5Demo {

    /** Demo-only class: the WRONG design, with an instance ticket counter. */
    static class InstanceCounterFacility {
        private final String name;
        private long counter = 0;               // one copy per facility object
        InstanceCounterFacility(String name) { this.name = name; }
        String issue() { return name + "-ticket#" + (++counter); }
    }

    /** Demo-only class: static counter shared by all objects. */
    static class StaticCounterFacility {
        private static final AtomicLong COUNTER = new AtomicLong(0);
        private final String name;
        StaticCounterFacility(String name) { this.name = name; }
        String issue() { return name + "-ticket#" + COUNTER.incrementAndGet(); }
    }

    /** Demo-only: facility id + local sequence (alternative scheme). */
    static class PrefixedFacility {
        private final String gate;
        private long local = 0;
        PrefixedFacility(String gate) { this.gate = gate; }
        String issue() { return gate + "-" + (++local); }
    }

    // ---- Object passing: the reference VALUE is copied ----
    static void updateOwner(Vehicle v) {
        v.setOwnerName("Updated Owner");                       // mutates the shared object
        v = new Vehicle("TN99ZZ9999", "CAR", "Local Only");    // only the local copy of the reference changes
        System.out.println("  inside method, local v -> " + v);
    }

    static void printTicket(ParkingTicket t) {
        System.out.println("  received " + t);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Static utility method ===");
        System.out.println(Vehicle.normalizeRegNo(" tn-01 ab 1111 "));
        System.out.println("billableHours(130) = " + Vehicle.billableHours(130));

        System.out.println("\n=== 2. Instance-specific occupancy (North vs South) ===");
        ParkingFacility north = new ParkingFacility("North Gate", new int[]{2, 1});
        ParkingFacility south = new ParkingFacility("South Gate", new int[]{2, 1});
        ParkingService northSvc = new ParkingService(north);
        ParkingService southSvc = new ParkingService(south);

        LocalDateTime t0 = LocalDateTime.of(2026, 11, 2, 9, 0);
        Vehicle v1 = new Vehicle("TN01AB1111", "CAR", "Asha");
        Vehicle v2 = new Vehicle("TN02CD2222", "TWO_WHEELER", "Ravi");
        Vehicle v3 = new Vehicle("TN03EF3333", "EV", "Meena");

        System.out.println("=== 3. Returning ParkingTicket from enter() ===");
        ParkingTicket tk1 = northSvc.enter(v1, t0);
        ParkingTicket tk2 = northSvc.enter(v2, t0.plusMinutes(5));
        ParkingTicket tk3 = southSvc.enter(v3, t0.plusMinutes(10));
        System.out.println(tk1);
        System.out.println(tk2);
        System.out.println(tk3);
        System.out.println("North occupancy = " + north.getOccupancy()
                + ", South occupancy = " + south.getOccupancy()
                + ", Total occupancy = " + ParkingFacility.getTotalOccupancy());

        System.out.println("\n=== 4. Passing objects to methods ===");
        printTicket(tk1);
        System.out.println("  before: " + v1);
        updateOwner(v1);
        System.out.println("  after : " + v1 + "  (state changed, caller's variable still refers to the same Vehicle)");

        System.out.println("\n=== 5. Returning ExitReceipt from exit() and life cycle ===");
        ExitReceipt receipt = northSvc.exit("tn01ab1111", t0.plusMinutes(130));
        System.out.println(receipt);
        System.out.println("North occupancy = " + north.getOccupancy()
                + ", South occupancy = " + south.getOccupancy()
                + ", Total occupancy = " + ParkingFacility.getTotalOccupancy());
        System.out.println("Active sessions in north service = " + northSvc.activeCount());
        System.out.println("tk1 closed? " + tk1.isClosed());

        // tk1 and receipt still hold references, so tk1 remains reachable.
        WeakReference<ParkingTicket> weak = new WeakReference<>(tk1);
        tk1 = null;                                  // drop the local variable
        System.gc();                                 // only a request; not guaranteed
        System.out.println("tk1 variable cleared, receipt still references ticket -> weak.get() != null: "
                + (weak.get() != null) + " (reachable via ExitReceipt)");
        System.out.println("Ticket via receipt: " + receipt.getTicket());

        System.out.println("\n=== 6. Static vs instance counter, North Gate and South Gate ===");
        StaticCounterFacility sn = new StaticCounterFacility("North");
        StaticCounterFacility ss = new StaticCounterFacility("South");
        System.out.println("Static : " + sn.issue() + ", " + ss.issue() + ", " + sn.issue());

        InstanceCounterFacility in = new InstanceCounterFacility("North");
        InstanceCounterFacility is = new InstanceCounterFacility("South");
        String a = in.issue(), b = is.issue();
        System.out.println("Instance: " + a + ", " + b + ", " + in.issue());
        System.out.println("Instance duplicate number across facilities: "
                + a.substring(a.indexOf('#')).equals(b.substring(b.indexOf('#'))));

        PrefixedFacility pn = new PrefixedFacility("N"), ps = new PrefixedFacility("S");
        System.out.println("Gate-prefixed: " + pn.issue() + ", " + ps.issue() + ", " + pn.issue());

        System.out.println("\n=== 7. Invalid release and failed allocation leave counters unchanged ===");
        int totalBefore = ParkingFacility.getTotalOccupancy();
        boolean r1 = north.release(5, 0);      // invalid floor
        boolean r2 = north.release(0, 0);      // slot already free (its vehicle exited)
        ParkingFacility tiny = new ParkingFacility("Tiny Lot", new int[]{0});
        int[] none = tiny.allocate("TN00XX0000");   // no free slot -> null
        System.out.println("release(5,0)=" + r1 + ", release(0,0)=" + r2 + ", allocate on full lot="
                + java.util.Arrays.toString(none));
        System.out.println("North occupancy = " + north.getOccupancy()
                + ", South occupancy = " + south.getOccupancy()
                + ", Tiny occupancy = " + tiny.getOccupancy()
                + ", Total occupancy = " + ParkingFacility.getTotalOccupancy()
                + " (before: " + totalBefore + ")");
    }
}
