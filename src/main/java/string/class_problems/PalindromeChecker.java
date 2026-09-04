package string.class_problems;

class EmptyInputException extends Exception {
    public EmptyInputException(String message) {
        super(message);
    }
}

class NullInputException extends IllegalArgumentException {
    public NullInputException(String message) {
        super(message);
    }
}

public class PalindromeChecker {

    private static String normalize(String text) throws EmptyInputException {
        if (text == null) {
            throw new NullInputException("Cannot process a null reference as text.");
        }
        
        String cleaned = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        if (cleaned.isEmpty()) {
            throw new EmptyInputException("Text content \"" + text + "\" contains no alphanumeric characters.");
        }
        
        return cleaned;
    }

    public static boolean isPalindromeIterative(String text) throws EmptyInputException {
        String clean = normalize(text);
        int start = 0;
        int end = clean.length() - 1;
        
        while (start < end) {
            if (clean.charAt(start) != clean.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) throws EmptyInputException {
        String clean = normalize(text);
        return checkRecursive(clean);
    }

    private static boolean checkRecursive(String cleanText) {
        if (cleanText.length() <= 1) {
            return true;
        }
        if (cleanText.charAt(0) != cleanText.charAt(cleanText.length() - 1)) {
            return false;
        }
        return checkRecursive(cleanText.substring(1, cleanText.length() - 1));
    }

    public static boolean isPalindromeArrayReversal(String text) throws EmptyInputException {
        String clean = normalize(text);
        char[] original = clean.toCharArray();
        char[] reversed = new char[original.length];
        
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        
        String reversedStr = new String(reversed);
        return clean.equals(reversedStr);
    }

    public static void verifyPalindrome(String text) {
        System.out.println("Checking: \"" + text + "\"");
        try {
            boolean iter = isPalindromeIterative(text);
            boolean recur = isPalindromeRecursive(text);
            boolean revArr = isPalindromeArrayReversal(text);

            if (iter == recur && recur == revArr) {
                String resultStr = iter ? "Palindrome" : "Not Palindrome";
                System.out.printf("Iterative: %s | Recursive: %s | Array Reversal: %s\n\n", 
                                  resultStr, resultStr, resultStr);
            } else {
                System.out.println("[WARNING] Implementation Mismatch! Approaches did not agree.");
                System.out.printf("Iterative: %b, Recursive: %b, Array Reversal: %b\n\n", iter, recur, revArr);
            }
        } catch (EmptyInputException e) {
            System.out.println("[Checked Exception Caught] Error: " + e.getMessage() + "\n");
        } catch (NullInputException e) {
            System.out.println("[Unchecked Exception Caught] Error: " + e.getMessage() + "\n");
        }
    }
}
