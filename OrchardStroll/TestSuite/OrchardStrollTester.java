// Written by Tiger Sachse.
// Part of OrchardStroll.

// To use, compile and run your program like this:
//     javac OrchardStroll.java OrchardStrollTester.java Tree.java
//     java OrchardStrollTester

import java.util.*;

// Test class for OrchardStroll.
public class OrchardStrollTester {
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
      
        String messageFormat = "determineLargestHaul() %s %d%s";
        HashMap<Tree[], Integer> tests = new HashMap<>();

        // Insert some tests into the tests hashmap.
        tests.put(
            new Tree[] {
                new Tree(Tree.Fruit.APPLE, 2),
                new Tree(Tree.Fruit.BANANA, 4),
                new Tree(Tree.Fruit.CHERRY, 6),
                new Tree(Tree.Fruit.BANANA, 3),
                new Tree(Tree.Fruit.BANANA, 3),
                new Tree(Tree.Fruit.BANANA, 3),
                new Tree(Tree.Fruit.BANANA, 3),
                new Tree(Tree.Fruit.APPLE, 10)
            },
            28
        );

        // For each test in the hashmap, run the test.
        for (Map.Entry<Tree[], Integer> test : tests.entrySet()) { 
            try {
                if (OrchardStroll.determineLargestHaul(test.getKey()) == test.getValue()) {
                    pass(String.format(messageFormat, "=", test.getValue(), ""));
                }
                else {
                    fail(
                        String.format(
                            messageFormat,
                            "!=",
                            test.getValue(),
                            " **output mismatch**"
                        )
                    );
                }
            }
            catch (Exception exception) {
                fail(
                    String.format(
                        messageFormat,
                        "!=",
                        test.getValue(),
                        " **program crashed**"
                    )
                );
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
