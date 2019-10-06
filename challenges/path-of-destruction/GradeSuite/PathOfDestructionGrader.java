// Grader for the PathOfDestruction assignment.
// Written by Tiger Sachse.

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Main class of the grading program.
public class PathOfDestructionGrader {
    private static final int TESTS = 11;
    private static final int TIMEOUT = 30;

    private static GradeRecorder recorder;

    // Run all tests for a given method.
    private static void runTests(String method,
                                 String inputFormat,
                                 String messageFormat,
                                 HashSet<Integer> validItems) {
   
        for (int test = 1; test <= TESTS; test++) {
            String input = String.format(inputFormat, test);
            String message = String.format(messageFormat, test);
        
            // Create a separate thread executor to allow for method timeouts.
            ExecutorService executor = Executors.newSingleThreadExecutor();
            TimeableCall timeable = new TimeableCall(method,
                                                     input,
                                                     message,
                                                     test,
                                                     recorder,
                                                     validItems);
            Future<Void> future = executor.submit(timeable);

            // Attempt to execute the method. If the method times out, fail.
            try {
                future.get(TIMEOUT, TimeUnit.SECONDS);
            }
            catch (TimeoutException exception) {
                recorder.fail(message + " **program timeout**");
            }
            catch (Exception exception) {
                recorder.fail(message + " **program crashed**");
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

        // Create a set of valid board input file numbers.
        HashSet<Integer> validBoards = new HashSet<>();
        Collections.addAll(validBoards, 1, 2, 3, 4, 5, 6, 8, 9, 11);

        // Create a set of board input file numbers containing valid paths of destruction.
        HashSet<Integer> validPaths = new HashSet<>();
        Collections.addAll(validPaths, 1, 2, 3, 4, 5);

        // Run all the tests.
        runTests("isValidBoard", "Inputs/Input%d.txt", "isValidBoard(Input%d)", validBoards);
        runTests("hasPathOfDestruction",
                 "Inputs/Input%d.txt",
                 "hasPathOfDestruction(Input%d)",
                 validPaths);

        recorder.printFinalReport();

        // A system exit is needed here to kill any lingering threads. I'm not good enough
        // at Java multithreading to do this the right way. All I wanted was to be able
        // to time out some methods... -_-
        System.exit(0);
    }
}

// A timeable method call.
class TimeableCall implements Callable<Void> {
    private String input;
    private String method;
    private String message;
    private int testNumber;
    private GradeRecorder recorder;
    private HashSet<Integer> validItems;

    // Constructor for some necessary parameters.
    public TimeableCall(String method,
                        String input,
                        String message,
                        int testNumber,
                        GradeRecorder recorder,
                        HashSet<Integer> validItems) {

        this.input = input;
        this.method = method;
        this.message = message;
        this.recorder = recorder;
        this.testNumber = testNumber;
        this.validItems = validItems;
    }

    // Run the appropriate method. I wanted to pass the functions as arguments instead
    // of string names but Java makes it hard as usual.
    @Override
    public Void call() throws Exception {
        if (method.equals("isValidBoard")) {
            boolean result = PathOfDestruction.isValidBoard(input);
            if (Thread.currentThread().isInterrupted()) {
                return null;
            }

            if (result == validItems.contains(testNumber)) {
                recorder.pass(message);
            }
            else {
                recorder.fail(message + " **output mismatch**");
            }
        }
        else if (method.equals("hasPathOfDestruction")) {
            boolean result = PathOfDestruction.hasPathOfDestruction(input);
            if (Thread.currentThread().isInterrupted()) {
                return null;
            }

            if (result == validItems.contains(testNumber)) {
                recorder.pass(message);
            }
            else {
                recorder.fail(message + " **output mismatch**");
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
