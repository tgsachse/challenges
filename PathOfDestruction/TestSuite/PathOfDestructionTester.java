// Tester class for PathOfDestruction.
// Written by Tiger Sachse.

// To use, compile and run your program like this:
//     javac PathOfDestruction.java PathOfDestructionTester.java
//     java PathOfDestructionTester

import java.util.*;

// Test class for PathOfDestruction.
public class PathOfDestructionTester {
 
    private static final int TESTS = 8;
    private static int passed = 0;
    private static int failed = 0;

    // Print a failure message and increment the failed counter.
    private static void fail(String message) {
        System.out.printf("FAILED: ");
        System.out.println(message);
        failed++;
    }

    // Print a success message and increment the passed counter.
    private static void pass(String message) {
        System.out.printf("PASSED: ");
        System.out.println(message);
        passed++;
    }

    // Main entry point of this tester.
    public static void main(String[] args) {
        System.out.println("Results:");
        System.out.println("=========================================================");
       
        // Create a set of valid board input file numbers.
        HashSet<Integer> validBoards = new HashSet<>();
        Collections.addAll(validBoards, 1, 2, 3, 4, 7, 8);

        // Test for valid boards across all input files.
        for (int test = 1; test <= TESTS; test++) {
            String input = String.format("Inputs/Input%d.txt", test);
            String message = String.format("isValidBoard(Input%d)", test);

            try {
                if (PathOfDestruction.isValidBoard(input) == validBoards.contains(test)) {
                    pass(message);
                }
                else {
                    fail(message + " **output mismatch**");
                }
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }
        }
       
        // Create a set of board input file numbers containing valid paths of destruction.
        HashSet<Integer> validPaths = new HashSet<>();
        Collections.addAll(validPaths, 1, 7, 8);
        
        // Test for valid paths across all input files.
        for (int test = 1; test <= TESTS; test++) {
            String input = String.format("Inputs/Input%d.txt", test);
            String message = String.format("hasPathOfDestruction(Input%d)", test);
          
            try {
                if (PathOfDestruction.hasPathOfDestruction(input) == validPaths.contains(test)) {
                    pass(message);
                }
                else {
                    fail(message + " **output mismatch**");
                }
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }
        }

        // Print final report.
        System.out.printf("\nTests passed: %d\n", passed);
        System.out.printf("Tests failed: %d\n", failed);
        System.out.println("=========================================================\n");

        if (failed == 0) {
            System.out.println("Congratulations! You are passing all test cases.");
            System.out.println("It is highly recommended that you write some tests");
            System.out.println("for yourself, as these tests are not comprehensive.");
        }
        else {
            System.out.println("You appear to be failing some tests.");
            System.out.println("Keep working hard and don't give up!");
        }
    }
}
