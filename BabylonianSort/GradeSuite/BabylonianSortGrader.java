// Grader written for BabylonianSort.
// Written by Tiger Sachse.

public class BabylonianSortGrader {

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
   
    // Print final report.
    private static void endReport() {
        System.out.printf("\nTests passed: %d\n", passed);
        System.out.printf("Tests failed: %d\n", failed);

        double score = (100.0 * passed) / (passed + failed);
        System.out.printf("Final score: %.2f/100.0\n", score);        
    }

    public static void main(String[] args) {
        testBabylonianSort(); 
        endReport();
    }
}
