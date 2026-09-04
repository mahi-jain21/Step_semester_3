package arrays_strings.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class InvalidCSVFormatException extends Exception {
    public InvalidCSVFormatException(String message) {
        super(message);
    }
}

public class ProductInventoryParser {

    /**
     * Parses a CSV record and prints it formatted, or flags it as invalid.
     * 
     * @param csvLine Single CSV entry line
     * @throws InvalidCSVFormatException (Checked) if layout size is not exactly 3 fields
     */
    public static void parseInventoryRecord(String csvLine) throws InvalidCSVFormatException {
        if (csvLine == null) {
            throw new IllegalArgumentException("CSV input record cannot be null.");
        }

        String[] fields = csvLine.split(",");

        // Validate exactly 3 fields are present
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            throw new InvalidCSVFormatException("Record contains " + fields.length + 
                " fields (expected exactly 3: ProductName, SKU, Quantity).");
        }

        String productName = fields[0].trim();
        String sku = fields[1].trim();
        String qty = fields[2].trim();

        System.out.println("Product: " + productName + " | SKU: " + sku + " | Qty: " + qty);
    }

    public static void main(String[] args) {
        System.out.println("=== 3. Product Inventory CSV Parser ===");

        // Test Case 1: Valid format
        String record1 = "Wireless Mouse,WM-2201,150";
        System.out.println("Parsing: \"" + record1 + "\"");
        try {
            parseInventoryRecord(record1);
        } catch (InvalidCSVFormatException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 2: Invalid format (Catches Checked Exception)
        String record2 = "Wireless Mouse,150";
        System.out.println("Parsing: \"" + record2 + "\"");
        try {
            parseInventoryRecord(record2);
        } catch (InvalidCSVFormatException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
