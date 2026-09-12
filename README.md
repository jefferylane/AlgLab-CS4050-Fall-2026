# Algorithm Performance Laboratory

CS4050-003 · Fall 2026 · Jeffery Lane

A framework for experimentally measuring and analyzing the runtime growth
of four sorting algorithms (Insertion Sort, Selection Sort, Merge Sort, and
`java.util.Arrays.sort`) and comparing the results against their theoretical
growth models.

## Requirements

- A working JDK (`javac` and `java` on your PATH). No Maven, Gradle, or Ant
  required — everything builds and runs with plain `javac`/`java`.

## Building and Running

All source files live in `src/` in the unnamed (default) package. Build and
run from inside that directory:

```bash
cd src
javac *.java
java Main
```

**Important:** Run `java Main` from inside the `src/` directory, not from
the project root. Output paths (see below) are resolved relative to
whatever directory you launch `java` from, so running from the wrong place
will still work, but will scatter your output files somewhere other than
where you expect them.

If you're running from an IDE instead of a terminal, check your run
configuration's "working directory" setting — some IDEs default this to
the project root rather than `src/`. Set it to `src/` to match the
terminal instructions above.

## What Running It Does

`Main` runs all four algorithms across input sizes 1,000 / 2,000 / 4,000 /
8,000 / 16,000, with 1,000 warm-up trials and 100 timed trials per size.
For each algorithm it:

1. Times execution across all input sizes (with fresh random input
   generated for every trial).
2. Computes mean, median, and standard deviation of the timings.
3. Compares the timing data against three theoretical growth models
   (linear, linearithmic, quadratic) and reports which one fits best.
4. Prints a summary of all of the above to the terminal.

Progress and per-algorithm/per-model results print to the terminal as the
program runs — this will take a few minutes given the trial and warm-up
counts, so don't worry if it's not instant.

## Output Files

After running, CSV output is written to a `reports/` folder created
alongside the source files (i.e. `src/reports/`, if you ran it as
instructed above):

- `reports/<AlgorithmName>_report.csv` — one file per algorithm, with
  columns `size, mean_ns, median_ns, stdev_ns`.
- `reports/combined_results.csv` — all four algorithms' data combined into
  one file, with an added `algorithm` column.

These CSVs are what you'd feed into a spreadsheet or plotting tool to
produce the performance-vs-size plots referenced in the written report.

## Project Structure

```
src/
  Algorithm.java              -- interface: an algorithm under study
  InputGenerator.java         -- interface: produces inputs of a given size
  InsertionSort.java          -- O(n^2)
  SelectionSort.java          -- O(n^2)
  MergeSort.java               -- O(n log n)
  ArraysSortWrapper.java      -- wraps java.util.Arrays.sort
  RandomIntArrayGenerator.java
  Measurement.java            -- warm-up, timed trials, basic stats
  Experiment.java             -- runs one algorithm across a range of sizes
  PerformanceData.java        -- size -> timing statistics table
  Analysis.java                -- fits empirical data against growth models
  AnalysisResult.java          -- holds one model's fit result
  Report.java                  -- CSV export
  Main.java                    -- wires everything together and runs it
  NaiveTimingDemo.java         -- (starter code) example of bad timing practice
```
