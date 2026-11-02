import java.util.Map;

/** Vehicle used by the parking system (kept minimal; hierarchy is Task 6 scope). */
public class Vehicle {
    // Static constant table, filled once when the class is initialised (static block)
    private static final Map<String, Integer> RATE_PER_HOUR;

    static {
        System.out.println("[Vehicle static block] executed once at class initialisation");
        RATE_PER_HOUR = Map.of("TWO_WHEELER", 10, "CAR", 30, "EV", 30);
    }

    private final String regNo;      // instance-specific
    private final String type;       // instance-specific
    private String ownerName;        // mutable state, used to demonstrate parameter passing

    public Vehicle(String regNo, String type, String ownerName) {
        this.regNo = normalizeRegNo(regNo);
        this.type = type;
        this.ownerName = ownerName;
    }

    /** Static utility: needs no Vehicle object and uses no instance data. */
    public static String normalizeRegNo(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("Registration number is required");
        }
        return raw.replaceAll("[\\s-]", "").toUpperCase();
    }

    /** Static utility: independent fee calculation, every started hour is charged. */
    public static long billableHours(long minutes) {
        return (minutes + 59) / 60;   // integer ceiling, no floating-point casts
    }

    public static int rateFor(String type) {
        Integer r = RATE_PER_HOUR.get(type);
        if (r == null) throw new IllegalArgumentException("Unknown vehicle type: " + type);
        return r;
    }

    public String getRegNo() { return regNo; }
    public String getType() { return type; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    @Override public String toString() { return type + "[" + regNo + ", owner=" + ownerName + "]"; }
}
