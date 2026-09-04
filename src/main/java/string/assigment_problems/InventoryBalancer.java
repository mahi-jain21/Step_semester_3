package string.assigment_problems;

import java.util.Scanner;

// Custom Checked Exception
class InventoryMismatchException extends Exception {
    public InventoryMismatchException(String message) {
        super(message);
    }
}

public class InventoryBalancer {

    /**
     * Computes section totals, reports balance status, and finds highest item quantity.
     * 
     * @param sectionA Storage quantities for Section A
     * @param sectionB Storage quantities for Section B
     * @throws InventoryMismatchException (Checked) if sections are empty or size mismatch
     */
    public static void analyzeInventory(int[] sectionA, int[] sectionB) throws InventoryMismatchException {
        if (sectionA == null || sectionB == null) {
            throw new IllegalArgumentException("Section inventory arrays cannot be null.");
        }
        
        if (sectionA.length != sectionB.length) {
            throw new InventoryMismatchException("Inventory layout mismatch: Section A size (" + 
                sectionA.length + ") does not match Section B size (" + sectionB.length + ").");
        }

        if (sectionA.length == 0) {
            throw new InventoryMismatchException("Inventory check failed: No items exist in either section.");
        }

        int totalA = 0;
        int totalB = 0;

        int highestQty = Integer.MIN_VALUE;
        String highestSection = "";
        int highestItemNum = -1;

        for (int i = 0; i < sectionA.length; i++) {
            int qtyA = sectionA[i];
            int qtyB = sectionB[i];

            if (qtyA < 0 || qtyB < 0) {
                throw new IllegalArgumentException("Inventory quantities cannot be negative. Index: " + i);
            }

            totalA += qtyA;
            totalB += qtyB;

            // Check Section A item
            if (qtyA > highestQty) {
                highestQty = qtyA;
                highestSection = "Section A";
                highestItemNum = i + 1; // 1-indexed item number
            }

            // Check Section B item
            if (qtyB > highestQty) {
                highestQty = qtyB;
                highestSection = "Section B";
                highestItemNum = i + 1;
            }
        }

        String status = (totalA == totalB) ? "Balanced" : "Not Balanced";

        System.out.printf("Section A Total: %d | Section B Total: %d | Status: %s | Highest Quantity: %d (%s, Item %d)\n",
            totalA, totalB, status, highestQty, highestSection, highestItemNum);
    }

    public static void main(String[] args) {
        System.out.println("=== 4. Warehouse Inventory Balancer ===");

        // Test Case 1: Balanced inventories
        int[] secA = {20, 15, 30};
        int[] secB = {25, 10, 30};
        System.out.println("Testing Inventories A & B (should balance):");
        try {
            analyzeInventory(secA, secB);
        } catch (InventoryMismatchException e) {
            System.out.println("[Exception] " + e.getMessage());
        }

        System.out.println();

        // Test Case 2: Size Mismatch (Checked Exception)
        int[] badA = {10, 20};
        int[] badB = {10, 20, 30};
        System.out.println("Testing Layout Size Mismatch:");
        try {
            analyzeInventory(badA, badB);
        } catch (InventoryMismatchException e) {
            System.out.println("[Checked Exception Caught] " + e.getMessage());
        }
    }
}
