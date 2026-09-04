package constructors.assigment_problems;

/**
 * Problem 5: Nightly Multi-Kitchen Reconciliation Engine
 * Account reconciliation with static block initialization, instanceof handling,
 * and robust parallel array processing.
 */
public class DeliveryAccount {
    protected static final SurgeFeeCalculator SURGE_CALCULATOR;

    // Static block for class-level configuration initialization
    static {
        SURGE_CALCULATOR = new SurgeFeeCalculator(1.0); // 1% minimum floor
    }

    protected final String studentId;
    protected final double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        if (orderValue < 0) {
            throw new IllegalArgumentException("Order value cannot be negative.");
        }
        this.studentId = studentId.trim();
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        return SURGE_CALCULATOR.calculateSurgeFee(this.orderValue, delayMinutes);
    }

    public String getStudentId() {
        return studentId;
    }

    public double getOrderValue() {
        return orderValue;
    }

    /**
     * Subclass representing premium accounts with preferential settlement.
     */
    public static class PremiumDeliveryAccount extends DeliveryAccount {
        public PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        public PremiumDeliveryAccount(String studentId) {
            super(studentId);
        }
    }

    public void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account != null) {
            double fee = account.calculateSurgeFee(delayMinutes);
            System.out.printf("Account %s processed with fee Rs %.2f\n", account.getStudentId(), fee);
        }
    }

    /**
     * Batch reconciliation processor.
     * Validates parallel array lengths and summarizes processed, null-skipped,
     * premium, and regular accounts with total surge fees.
     */
    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            throw new IllegalArgumentException("Input arrays cannot be null.");
        }

        // Operational integrity check: Mismatched lengths risk charging wrong student
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            throw new IllegalArgumentException(String.format(
                "Batch rejected: array lengths mismatch [accounts=%d, amounts=%d, delayMinutes=%d]. " +
                "Halting to prevent billing errors.",
                accounts.length, amounts.length, delayMinutesArray.length
            ));
        }

        int processedCount = 0;
        int nullSkippedCount = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFees = 0.0;

        for (int i = 0; i < accounts.length; i++) {
            DeliveryAccount acc = accounts[i];
            if (acc == null) {
                nullSkippedCount++;
                continue;
            }

            double fee = acc.calculateSurgeFee(delayMinutesArray[i]);

            if (acc instanceof PremiumDeliveryAccount) {
                premiumCount++;
                // Premium accounts receive 50% discount on surge fees
                fee *= 0.5;
            } else {
                regularCount++;
            }

            grandTotalSurgeFees += fee;
            processedCount++;
        }

        System.out.printf("%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = Rs %.2f\n",
                processedCount, nullSkippedCount, premiumCount, regularCount, grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5: Multi-Kitchen Reconciliation Demo ===");
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}
