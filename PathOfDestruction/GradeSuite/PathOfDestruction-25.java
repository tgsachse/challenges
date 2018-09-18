// Solution for PathOfDestruction.
// Written by Tiger Sachse.

import java.io.*;
import java.awt.*;
import java.util.*;

// Main class that detects paths of destruction on a checkerboard.
public class PathOfDestruction {

    // Check if a given x and y coordinate pair are out of bounds.
    private static boolean isOutOfBounds(int x, int y, int maxX, int maxY) {
        return (x < 1 || x > maxX || y < 1 || y > maxY);
    }
    
    // Check if a provided checkerboard is valid.
    public static boolean isValidBoard(String filename) throws IOException {
        Scanner scanner = new Scanner(new File(filename));

        // The first line is of the format: maxX, maxY, checkerCount.
        int maxX = scanner.nextInt();
        int maxY = scanner.nextInt();
        int checkerCount = scanner.nextInt();

        // if there aren't any checkers, it is a valid board.
        if (checkerCount == 0) {
            scanner.close();

            return true;
        }

        // The first checker coordinates determine the parity requirement of
        // the board. If both the x and y coordinate of the first checker have
        // a matching parity (e.g. if both are odd or both are even), then all
        // other checkers must also have coordinates with matching parity. If 
        // the parity of the coordinates is different (i.e. one is even, one
        // is odd), then all other checkers must also have mismatched
        // coordinate parity.
        int firstX = scanner.nextInt();
        int firstY = scanner.nextInt();
        boolean matchingParity = ((firstX & 1) == (firstY & 1));

        // If the first checker is out of bounds then the board is invalid.
        if (isOutOfBounds(firstX, firstY, maxX, maxY)) {
            scanner.close();

            return false;
        } 

        // Check the rest of the checkers.
        while (scanner.hasNext()) {
            int xCoord = scanner.nextInt();
            int yCoord = scanner.nextInt();
            
            // If the current checker is out of bounds then the board is invalid.
            if (isOutOfBounds(xCoord, yCoord, maxX, maxY)) {
                scanner.close();
                
                return false;
            }

            // Compare the parity of the x and y coordinates. If the result
            // of this comparison is not equal to the result of the first checker's
            // comparison, then the board is invalid.
            if (((xCoord & 1) == (yCoord & 1)) != matchingParity) {
                scanner.close();

                return false;
            }
        }

        scanner.close();
        
        // All checkers have been checked. The board is valid.
        return true;
    }

    // Check if a path of destruction exists on the checkerboard.
    public static boolean hasPathOfDestruction(String filename) throws IOException {
        
        // Board must be valid for a path of destruction.
        if (!isValidBoard(filename)) {
            return false;
        }

        Scanner scanner = new Scanner(new File(filename));
        int maxX = scanner.nextInt();
        int maxY = scanner.nextInt();
        int checkerCount = scanner.nextInt();

        // If no checkers exist on the board, then it is vacuously true
        // that there exists a path of destruction.
        if (checkerCount == 0) {
            scanner.close();
            
            return true;
        }

        // Get the parity of the first checker.
        int firstX = scanner.nextInt();
        int firstY = scanner.nextInt();
        boolean matchingParity = ((firstX & 1) == (firstY & 1));

        // Create a set for all checkers and add the first one.
        HashSet<Point> checkers = new HashSet<>();
        checkers.add(new Point(firstX, firstY));

        // Add all remaining checkers to the set.
        while (scanner.hasNext()) {
            checkers.add(new Point(scanner.nextInt(), scanner.nextInt()));
        }

        // Attempt to start a path of destruction at each position on the board.
        for (int yCoord = 1; yCoord <= maxY; yCoord += 1) {
            for (int xCoord = 1; xCoord <= maxX; xCoord += 1) {
                if (hasPathOfDestruction(xCoord, yCoord, maxX, maxY, checkers)) {
                    scanner.close();
                    
                    return true;
                } 
            }
        }

        scanner.close();
        
        return false;
    }

    // Private backtracking recursive solution to find a path of destruction.
    private static boolean hasPathOfDestruction(int xCoord,
                                                int yCoord,
                                                int maxX,
                                                int maxY,
                                                HashSet<Point> checkers) {
        int[][] moves = {
            {1, 1},
            {1, -1},
            {-1, 1},
            {-1, -1},
        };

        // Base case: king has landed on a checker.
        if (checkers.contains(new Point(xCoord, yCoord))) {
            return false;
        }

        // Base case: king is out of bounds.
        if (isOutOfBounds(xCoord, yCoord, maxX, maxY)) {
            return false;
        }

        // Base case: all the checkers have been destroyed.
        if (checkers.size() == 0) {
            return true;
        }

        // Try to jump over checkers for each move, then recursively call
        // this function from the new position.
        for (int[] move : moves) {
            Point adjacentSpace = new Point(xCoord + move[0], yCoord + move[1]);
            
            // Check if a checker is in the adjacent space that the king could
            // leap over.
            if (checkers.contains(adjacentSpace)) {

                // Capture the checker.
                checkers.remove(adjacentSpace);

                // If a new recursive call returns true, then return true.
                if (hasPathOfDestruction(xCoord + move[0] * 2,
                                         yCoord + move[1] * 2,
                                         maxX, maxY,
                                         checkers)) {
                    return true;
                }

                // Replace the checker for failed jumps.
                checkers.add(adjacentSpace);
            }
        }
        
        return false;
    }
}
