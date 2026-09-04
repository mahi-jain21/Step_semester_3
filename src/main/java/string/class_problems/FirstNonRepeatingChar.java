package string.class_problems;

class NoUniqueCharacterException extends Exception {
    public NoUniqueCharacterException(String message) {
        super(message);
    }
}

public class FirstNonRepeatingChar {

    public static char findFirstNonRepeatingChar(String text) throws NoUniqueCharacterException {
        if (text == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        if (text.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be empty or consist only of whitespace.");
        }

        int[] frequencyMap = new int[256];

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            
            if (ch > 255) {
                throw new IllegalArgumentException("The character '" + ch + "' (Unicode: " + (int) ch + 
                    ") is outside the standard 8-bit ASCII range.");
            }
            
            frequencyMap[ch]++;
        }

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (frequencyMap[ch] == 1) {
                return ch;
            }
        }

        throw new NoUniqueCharacterException("No non-repeating (unique) character found in the text \"" + text + "\".");
    }

    public static void huntUniqueLetter(String input) {
        System.out.println("Searching in string: \"" + input + "\"");
        try {
            char uniqueChar = findFirstNonRepeatingChar(input);
            System.out.printf("First Non-Repeating Character: '%c' (ASCII value: %d)\n\n", uniqueChar, (int) uniqueChar);
        } catch (NoUniqueCharacterException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage() + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("[Unchecked Exception Caught] " + e.getMessage() + "\n");
        }
    }
}
