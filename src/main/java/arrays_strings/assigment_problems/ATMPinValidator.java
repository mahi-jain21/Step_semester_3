package arrays_strings.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class PinEmptyException extends Exception {
    public PinEmptyException(String message) {
        super(message);
    }
}

public class ATMPinValidator {

    /**
     * Checks if the entered PIN is exactly 4 digits long.
     * 
     * @param pin The PIN string
     * @throws PinEmptyException (Checked) if PIN is empty or blank
     */
    public static void checkPinLength(String pin) throws PinEmptyException {
        if (pin == null) {
            throw new IllegalArgumentException("PIN input cannot be null.");
        }
        if (pin.trim().isEmpty()) {
            throw new PinEmptyException("PIN cannot be empty or consist of only spaces.");
        }

        // Get length and check if exactly 4 digits
        if (pin.length() != 4) {
            System.out.println("Invalid PIN \u2014 must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. ATM PIN Length Validator ===");

        // Test case 1: Invalid PIN length
        String pin1 = "482";
        System.out.println("Testing PIN: \"" + pin1 + "\"");
        try {
            checkPinLength(pin1);
        } catch (PinEmptyException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test case 2: Valid PIN length
        String pin2 = "4820";
        System.out.println("Testing PIN: \"" + pin2 + "\"");
        try {
            checkPinLength(pin2);
        } catch (PinEmptyException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test case 3: Empty PIN (Catches Checked Exception)
        String pin3 = "   ";
        System.out.println("Testing PIN: \"" + pin3 + "\"");
        try {
            checkPinLength(pin3);
        } catch (PinEmptyException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
