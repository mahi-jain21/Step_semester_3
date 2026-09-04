package string.class_problems;

class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}

public class ReverseCustomerName {

    public static String reverseCustomerName(String customerName) throws InvalidNameException {
        if (customerName == null) {
            throw new IllegalArgumentException("Customer name reference cannot be null.");
        }
        if (customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty or only spaces.");
        }

        String trimmedName = customerName.trim();
        for (int i = 0; i < trimmedName.length(); i++) {
            char ch = trimmedName.charAt(i);
            if (!Character.isLetter(ch) && ch != ' ' && ch != '-' && ch != '\'') {
                throw new InvalidNameException("Name compliance check failed: Name \"" + customerName + 
                    "\" contains illegal character '" + ch + "'. Names in banking profiles can only contain alphabetic letters, spaces, hyphens, and apostrophes.");
            }
        }

        char[] originalChars = trimmedName.toCharArray();
        int length = originalChars.length;
        char[] reversedChars = new char[length];

        for (int i = 0; i < length; i++) {
            reversedChars[i] = originalChars[length - 1 - i];
        }

        return new String(reversedChars);
    }

    public static void verifyReversal(String name) {
        System.out.println("Processing Name Verification: \"" + name + "\"");
        try {
            String reversed = reverseCustomerName(name);
            System.out.println("Original Name: " + name);
            System.out.println("Reversed Name: " + reversed + "\n");
        } catch (InvalidNameException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("[Unchecked Exception Caught] " + e.getMessage() + "\n");
        }
    }
}
