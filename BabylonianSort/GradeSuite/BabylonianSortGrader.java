// Grader written for BabylonianSort.
// Written by Tiger Sachse.

import java.io.*;
import java.util.*;

public class BabylonianSortGrader {

    private static int passed = 0;
    private static int failed = 0;
    private static final int INPUTS = 1;

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

    private static void testBabylonianSort() throws IOException {
        for (int input = 1; input <= INPUTS; input++) {
            Scanner scanner = new Scanner(new File(String.format("Inputs/Input%d.txt", input)));

            // The first integer of each input file is the number of strings
            // in the file.
            String[] numbers = new String[scanner.nextInt()];

            scanner.nextLine();
            for (int index = 0; index < numbers.length; index++) {
                numbers[index] = scanner.nextLine();
            }

            scanner.close();

            String message = String.format("babylonianSort(Inputs/Input%d.txt)", input);

            // Attempt to sort the array of numbers.
            try {
                BabylonianSort.babylonianSort(numbers);
            }
            catch (Exception e) {
                fail(message + " **program crashed**");
            }

            boolean successful = true;
            scanner = new Scanner(new File(String.format("Outputs/Output%d.txt", input)));

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
    }

    public static void main(String[] args) throws IOException {
        testBabylonianSort(); 
        endReport();
    }
}
