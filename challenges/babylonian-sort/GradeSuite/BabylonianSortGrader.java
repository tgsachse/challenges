// Grader written for BabylonianSort.
// Written by Tiger Sachse.

import java.io.*;
import java.util.*;
import java.util.Map.*;

// Provides grading criteria for BabylonianSort.
public class BabylonianSortGrader {

    private static int passed = 0;
    private static int failed = 0;
    private static final int VALID_INPUTS = 10;
    private static final int INVALID_INPUTS = 5;

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
   
    // Print final report.
    private static void endReport() {
        System.out.printf("\nTests passed: %d\n", passed);
        System.out.printf("Tests failed: %d\n", failed);

        double score = (100.0 * passed) / (passed + failed);
        System.out.printf("Final score: %.2f/100.0\n", score);        
    }

    // Get an array of numbers saved in a file.
    private static String[] getNumbers(String file) throws IOException {
        Scanner scanner = new Scanner(new File(file));

        // The first integer of each input file is the number of strings
        // in the file.
        String[] numbers = new String[scanner.nextInt()];

        scanner.nextLine();
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = scanner.nextLine();
        }

        scanner.close();
        
        return numbers;
    }

    // Test conversion of decimal longs to sexagesimal strings.
    private static void testDecimalToSexagesimal() {
        HashMap<Integer, String> tests = new HashMap<>();
        tests.put(0, "0");
        tests.put(9, "9");
        tests.put(40, "E");
        tests.put(2766, "K6");
        tests.put(213480, "Xi0");

        for (Entry<Integer, String> test : tests.entrySet()) {
            String message = String.format("decimalToSexagesimal(%d)", test.getKey());
            
            try {
                String result = BabylonianSort.decimalToSexagesimal(test.getKey());
                
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

    // Test conversion of sexagesimal strings to decimal longs.
    private static void testSexagesimalToDecimal() {
        HashMap<String, Integer> tests = new HashMap<>();
        tests.put("0", 0);
        tests.put("9", 9);
        tests.put("E", 40);
        tests.put("K6", 2766);
        tests.put("Xi0", 213480);

        for (Entry<String, Integer> test : tests.entrySet()) {
            String message = String.format("sexagesimalToDecimal(\"%s\")", test.getKey());

            try {
                long result = BabylonianSort.sexagesimalToDecimal(test.getKey());
                
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

    // Test that the proper error is thrown when given an invalid sexagesimal string.
    private static void testInvalidSexagesimalToDecimal() {
        String message = String.format("sexagesimalToDecimal(\"xyY\")");
        try {
            BabylonianSort.sexagesimalToDecimal("xyY");
            fail(message + " **exception expected**");
        }

        // This will catch the expected NumberFormatException and pass the case.
        catch (NumberFormatException e) {
            pass(message);
        }
        catch (Exception e) {
            fail(message + " **wrong exception**");
        }
        
        message = String.format("sexagesimalToDecimal(\"aaaaaaaaaaah\")");
        try {
            BabylonianSort.sexagesimalToDecimal("aaaaaaaaaaah");
            fail(message + " **exception expected**");
        }

        // This will catch the expected ArithmeticException and pass the case.
        catch (ArithmeticException e) {
            pass(message);
        }
        catch (Exception e) {
            fail(message + " **wrong exception**");
        }
    }

    // Test that the submission recognizes valid sexagesimal strings.
    private static void testValidSexagesimalNumbers() {
        String[] validNumbers = {
            "1",
            "68i",
            "00000",
            "att75",
            "yyghIIItjfk223kGGjk",
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

    // Test that the submission recognizes invalid sexagesimal strings.
    private static void testInvalidSexagesimalNumbers() {
        String[] invalidNumbers = {
            "-1000",
            "~~~88*",
            "AAaaY77",
            "abcdefghijklmnopZ"
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

    // Test BabylonianSort with several inputs.
    private static void testBabylonianSort() throws IOException {
        for (int input = 1; input <= VALID_INPUTS; input++) {
            String[] numbers = getNumbers(String.format("Inputs/Input%d.txt", input));
            String message = String.format("babylonianSort(Inputs/Input%d.txt)", input);

            // Attempt to sort the array of numbers.
            try {
                BabylonianSort.babylonianSort(numbers);
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
                continue;
            }

            boolean successful = true;
            Scanner scanner = new Scanner(new File(String.format("Outputs/Output%d.txt", input)));

            // Compare the numbers array to the expected output file. The output
            // file will list the numbers in order.
            for (int index = 0; index < numbers.length; index++) {
                if (!numbers[index].equals(scanner.nextLine())) {
                    fail(message + " **output mismatch**");
                    successful = false;
                    break;
                } 
            }

            // If all numbers matched, then pass the case.
            if (successful) {
                pass(message);
            }

            scanner.close();
        }

        // The inputs towards the end all have a problem that should result in
        // an exception being thrown.
        for (int input = VALID_INPUTS + 1; input <= INVALID_INPUTS + VALID_INPUTS; input++) {
            String[] numbers = getNumbers(String.format("Inputs/Input%d.txt", input));
            String message = String.format("babylonianSort(Inputs/Input%d.txt)", input);

            try {
                BabylonianSort.babylonianSort(numbers);
                fail(message + " **exception expected**");
            }
            catch (NumberFormatException e) {
                pass(message);
            }
            catch (Exception e) {
                fail(message + " **wrong exception**");
            }
        }
    }

    // Main entry point for the grader.
    public static void main(String[] args) throws IOException {
        testDecimalToSexagesimal();
        testSexagesimalToDecimal();
        testInvalidSexagesimalToDecimal();
        testValidSexagesimalNumbers();
        testInvalidSexagesimalNumbers();
        testBabylonianSort(); 

        endReport();
    }
}
