package metrics;


public class PerformanceTracker {
    public long comparisons = 0;
    public long swaps = 0;
    public long arrayAccesses = 0;
    public long allocations = 0;
    public long timeNs = 0;

    public void reset() {
        comparisons = swaps = arrayAccesses = allocations = timeNs = 0;
    }

    public void add(PerformanceTracker other) {
        if (other == null) return;
        comparisons += other.comparisons;
        swaps += other.swaps;
        arrayAccesses += other.arrayAccesses;
        allocations += other.allocations;
        timeNs += other.timeNs;
    }

    public String toCsv() {
        return comparisons + "," + swaps + "," + arrayAccesses + "," + allocations + "," + timeNs;
    }

    public static String csvHeader() {
        return "comparisons,swaps,arrayAccesses,allocations,timeNs";
    }
}
