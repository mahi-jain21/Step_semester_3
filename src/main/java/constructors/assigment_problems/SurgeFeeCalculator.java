package constructors.assigment_problems;

/**
 * Problem 4: Exam-Week Surge Fee Calculator
 * Immutability invariants with final class, field, and calculation method.
 */
public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percentage cannot be negative: " + minimumSurgePercent);
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    /**
     * Tiered delay surge fee calculator:
     * - Minutes 1-5: 0.5% per min
     * - Minutes 6-15: 1.0% per min
     * - Minutes 16+: 2.0% per min
     * - Minimum surge percent acts as a floor, but ONLY for delayed orders (delayMinutes > 0).
     */
    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0) {
            throw new IllegalArgumentException("Order value cannot be negative: " + orderValue);
        }
        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay minutes cannot be negative: " + delayMinutes);
        }

        // On-time orders never incur surge fee or minimum floor
        if (delayMinutes == 0) {
            return 0.0;
        }

        // O(1) closed-form calculation
        int b1 = Math.min(delayMinutes, 5);
        int b2 = Math.max(0, Math.min(delayMinutes - 5, 10));
        int b3 = Math.max(0, delayMinutes - 15);

        double tieredFee = (b1 * 0.005 + b2 * 0.010 + b3 * 0.020) * orderValue;
        double floorFee = (minimumSurgePercent / 100.0) * orderValue;

        return Math.max(tieredFee, floorFee);
    }

    public double getMinimumSurgePercent() {
        return minimumSurgePercent;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4: Exam-Week Surge Fee Calculator Demo ===");
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0); // 1.0% floor

        System.out.printf("orderValue = 500, delayMinutes = 0  -> Rs %.1f\n", calc.calculateSurgeFee(500, 0));
        System.out.printf("orderValue = 500, delayMinutes = 1  -> Rs %.1f\n", calc.calculateSurgeFee(500, 1));
        System.out.printf("orderValue = 500, delayMinutes = 16 -> Rs %.1f\n", calc.calculateSurgeFee(500, 16));
    }
}
