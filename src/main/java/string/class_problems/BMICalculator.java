package string.class_problems;

class EmptyTeamException extends Exception {
    public EmptyTeamException(String message) {
        super(message);
    }
}

public class BMICalculator {

    public static String getBmiStatus(double bmi) {
        if (bmi <= 0) {
            throw new IllegalArgumentException("BMI value must be positive.");
        }
        
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25.0) {
            return "Normal";
        } else if (bmi < 30.0) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    public static void printWellnessReport(double[] heights, double[] weights) throws EmptyTeamException {
        if (heights == null || weights == null) {
            throw new IllegalArgumentException("Heights and weights arrays cannot be null.");
        }
        
        if (heights.length != weights.length) {
            throw new IllegalArgumentException("Heights and weights arrays must be of equal size (parallel arrays).");
        }

        if (heights.length == 0) {
            throw new EmptyTeamException("Cannot generate wellness report for an empty team (size = 0).");
        }

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-10s | %-12s | %-12s | %-8s | %-15s\n", "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < heights.length; i++) {
            double height = heights[i];
            double weight = weights[i];

            if (height <= 0.0 || height > 3.0 || weight <= 0.0 || weight > 500.0) {
                throw new IllegalArgumentException("Physically unreasonable measurements for person " + (i + 1) + 
                    ": Height = " + height + " m, Weight = " + weight + " kg.");
            }

            double bmi = weight / (height * height);
            String status = getBmiStatus(bmi);

            System.out.printf("Person %-3d | %-12.2f | %-12.1f | %-8.2f | %-15s\n", 
                              (i + 1), height, weight, bmi, status);
        }
        System.out.println("-------------------------------------------------------------");
    }

    public static void runDemoSession(int teamSize) {
        System.out.println("\n=== Wellness Camp Health Check-up Program ===");
        System.out.println("Simulating physical measurements for " + teamSize + " team members...");

        java.util.Random random = new java.util.Random();
        double[] heights = new double[teamSize];
        double[] weights = new double[teamSize];

        for (int i = 0; i < teamSize; i++) {
            heights[i] = 1.50 + (random.nextDouble() * 0.50);
            weights[i] = 45.0 + (random.nextDouble() * 75.0);
        }

        try {
            printWellnessReport(heights, weights);
        } catch (EmptyTeamException e) {
            System.out.println("[Checked Exception Caught] Failed to print report: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("[Unchecked Exception Caught] Invalid data: " + e.getMessage());
        }
    }
}
