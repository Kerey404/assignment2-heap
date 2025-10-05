package cli;

import algorithms.MaxHeap;
import algorithms.MinHeap;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class BenchmarkRunner {

    private static final Random R = new Random(42);

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Heap Benchmark Runner ===");
        System.out.println("1. Run MaxHeap benchmark");
        System.out.println("2. Run MinHeap benchmark");
        System.out.print("Choose (1 or 2): ");
        int choice = sc.nextInt();

        if (choice == 1) runHeapBenchmark("max");
        else if (choice == 2) runHeapBenchmark("min");
        else System.out.println("Invalid choice. Exiting.");
    }

    private static void runHeapBenchmark(String type) throws IOException {
        int[] ns = {100, 1000, 10000};
        int runs = 3;

        String fileName = "docs/benchmarks/" + type + "heap_results.csv";
        Files.createDirectories(Paths.get("docs/benchmarks"));

        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write("n,run,inputType," + PerformanceTracker.csvHeader() + "\n");

            for (int n : ns) {
                for (int run = 1; run <= runs; run++) {
                    int[] data = randomArray(n);

                    // --- Warmup phase ---
                    for (int w = 0; w < 2; w++) {
                        if (type.equals("max")) {
                            MaxHeap h = new MaxHeap();
                            for (int v : data) h.insert(v);
                            while (!h.isEmpty()) h.extractMax();
                        } else {
                            MinHeap h = new MinHeap();
                            for (int v : data) h.insert(v);
                            while (!h.isEmpty()) h.extractMin();
                        }
                    }

                    // --- Benchmark phase ---
                    PerformanceTracker m;
                    long start, end;
                    if (type.equals("max")) {
                        MaxHeap heap = new MaxHeap();
                        m = heap.getMetrics();
                        m.reset();
                        start = System.nanoTime();
                        for (int v : data) heap.insert(v);
                        while (!heap.isEmpty()) heap.extractMax();
                        end = System.nanoTime();
                    } else {
                        MinHeap heap = new MinHeap();
                        m = heap.getMetrics();
                        m.reset();
                        start = System.nanoTime();
                        for (int v : data) heap.insert(v);
                        while (!heap.isEmpty()) heap.extractMin();
                        end = System.nanoTime();
                    }

                    m.timeNs = end - start;
                    fw.write(n + "," + run + ",random," + m.toCsv() + "\n");
                    fw.flush();

                    System.out.printf("✅ %sHeap | n=%d | run=%d | time=%.2f ms%n",
                            type.equals("max") ? "Max" : "Min",
                            n, run, m.timeNs / 1_000_000.0);
                }
            }
        }
        System.out.printf("📊 Results saved to %s%n", "docs/benchmarks/" + type + "heap_results.csv");
    }

    private static int[] randomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = R.nextInt(Integer.MAX_VALUE);
        return arr;
    }
}
