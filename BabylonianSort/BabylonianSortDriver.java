// Driver to run BabylonianSort on given input file.
// Written by Tiger Sachse.

import java.io.*;
import java.util.*;

// Class that provides interface to use BabylonianSort.
class BabylonianSortDriver {
    
    private static final String OUTPUT = "output.txt";

    // Main entry point of the driver.
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File(args[0]));
        String[] numbers = new String[scanner.nextInt()];

        // Read the file into an array of strings.
        scanner.nextLine();
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = scanner.nextLine();
        }

        // Run BabylonianSort in place on the array.
        BabylonianSort.babylonianSort(numbers);

        // Write the array to the output file.
        BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT));
        for (int index = 0; index < numbers.length; index++) {
            writer.write(numbers[index]); 
            writer.newLine();
        }

        writer.close();
    }
}
