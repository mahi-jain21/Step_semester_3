package arrays_strings.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class EmptySentenceException extends Exception {
    public EmptySentenceException(String message) {
        super(message);
    }
}

public class WordReversalEncoder {

    /**
     * Splits a sentence, reverses each word, and joins them back together.
     * 
     * @param sentence Sentence string (words separated by spaces)
     * @return Transformed string with each word reversed
     * @throws EmptySentenceException (Checked) if input is empty or blank
     */
    public static String reverseEachWord(String sentence) throws EmptySentenceException {
        if (sentence == null) {
            throw new IllegalArgumentException("Sentence input cannot be null.");
        }
        if (sentence.trim().isEmpty()) {
            throw new EmptySentenceException("Sentence cannot be empty or only spaces.");
        }

        // Split by single spaces
        String[] words = sentence.split(" ");
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            
            // Reverse the current word using a loop
            StringBuilder reversedWord = new StringBuilder();
            for (int j = word.length() - 1; j >= 0; j--) {
                reversedWord.append(word.charAt(j));
            }
            
            resultBuilder.append(reversedWord.toString());
            
            // Add a space between words
            if (i < words.length - 1) {
                resultBuilder.append(" ");
            }
        }

        return resultBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== 2. Word Reversal Encoder ===");

        // Test Case 1
        String text1 = "hello club";
        System.out.println("Original: \"" + text1 + "\"");
        try {
            String output1 = reverseEachWord(text1);
            System.out.println("Encoded:  \"" + output1 + "\"");
        } catch (EmptySentenceException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 2: Empty input (Checked Exception catch)
        String text2 = "     ";
        System.out.println("Original: \"" + text2 + "\"");
        try {
            reverseEachWord(text2);
        } catch (EmptySentenceException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
