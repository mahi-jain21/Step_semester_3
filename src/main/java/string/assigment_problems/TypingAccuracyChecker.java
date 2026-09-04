package string.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class LengthMismatchException extends Exception {
    public LengthMismatchException(String message) {
        super(message);
    }
}

public class TypingAccuracyChecker {

    /**
     * Compares original text with typed text, prints matching ratio, accuracy %, and first mismatch.
     * 
     * @param original Original passage
     * @param typed User's typed passage
     * @throws LengthMismatchException (Checked) if strings are not equal in length
     */
    public static void checkTypingAccuracy(String original, String typed) throws LengthMismatchException {
        if (original == null || typed == null) {
            throw new IllegalArgumentException("Passage inputs cannot be null.");
        }
        
        if (original.length() != typed.length()) {
            throw new LengthMismatchException("Passages are of different lengths. " +
                "Original: " + original.length() + " chars, Typed: " + typed.length() + " chars.");
        }

        int totalChars = original.length();
        int matchedChars = 0;
        int firstMismatchPosition = -1;

        for (int i = 0; i < totalChars; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matchedChars++;
            } else {
                if (firstMismatchPosition == -1) {
                    firstMismatchPosition = i + 1; // 1-indexed position
                }
            }
        }

        double accuracy = ((double) matchedChars / totalChars) * 100.0;

        System.out.printf("Matched: %d/%d | Accuracy: %.2f%% | ", matchedChars, totalChars, accuracy);
        if (firstMismatchPosition == -1) {
            System.out.println("No Mismatches");
        } else {
            char originalChar = original.charAt(firstMismatchPosition - 1);
            char typedChar = typed.charAt(firstMismatchPosition - 1);
            System.out.printf("First Mismatch at position %d ('%c' vs '%c')\n", 
                firstMismatchPosition, originalChar, typedChar);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 2. Typing Speed Test Accuracy Checker ===");

        // Test Case 1: Partial match
        String orig1 = "hello world";
        String type1 = "hello worlt";
        System.out.println("Original: \"" + orig1 + "\", Typed: \"" + type1 + "\"");
        try {
            checkTypingAccuracy(orig1, type1);
        } catch (LengthMismatchException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 2: Perfect match
        String orig2 = "coding";
        String type2 = "coding";
        System.out.println("Original: \"" + orig2 + "\", Typed: \"" + type2 + "\"");
        try {
            checkTypingAccuracy(orig2, type2);
        } catch (LengthMismatchException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 3: Length Mismatch (Catches Checked Exception)
        String orig3 = "length check";
        String type3 = "length check mismatch";
        System.out.println("Original: \"" + orig3 + "\", Typed: \"" + type3 + "\"");
        try {
            checkTypingAccuracy(orig3, type3);
        } catch (LengthMismatchException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
