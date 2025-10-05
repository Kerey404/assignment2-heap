# Assignment 2 — Heap Data Structures

## 👤 Authors
- **Bakytzhan Kassymgali (Kerey404)** — *Max-Heap Implementation (increase-key, extract-max)*
- **[Alikhan Serik]** — *Min-Heap Implementation (decrease-key, merge)*

---

## 📘 Overview
This project implements and analyzes **Heap Data Structures** as part of the *Design and Analysis of Algorithms* (DAA) course.

- **Max-Heap** — supports `insert`, `extractMax`, `increaseKey`, and `merge`.
- **Min-Heap** — supports `insert`, `extractMin`, `decreaseKey`, and `merge`.
- Each implementation includes **performance tracking** (comparisons, swaps, array accesses, allocations, and time).

---

## 📁 Project Structure

```assignment2-heap/
│

├── docs/ 

│ ├── benchmarks/

│ │ └── maxheap_results.csv

│ ├── performance-plots/

│ └── analysis-report.pdf
│

├── src/

│ ├── main/java/

│ │ ├── algorithms/MaxHeap.java

│ │ ├── metrics/PerformanceTracker.java

│ │ └── cli/BenchmarkRunner.java

│ └── test/java/
│ └── algorithms/MaxHeapTest.java
│

├── pom.xml
└── README.md
```
**Analysis**

| n     | Comparisons | Swaps  | Array Accesses | Allocations | Time (ns) |
| ----- | ----------- | ------ | -------------- | ----------- | --------- |
| 100   | 1057        | 523    | 4506           | 3           | 111000    |
| 1000  | 17206       | 8574   | 71708          | 6           | 3106000   |
| 10000 | 239610      | 119737 | 988168         | 10          | 13800000  |


**Time Complexities**

| Operation             | Max-Heap | Min-Heap | Complexity |
| --------------------- | -------- | -------- | ---------- |
| Insert                | ✔️       | ✔️       | O(log n)   |
| Extract (max/min)     | ✔️       | ✔️       | O(log n)   |
| Increase/Decrease Key | ✔️       | ✔️       | O(log n)   |
| Merge                 | ✔️       | ✔️       | O(n)       |
