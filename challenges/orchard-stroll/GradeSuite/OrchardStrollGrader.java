// Grader for the OrchardStroll assignment.
// Written by Tiger Sachse.

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Main class of the grading program.
public class OrchardStrollGrader {
    private static GradeRecorder recorder;

    private static final int TIMEOUT = 10;

    // Run all tests for a given method.
    private static void runTests(HashMap<Tree[], Integer> tests) {
        String message = "determineLargestHaul() %s %d%s";

        // For each test, create a separate thread executor to
        // allow for method timeouts.
        for (Map.Entry<Tree[], Integer> test : tests.entrySet()) { 
            ExecutorService executor = Executors.newSingleThreadExecutor();
            TimeableCall timeable = new TimeableCall(message, recorder, test);
            Future<Void> future = executor.submit(timeable);

            // Attempt to execute the method. If the method times out, fail.
            try {
                future.get(TIMEOUT, TimeUnit.SECONDS);
            }
            catch (TimeoutException exception) {
                recorder.fail(
                    String.format(
                        message,
                        "?=",
                        test.getValue(),
                        " **program timeout**"
                    )
                );
            }
            catch (Exception exception) {
                recorder.fail(
                    String.format(
                        message,
                        "!=",
                        test.getValue(),
                        " **program crashed**"
                    )
                );
            }
            executor.shutdownNow();
        }
    }

    // Entry point of the grading program.
    public static void main(String[] args) {
     
        // Set up the grade recorder using the first argument to the program.
        try {
            recorder = new GradeRecorder(args[0]);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        // Add some tests to the test map.
        HashMap<Tree[], Integer> tests = new HashMap<>();
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.APPLE, 5),
                new Tree(Tree.Fruit.APPLE, 5),
                new Tree(Tree.Fruit.APPLE, 5),
                new Tree(Tree.Fruit.APPLE, 5),
                new Tree(Tree.Fruit.APPLE, 5)
            },
            25
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.MANGO, 10),
                new Tree(Tree.Fruit.MANGO, 22),
                new Tree(Tree.Fruit.LEMON, 19),
                new Tree(Tree.Fruit.LEMON, 15),
                new Tree(Tree.Fruit.LEMON, 6)
            },
            72
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.LIME, 1),
                new Tree(Tree.Fruit.PEAR, 2),
                new Tree(Tree.Fruit.MANGO, 3),
                new Tree(Tree.Fruit.LEMON, 4),
                new Tree(Tree.Fruit.APPLE, 5),
                new Tree(Tree.Fruit.BANANA, 6),
                new Tree(Tree.Fruit.ORANGE, 7),
                new Tree(Tree.Fruit.TOMATO, 8),
                new Tree(Tree.Fruit.CHERRY, 9),
                new Tree(Tree.Fruit.STRAWBERRY, 10),
                new Tree(Tree.Fruit.WATERMELON, 11),
            },
            21
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.LIME, 11),
                new Tree(Tree.Fruit.PEAR, 10),
                new Tree(Tree.Fruit.MANGO, 9),
                new Tree(Tree.Fruit.LEMON, 8),
                new Tree(Tree.Fruit.APPLE, 7),
                new Tree(Tree.Fruit.BANANA, 6),
                new Tree(Tree.Fruit.ORANGE, 5),
                new Tree(Tree.Fruit.TOMATO, 4),
                new Tree(Tree.Fruit.CHERRY, 3),
                new Tree(Tree.Fruit.STRAWBERRY, 2),
                new Tree(Tree.Fruit.WATERMELON, 1),
            },
            21
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.TOMATO, 12),
                new Tree(Tree.Fruit.TOMATO, 14),
                new Tree(Tree.Fruit.TOMATO, 16),
                new Tree(Tree.Fruit.TOMATO, 18),
                new Tree(Tree.Fruit.TOMATO, 17),
                new Tree(Tree.Fruit.WATERMELON, 400),
                new Tree(Tree.Fruit.CHERRY, 500)
            },
            900
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.MANGO, 9001)
            },
            9001
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.BANANA, 3),
                new Tree(Tree.Fruit.WATERMELON, 1),
                new Tree(Tree.Fruit.TOMATO, 9),
                new Tree(Tree.Fruit.WATERMELON, 4),
                new Tree(Tree.Fruit.WATERMELON, 4),
                new Tree(Tree.Fruit.WATERMELON, 4),
                new Tree(Tree.Fruit.WATERMELON, 4),
                new Tree(Tree.Fruit.BANANA, 15)
            },
            35
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.CHERRY, 1000),
                new Tree(Tree.Fruit.PEAR, 2000),
                new Tree(Tree.Fruit.LIME, 30)
            },
            3000
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.WATERMELON, 10),
                new Tree(Tree.Fruit.TOMATO, 12),
                new Tree(Tree.Fruit.MANGO, 4),
                new Tree(Tree.Fruit.CHERRY, 16),
                new Tree(Tree.Fruit.APPLE, 19),
                new Tree(Tree.Fruit.ORANGE, 21),
                new Tree(Tree.Fruit.STRAWBERRY, 1)
            },
            40
        );
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.LIME, 6),
                new Tree(Tree.Fruit.LIME, 6),
                new Tree(Tree.Fruit.APPLE, 15),
                new Tree(Tree.Fruit.CHERRY, 2),
                new Tree(Tree.Fruit.CHERRY, 20),
                new Tree(Tree.Fruit.CHERRY, 20),
                new Tree(Tree.Fruit.CHERRY, 20),
                new Tree(Tree.Fruit.LIME, 10)
            },
            84
        );

        // Run all the tests.
        runTests(tests);

        recorder.printFinalReport();

        // A system exit is needed here to kill any lingering threads. I'm not good enough
        // at Java multithreading to do this the right way. All I wanted was to be able
        // to time out some methods... -_-
        System.exit(0);
    }
}

