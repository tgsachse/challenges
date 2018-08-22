import java.io.*;
import java.util.*;

class BabylonianSortDriver {
    
    private static final String OUTPUT = "output.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File(args[0]));
        String[] numbers = new String[scanner.nextInt()];

        scanner.nextLine();
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = scanner.nextLine();
        }

        BabylonianSort.babylonianSort(numbers);

        BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT));
        for (int index = 0; index < numbers.length; index++) {
            writer.write(numbers[index]); 
            writer.newLine();
        }

        writer.close();
    }
}
