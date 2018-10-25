// Solution to BabylonianSort. This solution is quick and dirty.
// Written by Tiger Sachse.

import java.util.*;

// Main class to sort sexagesimal numbers.
public class BabylonianSort {
   
    private static final String NUMERALS = "0123456789" +
                                           "abcdefghijklmnopqrstuvwxyz" +
                                           "ABCDEFGHIJKLMNOPQRSTUVWX";

    // Sort an array of sexagesimal strings.
    public static void babylonianSort(String[] numbers) throws NumberFormatException {

        // Check that all the numbers are valid and get the longest length.
        int places = 0;
        for (String number : numbers) {
            if (!isValidSexagesimalNumber(number)) {
                String error = String.format("Invalid sexagesimal number '%s'.", number);

                throw new NumberFormatException(error);
            }

            if (number.length() > places) {
                places = number.length();
            }
        }

        // Run the sorting algorithm once for each place in the longest number.
        for (int place = 0; place < places; place++) {

            // This hashmap holds the numbers as they are being sorted by place.
            HashMap<Character, Queue<String>> map = new HashMap<>();

            for (String number : numbers) {
                Character numeral = '0';
               
                // If the number has a valid numeral at the current position,
                // save that numeral, else default to '0'.
                if (number.length() > place) {
                    numeral = number.charAt(number.length() - 1 - place);
                }

                // Insert the number into the map at the correct position,
                // based on its numeral at the current place.
                if (map.containsKey(numeral)) {
                    Queue<String> queue = map.get(numeral);
                    queue.add(number);
                }
                else {
                    Queue<String> queue = new LinkedList<String>();
                    queue.add(number);
                    map.put(numeral, queue);
                }
            }

            // Pop every number from the map in order, based on NUMERALS.
            int arrayIndex = 0;
            for (int index = 0; index < NUMERALS.length(); index++) {
                Character numeral = NUMERALS.charAt(index);
                if (map.containsKey(numeral)) {
                    Queue<String> queue = map.get(numeral);

                    // Pop each number in the queue in order.
                    String number = queue.poll();
                    while (number != null) {
                        numbers[arrayIndex] = number;
                        arrayIndex++;
                        number = queue.poll();
                    }
                }
            }
        }
    }

    // Return if a given string is a sexagesimal number.
    public static boolean isValidSexagesimalNumber(String number) {
        for (int index = 0; index < number.length(); index++) {
            char numeral = number.charAt(index);

            // All characters in a sexagesimal number fall within one of
            // these ranges.
            if ((numeral >= '0' && numeral <= '9') ||
                (numeral >= 'a' && numeral <= 'z') ||
                (numeral >= 'A' && numeral <= 'X')) {

                continue; 
            }
            else {
                return false;
            }
        }

        return true;
    }

    // Convert a sexagesimal number to a long decimal.
    public static long sexagesimalToDecimal(String number) throws NumberFormatException,
                                                                   ArithmeticException {   
        long decimal = 0;
        
        for (int index = 0; index < number.length(); index++) {
            char numeral = number.charAt(index);
           
            // Determine the decimal value of the current numeral in the number.
            long numeralValue;
            if (numeral >= '0' && numeral <= '9') {
                numeralValue = numeral - '0';
            }
            else if (numeral >= 'a' && numeral <= 'z') {
                numeralValue = numeral - 'a' + 10;
            }
            else if (numeral >= 'A' && numeral <= 'X') {
                numeralValue = numeral - 'A' + 36;
            }
            else {
                String error = String.format("Character '%c' is not a valid numeral.", numeral);
                
                throw new NumberFormatException(error);
            }
   
            long previousDecimal = decimal;

            // Use Horner's Rule to calculate the base-10 value of the number.
            decimal *= 60;
            decimal += numeralValue;

            // If the decimal overflows, throw an error.
            if (previousDecimal > decimal) {
                String error = String.format("Sexagesimal number '%s' has overflowed.", number);

                throw new ArithmeticException(error);
            }
        }
        
        return decimal;
    }

    // Convert a decimal long to a sexagesimal string.
    public static String decimalToSexagesimal(long number) {

        // Handle 0 here so the rest of the function can always assume positive numbers.
        if (number == 0) {
            return "0";
        }

        // Assemble the sexagesimal string in reverse order using
        // the division method for base conversion back to base-10.
        // Assembling in reverse is easier than inserting at the beginning
        // of the StringBuilder's array.
        StringBuilder sexagesimalBuilder = new StringBuilder();
        for ( ; number > 0; number /= 60) {
            long numeralValue = number % 60;

            char numeral = 0;
            if (numeralValue >= 0 && numeralValue <= 9) {
                numeral = (char) (numeralValue + '0');
            }
            else if (numeralValue >= 10 && numeralValue <= 35) {
                numeral = (char) (numeralValue + 'a' - 10);
            }
            else if (numeralValue >= 36 && numeralValue <= 59) {
                numeral = (char) (numeralValue + 'A' - 36);
            }

            sexagesimalBuilder.append(numeral);
        }

        // Reverse the reversed string.
        sexagesimalBuilder.reverse();

        return sexagesimalBuilder.toString();
    }
}
