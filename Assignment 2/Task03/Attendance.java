import java.util.HashSet;
import java.util.Set;

public class Attendance {

    // Deliberately incorrect version: equals() exists, hashCode() does not.
    static class BrokenStudent {
        final String regNo;
        final String name;

        BrokenStudent(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BrokenStudent)) return false;
            BrokenStudent other = (BrokenStudent) o;
            return regNo.equals(other.regNo);
        }
        // TODO 1 intentionally left incomplete in this test class.
    }

    // Correct version: equality and hashing use the same identity field.
    static class Student {
        private final String regNo;
        private final String name;

        Student(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student)) return false;
            Student other = (Student) o;
            return regNo.equals(other.regNo);
        }

        // TODO 1 completed.
        @Override
        public int hashCode() {
            return regNo.hashCode();
        }

        @Override
        public String toString() {
            return regNo + " " + name;
        }
    }

    // Additional test class: hashCode depends on a mutable register number.
    static class MutableStudent {
        String regNo;
        MutableStudent(String regNo) { this.regNo = regNo; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MutableStudent)) return false;
            return regNo.equals(((MutableStudent) o).regNo);
        }

        @Override
        public int hashCode() {
            return regNo.hashCode();
        }
    }

    private static int spread(int h) {
        return h ^ (h >>> 16);
    }

    private static int bucket(String value) {
        return spread(value.hashCode()) & 15;
    }

    private static int identityBucket(Object value) {
        return spread(System.identityHashCode(value)) & 15;
    }

    // Generate two equal students whose identity hashes land in the same
    // initial HashSet table bucket (capacity 16).
    private static BrokenStudent[] sameBucketPair() {
        BrokenStudent first = new BrokenStudent("192311001", "Arun");
        int target = identityBucket(first);
        for (int i = 0; i < 100000; i++) {
            BrokenStudent candidate =
                new BrokenStudent("192311001", "Arun");
            if (identityBucket(candidate) == target) {
                return new BrokenStudent[] { first, candidate };
            }
        }
        throw new IllegalStateException("Could not find same-bucket pair");
    }

    // Generate equal students whose identity hashes land in different
    // initial table buckets, exposing the missing hashCode implementation.
    private static BrokenStudent[] differentBucketPair() {
        BrokenStudent first = new BrokenStudent("192311001", "Arun");
        int target = identityBucket(first);
        for (int i = 0; i < 100000; i++) {
            BrokenStudent candidate =
                new BrokenStudent("192311001", "Arun");
            if (identityBucket(candidate) != target) {
                return new BrokenStudent[] { first, candidate };
            }
        }
        throw new IllegalStateException("Could not find different-bucket pair");
    }

    private static void testOneAppearsToWork() {
        System.out.println("\nTEST 1 - Same object scanned twice (broken class)");
        BrokenStudent scannedStudent =
                new BrokenStudent("192311001", "Arun");
        Set<BrokenStudent> set = new HashSet<>();
        set.add(scannedStudent);
        set.add(scannedStudent); // same object reference scanned again

        System.out.println("Same object reference inserted twice: true");
        System.out.println("Observed HashSet size: " + set.size());
        System.out.println("Prediction: 1, because HashSet recognizes "
                + "the same object reference.");
    }

    private static void testTwoExposesDuplicate() {
        System.out.println("\nTEST 2 - Different-bucket duplicate (broken class)");
        BrokenStudent[] pair = differentBucketPair();
        Set<BrokenStudent> set = new HashSet<>();
        set.add(pair[0]);
        set.add(pair[1]);

        System.out.println("Both objects have same regNo: "
                + pair[0].regNo.equals(pair[1].regNo));
        System.out.println("Bucket indexes: "
                + identityBucket(pair[0]) + " and "
                + identityBucket(pair[1]));
        System.out.println("Observed HashSet size: " + set.size());
        System.out.println("Prediction: 2, because HashSet searches "
                + "the bucket selected by hashCode().");
    }

    private static void testThreeMutation() {
        System.out.println("\nTEST 3 - Mutating a key after insertion");
        MutableStudent student = new MutableStudent("REG-A");
        Set<MutableStudent> set = new HashSet<>();
        set.add(student);

        int oldBucket = bucket(student.regNo);
        student.regNo = "REG-B";
        int newBucket = bucket(student.regNo);

        // If these two values happen to share a bucket, choose another
        // valid register number to make the changed bucket deterministic.
        int suffix = 0;
        while (newBucket == oldBucket) {
            student.regNo = "REG-B-" + (++suffix);
            newBucket = bucket(student.regNo);
        }

        System.out.println("Stored object register number changed.");
        System.out.println("Original bucket: " + oldBucket);
        System.out.println("New bucket: " + newBucket);
        System.out.println("contains(student): " + set.contains(student));
        System.out.println("remove(student): " + set.remove(student));
        System.out.println("Set size after failed lookup/removal: " + set.size());
        System.out.println("Prediction: contains and remove return false "
                + "because the object remains in its old bucket.");
    }

    private static void testCorrectedClass() {
        System.out.println("\nCORRECTED IMPLEMENTATION - hashCode() added");
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));

        System.out.println("Unique students: " + present.size());
        System.out.println("Expected output: Unique students: 2");
    }

    private static void showSuppliedProgramBeforeFix() {
        System.out.println("\nSUPPLIED COMPLETE-THE-CODE EXAMPLE - BEFORE TODO 1");
        Set<BrokenStudent> present = new HashSet<>();
        present.add(new BrokenStudent("192311001", "Arun"));
        present.add(new BrokenStudent("192311002", "Divya"));
        present.add(new BrokenStudent("192311001", "Arun"));
        System.out.println("Unique students before hashCode(): "
                + present.size());
        System.out.println("Note: This particular three-object example "
                + "can show 2 or 3 depending on identity hash buckets.");
    }

    public static void main(String[] args) {
        System.out.println("Assignment 2 (CO2) - Task 3");
        System.out.println("Java version: " + System.getProperty("java.version"));
        testOneAppearsToWork();
        testTwoExposesDuplicate();
        testThreeMutation();
        showSuppliedProgramBeforeFix();
        testCorrectedClass();
    }
}
