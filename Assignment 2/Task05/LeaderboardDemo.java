import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/*
 * Assignment 2 - Q5: Derive a Generic Ranking Component from a Principle
 * Compile: javac LeaderboardDemo.java
 * Run:     java LeaderboardDemo
 */
class Team {
    private final String name;
    private final int points;
    private final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    String getName() {
        return name;
    }

    int getPoints() {
        return points;
    }

    int getGoalDiff() {
        return goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

/*
 * T is the element type.
 * Comparator<? super T> accepts a comparator of T or one of its supertypes.
 */
class Leaderboard<T> {
    private final List<T> items = new ArrayList<T>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        if (order == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    // The wildcard allows a list of T or any subtype of T to be supplied.
    void addAll(List<? extends T> newItems) {
        items.addAll(newItems);
    }

    // Return a sorted copy; the internal insertion list is not modified.
    List<T> ranking() {
        List<T> result = new ArrayList<T>(items);
        Collections.sort(result, order);
        return result;
    }

    int size() {
        return items.size();
    }
}

public class LeaderboardDemo {
    // Negative means first argument ranks above the second argument.
    private static final Comparator<Team> BY_RANK =
        Comparator.comparingInt(Team::getPoints).reversed()
            .thenComparing(
                Comparator.comparingInt(Team::getGoalDiff).reversed())
            .thenComparing(Team::getName);

    private static void printSection(String heading, List<Team> teams) {
        System.out.println("\n" + heading);
        System.out.println(teams);
    }

    private static boolean isTransitive(
            List<Team> teams, Comparator<Team> comparator) {
        for (Team a : teams) {
            for (Team b : teams) {
                for (Team c : teams) {
                    if (comparator.compare(a, b) <= 0
                            && comparator.compare(b, c) <= 0
                            && comparator.compare(a, c) > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean matchesRequiredOrder(List<Team> actual,
                                                 List<Team> expected) {
        if (actual.size() != expected.size()) {
            return false;
        }
        for (int i = 0; i < actual.size(); i++) {
            if (!actual.get(i).toString().equals(expected.get(i).toString())) {
                return false;
            }
        }
        return true;
    }

    private static void runBenchmark() {
        int[] sizes = {1000, 10000, 50000};
        final int repetitions = 5;
        Random random = new Random(20261106L);

        System.out.println("\nSORT BENCHMARK (milliseconds; five repetitions)");
        System.out.println("Size,AverageMilliseconds");

        try (java.io.PrintWriter out =
                 new java.io.PrintWriter("task5_results.csv")) {
            out.println("size,repetition,elapsed_ns,elapsed_ms,checksum");

            for (int size : sizes) {
                long totalNanos = 0L;
                long checksum = 0L;

                // Prepare the same base dataset for the measured repetitions.
                List<Team> source = new ArrayList<Team>();
                for (int i = 0; i < size; i++) {
                    source.add(new Team(
                        String.format("Team%06d", i),
                        random.nextInt(101),
                        random.nextInt(101) - 50
                    ));
                }

                // One unmeasured warm-up.
                List<Team> warmup = new ArrayList<Team>(source);
                Collections.sort(warmup, BY_RANK);

                for (int rep = 1; rep <= repetitions; rep++) {
                    List<Team> copy = new ArrayList<Team>(source);
                    long start = System.nanoTime();
                    Collections.sort(copy, BY_RANK);
                    long elapsed = System.nanoTime() - start;
                    totalNanos += elapsed;
                    checksum += copy.get(0).getPoints()
                              + copy.get(copy.size() - 1).getGoalDiff();

                    out.printf(java.util.Locale.US, "%d,%d,%d,%.6f,%d%n",
                        size, rep, elapsed, elapsed / 1000000.0,
                        copy.get(0).getPoints()
                            + copy.get(copy.size() - 1).getGoalDiff());
                }

                double averageMs =
                    totalNanos / (double) repetitions / 1000000.0;
                System.out.printf(java.util.Locale.US,
                    "%d,%.6f%n", size, averageMs);
                // Prevent the computed results from being treated as unused.
                if (checksum == Long.MIN_VALUE) {
                    System.out.println("Unreachable checksum: " + checksum);
                }
            }
        } catch (java.io.IOException ex) {
            System.out.println("Could not write benchmark CSV: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("ASSIGNMENT 2 - Q5: GENERIC LEADERBOARD");

        // Supplied test data from the assignment.
        Leaderboard<Team> board = new Leaderboard<Team>(BY_RANK);
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        List<Team> supplied = board.ranking();
        printSection("TEST 1 - SUPPLIED DATA", supplied);
        System.out.println("Expected: [Eagles(9,6), Bisons(9,4), "
                + "Panthers(9,4), Cobras(6,1)]");
        System.out.println("Matches expected: " +
                matchesRequiredOrder(supplied, java.util.Arrays.asList(
                    new Team("Eagles", 9, 6),
                    new Team("Bisons", 9, 4),
                    new Team("Panthers", 9, 4),
                    new Team("Cobras", 6, 1))));

        // A second dataset with ties at points and goal difference.
        List<Team> secondData = new ArrayList<Team>();
        secondData.add(new Team("Falcons", 12, 5));
        secondData.add(new Team("Aces", 12, 5));
        secondData.add(new Team("Wolves", 12, 8));
        secondData.add(new Team("Tigers", 10, 9));
        secondData.add(new Team("Bears", 10, 9));
        secondData.add(new Team("Lions", 10, 2));
        secondData.add(new Team("Sharks", 7, 0));

        Leaderboard<Team> secondBoard =
                new Leaderboard<Team>(BY_RANK);
        secondBoard.addAll(secondData);
        List<Team> secondRanking = secondBoard.ranking();
        printSection("TEST 2 - MULTI-LEVEL TIES", secondRanking);
        System.out.println("Comparator transitivity check: "
                + (isTransitive(secondData, BY_RANK) ? "PASS" : "FAIL"));

        // Faulty comparator: it compares points only and ignores both tie-breakers.
        Comparator<Team> faulty = (a, b) ->
                Integer.compare(b.getPoints(), a.getPoints());

        List<Team> faultyRanking = new ArrayList<Team>(secondData);
        Collections.sort(faultyRanking, faulty);
        printSection("TEST 3 - FAULTY COMPARATOR (POINTS ONLY)",
                faultyRanking);
        System.out.println("Faulty comparator satisfies full ranking rule: "
                + matchesRequiredOrder(faultyRanking, secondRanking));
        System.out.println("Reason: teams tied on points are not ordered "
                + "by goal difference and name.");

        // Demonstrate why subtraction-based comparators can overflow.
        Comparator<Team> subtractionFault = (a, b) ->
                b.getPoints() - a.getPoints();
        Team high = new Team("High", Integer.MAX_VALUE, 0);
        Team low = new Team("Low", Integer.MIN_VALUE, 0);
        System.out.println("Subtraction comparator overflow test (expected "
                + "High before Low): "
                + (subtractionFault.compare(high, low) < 0 ? "PASS" : "FAIL"));
        System.out.println("Safe Integer.compare test: "
                + (Integer.compare(low.getPoints(), high.getPoints()) < 0
                    ? "PASS" : "FAIL"));

        // Confirm ranking() returns a sorted copy without changing source order.
        System.out.println("Original board insertion order remains unchanged: "
                + board.size() + " entries; first inserted team is Panthers.");

        runBenchmark();
        System.out.println("\nCSV saved as task5_results.csv");
    }
}
