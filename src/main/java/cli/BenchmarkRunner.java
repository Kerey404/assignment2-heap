package cli;

import algorithms.MaxHeap;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class BenchmarkRunner {
    private static final Random R = new Random(42);

    public static void main(String[] args) throws IOException {
        int[] ns = {100, 1000, 10000};
        int runs = 3;

        java.nio.file.Files.createDirectories(java.nio.file.Paths.get("docs/benchmarks"));
        try (FileWriter fw = new FileWriter("docs/benchmarks/maxheap_results.csv")) {
            fw.write("n,run,inputType," + PerformanceTracker.csvHeader() + "\n");

            for (int n : ns) {
                for (int run = 1; run <= runs; run++) {
                    int[] data = randomArray(n);

                    // Warmup
                    for (int w = 0; w < 2; w++) {
                        MaxHeap h = new MaxHeap();
                        for (int v : data) h.insert(v);
                        while (!h.isEmpty()) h.extractMax();
                    }

                    // Benchmark
                    MaxHeap heap = new MaxHeap();
                    PerformanceTracker m = heap.getMetrics();
                    m.reset();
                    long start = System.nanoTime();
                    for (int v : data) heap.insert(v);
                    while (!heap.isEmpty()) heap.extractMax();
                    long end = System.nanoTime();
                    m.timeNs = end - start;

                    fw.write(n + "," + run + ",random," + m.toCsv() + "\n");
                    fw.flush();
                    System.out.println("n=" + n + " run=" + run + " time=" + m.timeNs + " ns");
                }
            }
        }
        System.out.println("✅ Results saved to docs/benchmarks/maxheap_results.csv");
    }

    private static int[] randomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = R.nextInt(Integer.MAX_VALUE);
        return arr;
    }
}
