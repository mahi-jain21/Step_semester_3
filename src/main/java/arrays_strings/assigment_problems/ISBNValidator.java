package arrays_strings.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class InvalidISBNException extends Exception {
    public InvalidISBNException(String message) {
        super(message);
    }
}

public class ISBNValidator {

    /**
     * Trims spaces and uppercases only the first 3 characters.
     */
    public static String normalizeCode(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("ISBN string cannot be null.");
        }
        
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        
        // Uppercase first 3 characters and join the rest untouched
        String prefix = trimmed.substring(0, 3).toUpperCase();
        String suffix = trimmed.substring(3);
        return prefix + suffix;
    }

    /**
     * Validates and formats the normalized ISBN code.
     * 
     * @param code Normalized code string
     * @return Formatted layout representation
     * @throws InvalidISBNException (Checked) detailing specific validation failure reason
     */
    public static String validateAndFormat(String code) throws InvalidISBNException {
        if (code == null) {
            throw new IllegalArgumentException("Normalized ISBN cannot be null.");
        }

        // Rule 1: Must be exactly 13 characters
        if (code.length() != 13) {
            throw new InvalidISBNException("Invalid: wrong length (must be exactly 13 characters, found " + code.length() + ")");
        }

        // Rule 2: First 3 characters are letters
        for (int i = 0; i < 3; i++) {
            char ch = code.charAt(i);
            if (!Character.isLetter(ch)) {
                throw new InvalidISBNException("Invalid: publisher code must be 3 letters (found '" + ch + "' at index " + i + ")");
            }
        }

        // Rule 3: Remaining 10 characters are digits
        for (int i = 3; i < 13; i++) {
            char ch = code.charAt(i);
            if (!Character.isDigit(ch)) {
                throw new InvalidISBNException("Invalid: body must be 10 digits (found non-digit '" + ch + "' at index " + i + ")");
            }
        }

        // Formatted display: [PUBCODE] YEAR: 20XX | CATALOG: 123456
        String pubCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalog = code.substring(7);

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(pubCode).append("] ");
        sb.append("YEAR: ").append(year).append(" | ");
        sb.append("CATALOG: ").append(catalog);

        return sb.toString();
    }

    public static void runTest(String rawISBN) {
        System.out.println("Raw Input: \"" + rawISBN + "\"");
        try {
            String normalized = normalizeCode(rawISBN);
            System.out.println("Normalized: \"" + normalized + "\"");
            String result = validateAndFormat(normalized);
            System.out.println("Output: " + result + "\n");
        } catch (InvalidISBNException e) {
            System.out.println("Output: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 4. Library ISBN Normalizer & Validator ===");

        // Test Case 1: Valid input
        runTest(" pen2026004251 ");

        // Test Case 2: Invalid Publisher code
        runTest("12N2026004251");

        // Test Case 3: Invalid length
        runTest("ABC12345");
        
        // Test Case 4: Non-digit body
        runTest("ABC2026XYZ456");
    }
}
