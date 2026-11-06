import java.util.concurrent.atomic.AtomicBoolean;

// Alternative implementation using one AtomicBoolean per seat.
public class AtomicSeatBooking {
    private final AtomicBoolean[] booked;

    AtomicSeatBooking(int seats) {
        booked = new AtomicBoolean[seats];
        for (int i = 0; i < seats; i++) booked[i] = new AtomicBoolean(false);
    }

    boolean book(int seat) {
        return booked[seat].compareAndSet(false, true);
    }

    public static void main(String[] args) {
        AtomicSeatBooking cinema = new AtomicSeatBooking(10);
        System.out.println("First booking for seat 3: " + cinema.book(3));
        System.out.println("Second booking for seat 3: " + cinema.book(3));
    }
}
