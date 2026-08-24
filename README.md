# Algorithm Performance Laboratory — Starter Code

This is a starting point, not a solution. It gives you two things:

1. **The two interfaces the rest of your framework should build on**
   (`Algorithm<T>` and `InputGenerator<T>`), plus some ready-to-use
   implementations of each so you aren't spending your time re-deriving
   basic sorting algorithms.
2. **`NaiveTimingDemo`**, a deliberately bad timing example. Run it a few
   times (the flaws are easiest to see across repeated runs) before you
   start designing your own framework. It exists to make two problems
   concrete instead of abstract:
   - measuring the *same trial* more than once without regenerating input
     that the algorithm mutates, and
   - drawing conclusions from a single, un-warmed-up measurement.

   Your framework's job is to not have these problems.

Everything lives in the unnamed (default) package.
That's deliberate: this is exactly the kind of small, self-contained,
experimental codebase the unnamed package exists for, and it keeps the
file layout flat and simple.

## What's provided

```
src/
  Algorithm.java              -- interface: an algorithm under study
  InputGenerator.java         -- interface: produces inputs of a given size
  SelectionSort.java          -- O(n^2)
  InsertionSort.java          -- O(n^2)
  MergeSort.java               -- O(n log n)
  ArraysSortWrapper.java      -- wraps java.util.Arrays.sort (JDK dual-pivot quicksort)
  RandomIntArrayGenerator.java
  NaiveTimingDemo.java        -- run this first; see above
```

## What you need to design and build

Everything downstream of "I have an `Algorithm` and an `InputGenerator`":

- **Experiment** — orchestrates running one `Algorithm` against inputs of
  increasing size produced by an `InputGenerator`.
- **Measurement** — actually times a run. This is where warm-up, repeated
  trials, and basic statistics (mean, median, standard deviation — your
  choice, but justify it) belong.
- **PerformanceData** — the resulting table of (input size → timing
  statistics) for one algorithm.
- **Analysis** — compares the empirical data against a theoretical
  growth-rate model and reports how well they match.
- **Report** — produces output a human (or a plotting tool) can use.
  At minimum, produce a CSV export of size vs. timing statistics.

See the assignment handout for the full requirements and rubric.

## Building and running

Your submission must build and run with nothing but plain `javac`/`java`.
Grading does not assume Maven, Gradle, Ant, or any particular IDE, so don't
structure your code in a way that depends on one of them being present.
The baseline that must always work:

```bash
cd src
javac *.java
java NaiveTimingDemo
```

If you personally prefer working inside Maven, Gradle, or Ant, you're
welcome to set one up for your own convenience, but it has to be in
addition to the plain `javac`/`java` path working, never in place of it.
(Note that adopting one of those tools typically means moving these files
into a package and a `src/main/java/...` layout; that's fine for your own
setup, but the version you submit still needs the plain-`javac` path to
work unmodified.)