// A timeable method call.
class TimeableCall implements Callable<Void> {
    private String message;
    private GradeRecorder recorder;
    private Map.Entry<Tree[], Integer> test;

    // Constructor that collects the test entry and function message.
    public TimeableCall(
        String message,
        GradeRecorder recorder,
        Map.Entry<Tree[], Integer> test) {

        this.test = test;
        this.message = message;
        this.recorder = recorder;
    }

    // Executable method that contains the main testing check.
    @Override
    public Void call() throws Exception {
        int result = OrchardStroll.determineLargestHaul(test.getKey());

        if (!Thread.currentThread().isInterrupted()) {
            if (result == test.getValue()) {
                recorder.pass(String.format(message, "=", test.getValue(), ""));
            }
            else {
                recorder.fail(
                    String.format(
                        message,
                        "!=",
                        test.getValue(),
                        " **output mismatch**"
                    )
                );
            }
        }

        return null;
    }
}

// Record grades into a provided report file.
class GradeRecorder extends BufferedWriter {
    private int passed;
    private int failed;

    // Constructor that initializes test counts and opens the file.
    public GradeRecorder(String filename) throws IOException {
        super(new FileWriter(filename, true));
        passed = 0;
        failed = 0;
    }

    // Print a failure message and increment the failed counter.
    public void fail(String message) {
        try {
            write("FAILED: ");
            write(String.format("%s\n", message));
            flush();
            failed++;
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Print a success message and increment the passed counter.
    public void pass(String message) {
        try {
            write("PASSED: ");
            write(String.format("%s\n", message));
            flush();
            passed++;
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Print the final report footer.
    public void printFinalReport() {
        try {
            long score = Math.round((100.0 * passed) / (passed + failed));

            write(String.format("\nTests passed: %d\n", passed));
            write(String.format("Tests failed: %d\n", failed));
            write(String.format("Final score: %d/100\n", score));
            flush();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
