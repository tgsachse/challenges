// Grader for the PathOfDestruction assignment.
// Written by Tiger Sachse.

import java.util.*;

// Main class of the grading program.
public class PathOfDestructionGrader {
    
    private static int passed = 0;
    private static int failed = 0;
    private static final int TESTS = 11;

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
    
    // Entry point of the grading program.
    public static void main(String[] args) {
        
        // Create a set of valid board input file numbers.
        HashSet<Integer> validBoards = new HashSet<>();
        Collections.addAll(validBoards, 1, 2, 3, 4, 5, 6, 8, 9, 11);

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
        Collections.addAll(validPaths, 1, 2, 3, 4, 5);
        
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

        double score = (100.0 * passed) / (passed + failed);
        System.out.printf("Final score: %.2f/100.0\n", score);        
    }
}
