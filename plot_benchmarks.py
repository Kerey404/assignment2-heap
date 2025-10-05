import pandas as pd
import matplotlib.pyplot as plt
import os

# === Path settings ===
os.makedirs("docs/performance-plots", exist_ok=True)
data_dir = "docs/benchmarks"

# === Secure CSV upload ===
def load_csv_safe(path):
    if os.path.exists(path):
        print(f"Loading {path}")
        return pd.read_csv(path)
    else:
        print(f"File not found: {path}")
        return None

min_df = load_csv_safe(os.path.join(data_dir, "minheap_results.csv"))
max_df = load_csv_safe(os.path.join(data_dir, "maxheap_results.csv"))

# === Check if there is any data ===
if min_df is None and max_df is None:
    print("There are no files for building graphs.")
    exit(0)

# === Averaging function only for numeric fields ===
def average_numeric(df):
    numeric_cols = df.select_dtypes(include=["number"]).columns.tolist()
    if "n" in numeric_cols:
        numeric_cols.remove("n")
    grouped = df.groupby("n")[numeric_cols].mean().reset_index()
    return grouped


# === Data averaging ===
min_avg = average_numeric(min_df) if min_df is not None else None
max_avg = average_numeric(max_df) if max_df is not None else None

# === Converting time to milliseconds ===
if min_avg is not None:
    min_avg["time_ms"] = min_avg["timeNs"] / 1_000_000
if max_avg is not None:
    max_avg["time_ms"] = max_avg["timeNs"] / 1_000_000

# === Plotting graphs ===
def plot_graph(x, y_min, y_max, title, ylabel, filename):
    plt.figure(figsize=(8, 5))
    if y_min is not None:
        plt.plot(min_avg["n"], y_min, marker="o", label="MinHeap")
    if y_max is not None:
        plt.plot(max_avg["n"], y_max, marker="s", label="MaxHeap")
    plt.xscale("log")
    plt.xlabel("Input size (n)")
    plt.ylabel(ylabel)
    plt.title(title)
    plt.grid(True, which="both", linestyle="--", alpha=0.6)
    plt.legend()
    plt.tight_layout()
    plt.savefig(f"docs/performance-plots/{filename}", dpi=300)
    plt.close()
    print(f"Schedule saved: docs/performance-plots/{filename}")

# === Building ===
plot_graph("n",
           min_avg["time_ms"] if min_avg is not None else None,
           max_avg["time_ms"] if max_avg is not None else None,
           "Heap Build + Extract Time vs Input Size",
           "Average time (ms)",
           "time_vs_n.png")

plot_graph("n",
           min_avg["comparisons"] if min_avg is not None else None,
           max_avg["comparisons"] if max_avg is not None else None,
           "Comparisons vs Input Size",
           "Average comparisons",
           "comparisons_vs_n.png")

plot_graph("n",
           min_avg["arrayAccesses"] if min_avg is not None else None,
           max_avg["arrayAccesses"] if max_avg is not None else None,
           "Array Accesses vs Input Size",
           "Average array accesses",
           "accesses_vs_n.png")

print("All graphs are created and saved in docs/performance-plots/")
