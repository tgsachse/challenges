// Tester for BabylonianSort, written by Tiger Sachse.
// To use, compile and run your program with these commands:
//      javac BabylonianSort.java BabylonianSortTester.java
//      java BabylonianSortTester

import java.util.*;
import java.util.Map.*;

public class BabylonianSortTester {

    private static int passed = 0;
    private static int failed = 0;

    // Print a failure message and increment the failed counter.
    private static void fail(String message) {
        System.out.print("FAILED: ");
        System.out.println(message);
        failed++;
    }

    // Print a success message and increment the passed counter.
    private static void pass(String message) {
        System.out.print("PASSED: ");
        System.out.println(message);
        passed++;
    }

    private static void printFinalReport() {
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

    private static void testDecimalToSexagesimal() {
        HashMap<Integer, String> tests = new HashMap<>();
        tests.put(0, "0");
        tests.put(7, "7");
        tests.put(59, "X");
        tests.put(1337, "mh");//confirm
        tests.put(100000, "rKE");//confirm

        for (Entry<Integer, String> test : tests.entrySet()) {
            String result = BabylonianSort.decimalToSexagesimal(test.getKey());
            String message = String.format("decimalToSexagesimal(%d)", test.getKey());
            
            try {
                if (result.equals(test.getValue())) {
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
    }

    private static void testSexagesimalToDecimal() {
        HashMap<String, Integer> tests = new HashMap<>();
        tests.put("0", 0);
        tests.put("7", 7);
        tests.put("X", 59);
        tests.put("mh", 1337);
        tests.put("rKE", 100000);

        for (Entry<String, Integer> test : tests.entrySet()) {
            long result = BabylonianSort.sexagesimalToDecimal(test.getKey());
            String message = String.format("sexagesimalToDecimal(\"%s\")", test.getKey());

            try {
                if (result == test.getValue()) {
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
    }

    private static void testInvalidSexagesimalToDecimal() {
        String message = String.format("sexagesimalToDecimal(\"12aZ\")");

        try {
            BabylonianSort.sexagesimalToDecimal("12aZ");
            fail(message + " **error expected**");
        }
        catch (NumberFormatException e) {
            pass(message);
        }
        catch (Exception e) {
            fail(message + " **error expected**");
        }
    }

    private static void testValidSexagesimalNumbers() {
        String[] validNumbers = {
            "17bbxX",
            "0000",
            "8ayT",
            "2",
        };

        for (String number : validNumbers) {
            String message = String.format("isValidSexagesimalNumber(\"%s\")", number);
            try {
                if (BabylonianSort.isValidSexagesimalNumber(number)) {
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
    }

    private static void testInvalidSexagesimalNumbers() {
        String[] invalidNumbers = {
            "-1",
            "AAZ",
            "**&%@#???",
        };
   
        for (String number : invalidNumbers) {
            String message = String.format("isValidSexagesimalNumber(\"%s\")", number);
            try {
                if (!BabylonianSort.isValidSexagesimalNumber(number)) {
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
    }

    public static void main(String[] args) {
        System.out.println("Results:");
        System.out.println("=========================================================");

        testDecimalToSexagesimal();
        testSexagesimalToDecimal();
        testInvalidSexagesimalToDecimal();
        testValidSexagesimalNumbers();
        testInvalidSexagesimalNumbers();

        printFinalReport();
    }
}
