class BabylonianSortDriver {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File(args[0]));
        String[] numbers = new String[scanner.nextInt()];

        scanner.nextLine();
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = scanner.nextLine();
        }

        BabylonianSort.babylonianSort(numbers);

            for (int index = 0; index < numbers.length; index++) {
                System.out.println(numbers[index]);
            }

            boolean successful = true;
            Scanner outScanner = new Scanner(new File(String.format("Outputs/Output%d.txt", input)));
            // Compare the numbers array to the expected output file. The output
            // file will list the numbers in order.
            for (int index = 0; index < numbers.length; index++) {
                if (!numbers[index].equals(outScanner.nextLine())) {
                    fail(message + " **output mismatch**");
                    successful = false;
                    break;
                } 
            }

            // If all numbers matched, then pass the case.
            if (successful) {
                pass(message);
            }
        }
    }
    }
}
