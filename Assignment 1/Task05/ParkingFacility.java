public class ParkingFacility {
    private final String name;
    private final String[][] slots;      // slots[floor][slot] = reg no, or null if free
    private int occupancy;               // instance-specific: vehicles parked in THIS facility
    private static int totalOccupancy;   // static: vehicles parked across ALL facilities

    public ParkingFacility(String name, int[] slotsPerFloor) {
        this.name = name;
        slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            slots[f] = new String[slotsPerFloor[f]];
        }
    }

    /** Returns {floor, slot} of the first free slot, or null if the facility is full. */
    public int[] allocate(String vehicleNo) {
        for (int f = 0; f < slots.length; f++) {
            for (int s = 0; s < slots[f].length; s++) {
                if (slots[f][s] == null) {
                    slots[f][s] = vehicleNo;
                    occupancy++;                 // successful allocation only
                    totalOccupancy++;
                    return new int[]{f, s};
                }
            }
        }
        return null;
    }

    /** Frees a slot. Returns false if the position is invalid or already free. */
    public boolean release(int floor, int slot) {
        if (floor < 0 || floor >= slots.length || slot < 0 || slot >= slots[floor].length
                || slots[floor][slot] == null) {
            return false;
        }
        slots[floor][slot] = null;
        occupancy--;                     // successful release only
        totalOccupancy--;
        return true;
    }

    public String getName() { return name; }
    public int getOccupancy() { return occupancy; }                      // this facility
    public static int getTotalOccupancy() { return totalOccupancy; }     // all facilities
}
