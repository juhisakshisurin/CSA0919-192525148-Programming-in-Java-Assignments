public class TokenCounterWhileVsIf {
    public static void main(String[] args) {
        System.out.println("Correct: while (count == 0) wait();");
        System.out.println("Incorrect: if (count == 0) wait();");
        System.out.println("A while loop rechecks the condition after waking.");
        System.out.println("An if statement does not protect against spurious wake-ups");
        System.out.println("or another thread changing the buffer before the lock is reacquired.");
    }
}
