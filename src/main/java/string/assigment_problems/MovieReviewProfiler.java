package string.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class EmptyReviewException extends Exception {
    public EmptyReviewException(String message) {
        super(message);
    }
}

public class MovieReviewProfiler {

    /**
     * Splits the review into individual words and classifies them by length.
     * Short (1-4), Medium (5-8), Long (9+).
     * 
     * @param review Movie review string
     * @throws EmptyReviewException (Checked) if review contains no valid words
     */
    public static void classifyWordLengths(String review) throws EmptyReviewException {
        if (review == null) {
            throw new IllegalArgumentException("Review input cannot be null.");
        }
        
        // Strip punctuation and split by whitespace
        String cleaned = review.replaceAll("[^a-zA-Z ]", " ").trim();
        if (cleaned.isEmpty()) {
            throw new EmptyReviewException("Review profile check failed: No alphabetic words found in review.");
        }

        String[] words = cleaned.split("\\s+");

        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;

        for (String word : words) {
            int length = word.length();
            if (length >= 1 && length <= 4) {
                shortCount++;
            } else if (length >= 5 && length <= 8) {
                mediumCount++;
            } else if (length >= 9) {
                longCount++;
            }
        }

        System.out.printf("Short: %d | Medium: %d | Long: %d\n", shortCount, mediumCount, longCount);
    }

    public static void main(String[] args) {
        System.out.println("=== 5. Movie Review Word Length Profiler ===");

        // Test Case 1
        String review1 = "This movie was absolutely fantastic and thrilling";
        System.out.println("Analyzing Review:\n\"" + review1 + "\"");
        try {
            classifyWordLengths(review1);
        } catch (EmptyReviewException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 2: Empty Review (Checked Exception catch)
        String review2 = "   12345!!! ???  ";
        System.out.println("Analyzing Review:\n\"" + review2 + "\"");
        try {
            classifyWordLengths(review2);
        } catch (EmptyReviewException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
