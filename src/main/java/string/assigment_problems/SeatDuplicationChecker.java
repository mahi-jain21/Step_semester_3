package string.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class EmptyHallException extends Exception {
    public EmptyHallException(String message) {
        super(message);
    }
}

public class SeatDuplicationChecker {

    /**
     * Scans seat numbers and flags any duplicates.
     * Arrays and loops only (no Collections class).
     * 
     * @param seatNumbers Array of assigned seat numbers
     * @throws EmptyHallException (Checked) if array size is 0
     */
    public static void checkDuplicateSeats(int[] seatNumbers) throws EmptyHallException {
        if (seatNumbers == null) {
            throw new IllegalArgumentException("Seat numbers array cannot be null.");
        }
        if (seatNumbers.length == 0) {
            throw new EmptyHallException("No seats assigned. The exam hall is empty.");
        }

        boolean foundDuplicate = false;
        boolean[] visited = new boolean[seatNumbers.length];

        for (int i = 0; i < seatNumbers.length; i++) {
            if (visited[i]) continue;
            boolean isDuplicate = false;
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    isDuplicate = true;
                    visited[j] = true; // Mark as visited to avoid double printing
                }
            }
            if (isDuplicate) {
                System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                foundDuplicate = true;
            }
        }

        if (!foundDuplicate) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Exam Hall Seat Duplication Checker ===");

        // Test case 1: Duplicates present
        int[] hallA = {101, 102, 103, 102, 105};
        System.out.println("Testing Seat List 1 (with duplicates):");
        try {
            checkDuplicateSeats(hallA);
        } catch (EmptyHallException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test case 2: No duplicates
        int[] hallB = {101, 102, 103, 104, 105};
        System.out.println("Testing Seat List 2 (clean seating):");
        try {
            checkDuplicateSeats(hallB);
        } catch (EmptyHallException e) {
            System.out.println("[Exception] " + e.getMessage());
        }
    }
}
