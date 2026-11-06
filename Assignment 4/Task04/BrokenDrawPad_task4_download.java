import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BrokenDrawPad extends Frame {
    public BrokenDrawPad() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseclicked(MouseEvent e) {
                // Deliberate spelling error for Task 4.4 demonstration.
            }
        });
        setSize(400, 300);
    }

    public static void main(String[] args) {
        new BrokenDrawPad().setVisible(true);
    }
}
