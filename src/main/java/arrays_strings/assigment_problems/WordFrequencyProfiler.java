package arrays_strings.assigment_problems;

import java.util.*;

// Custom Checked Exception
class NoMeaningfulWordsException extends Exception {
    public NoMeaningfulWordsException(String message) {
        super(message);
    }
}

public class WordFrequencyProfiler {

    private static final String[] STOP_WORDS = {"the", "was", "and", "a", "is", "of", "in"};

    /**
     * Filters stop words, counts word frequencies, and prints reports sorted by count descending.
     * 
     * @param feedback The feedback review paragraph
     * @throws NoMeaningfulWordsException (Checked) if no valid words remain after filtering
     */
    public static void printFilteredWordFrequency(String feedback) throws NoMeaningfulWordsException {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback input cannot be null.");
        }

        // Normalize: convert to lowercase
        String normalized = feedback.toLowerCase();

        // Strip punctuation (periods and commas) using replace()
        normalized = normalized.replace(".", "");
        normalized = normalized.replace(",", "");
        normalized = normalized.replace("!", "");
        normalized = normalized.replace("?", "");

        // Split by whitespace
        String[] words = normalized.split("\\s+");

        // Prepare stop-word look-up list
        List<String> stopWordList = Arrays.asList(STOP_WORDS);

        // Count frequency of unique words (HashMap)
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            String trimmedWord = word.trim();
            if (trimmedWord.isEmpty()) {
                continue;
            }

            // Exclude stop words
            if (stopWordList.contains(trimmedWord)) {
                continue;
            }

            frequencyMap.put(trimmedWord, frequencyMap.getOrDefault(trimmedWord, 0) + 1);
        }

        if (frequencyMap.isEmpty()) {
            throw new NoMeaningfulWordsException("No meaningful words remained after stop-word filtering.");
        }

        // Sort unique words by frequency count in descending order
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(frequencyMap.entrySet());
        entryList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // Print each word and its frequency
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 5. Stop-Word-Filtered Word Frequency Report ===");

        // Test Case 1
        String review1 = "The mentor was great, the session was great and clear.";
        System.out.println("Analyzing paragraph:\n\"" + review1 + "\"");
        try {
            printFilteredWordFrequency(review1);
        } catch (NoMeaningfulWordsException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 2: Only stop words (Catches Checked Exception)
        String review2 = "The was a and of in.";
        System.out.println("Analyzing paragraph:\n\"" + review2 + "\"");
        try {
            printFilteredWordFrequency(review2);
        } catch (NoMeaningfulWordsException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
