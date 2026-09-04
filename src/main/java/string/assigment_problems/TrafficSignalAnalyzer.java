package string.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class EmptyLogException extends Exception {
    public EmptyLogException(String message) {
        super(message);
    }
}

public class TrafficSignalAnalyzer {

    /**
     * Scans through the sequence and prints the color and length of the longest continuous streak.
     * 
     * @param signalLog String of signal readings
     * @throws EmptyLogException (Checked) if log contains no readings
     */
    public static void findLongestStreak(String signalLog) throws EmptyLogException {
        if (signalLog == null) {
            throw new IllegalArgumentException("Signal log cannot be null.");
        }
        if (signalLog.trim().isEmpty()) {
            throw new EmptyLogException("Signal log contains no signal readings.");
        }

        char longestColor = ' ';
        int longestCount = 0;

        char currentColor = signalLog.charAt(0);
        int currentCount = 1;

        for (int i = 1; i < signalLog.length(); i++) {
            char signal = signalLog.charAt(i);
            if (signal == currentColor) {
                currentCount++;
            } else {
                if (currentCount > longestCount) {
                    longestCount = currentCount;
                    longestColor = currentColor;
                }
                currentColor = signal;
                currentCount = 1;
            }
        }

        // Final check for the last running streak
        if (currentCount > longestCount) {
            longestCount = currentCount;
            longestColor = currentColor;
        }

        System.out.printf("Longest Streak: '%c' repeated %d times\n", longestColor, longestCount);
    }

    public static void main(String[] args) {
        System.out.println("=== 3. Traffic Signal Streak Analyzer ===");

        // Test Case 1
        String log1 = "RRGGGYRR";
        System.out.println("Testing Log: \"" + log1 + "\"");
        try {
            findLongestStreak(log1);
        } catch (EmptyLogException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 2
        String log2 = "RRRRYYGG";
        System.out.println("Testing Log: \"" + log2 + "\"");
        try {
            findLongestStreak(log2);
        } catch (EmptyLogException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 3: Empty log (Checked Exception catch)
        String log3 = "   ";
        System.out.println("Testing Log: \"" + log3 + "\"");
        try {
            findLongestStreak(log3);
        } catch (EmptyLogException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
