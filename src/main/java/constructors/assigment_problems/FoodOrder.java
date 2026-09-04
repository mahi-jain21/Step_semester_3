package constructors.assigment_problems;

/**
 * Problem 1: Ghost Order Validator
 * Campus food delivery order validator with parameterized-only constructor
 * and duplicate delivery detection.
 */
public class FoodOrder {
    private final String studentName;
    private final String dishName;
    private boolean delivered;
    private int deliveryCallCount;

    /**
     * Parameterized constructor validating studentName and dishName.
     * No no-argument constructor is provided.
     */
    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name: name cannot be null, blank, or whitespace-only.");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name: dish cannot be null, blank, or whitespace-only.");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
        this.deliveryCallCount = 0;
    }

    /**
     * Marks order as delivered. Warns if called more than once on the same order.
     */
    public void markDelivered() {
        deliveryCallCount++;
        if (deliveryCallCount == 1) {
            delivered = true;
            System.out.println("Order for " + studentName + " (" + dishName + ") marked as delivered.");
        } else {
            System.out.println("ALERT: Order for " + studentName + " (" + dishName + ") was already delivered! Possible duplicate delivery detected (Call #" + deliveryCallCount + ").");
        }
    }

    public boolean isDelivered() {
        return delivered;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDishName() {
        return dishName;
    }

    /**
     * Batch processor that reads raw {studentName, dishName} attempts
     * and reports accepted vs rejected counts.
     */
    public static void processBatch(String[][] rawOrders) {
        if (rawOrders == null) {
            System.out.println("Valid: 0 | Rejected: 0");
            return;
        }

        int validCount = 0;
        int rejectedCount = 0;

        for (String[] entry : rawOrders) {
            if (entry == null || entry.length < 2) {
                rejectedCount++;
                continue;
            }
            try {
                new FoodOrder(entry[0], entry[1]);
                validCount++;
            } catch (IllegalArgumentException e) {
                rejectedCount++;
            }
        }

        System.out.println("Valid: " + validCount + " | Rejected: " + rejectedCount);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1: Ghost Order Validator Demo ===");
        String[][] rawOrders = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };

        processBatch(rawOrders);

        // Demonstrate duplicate delivery alert
        System.out.println("\n--- Testing markDelivered() on single order ---");
        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered();
        order.markDelivered();
    }
}
