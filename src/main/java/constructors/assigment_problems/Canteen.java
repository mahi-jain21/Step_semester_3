package constructors.assigment_problems;

import java.util.Arrays;

/**
 * Problem 3: Canteen Trust-Score Ranking Engine
 * Comparison and deterministic ranking sort without using built-in sort utilities.
 */
public class Canteen implements Comparable<Canteen> {
    private static final int DEFAULT_TRUST_SCORE = 3;

    private final String canteenCode;
    private final String canteenName;
    private final int trustScore;

    /**
     * Primary constructor resolving name clashes using this.
     */
    public Canteen(String canteenCode, String canteenName, int trustScore) {
        if (canteenCode == null || canteenCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Canteen code cannot be empty.");
        }
        if (canteenName == null || canteenName.trim().isEmpty()) {
            throw new IllegalArgumentException("Canteen name cannot be empty.");
        }
        this.canteenCode = canteenCode.trim();
        this.canteenName = canteenName.trim();
        this.trustScore = trustScore;
    }

    /**
     * Secondary constructor chaining to primary with default trust score of 3.
     */
    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, DEFAULT_TRUST_SCORE);
    }

    /**
     * Comparison logic:
     * 1. Higher trustScore ranks first (descending).
     * 2. Tie-break: Case-insensitive canteenCode (ascending).
     * 3. Tie-break: Canteen name length (ascending).
     * 4. Tie-break: Exact canteenName (ascending).
     */
    @Override
    public int compareTo(Canteen other) {
        if (other == null) return -1;

        // Higher trust score first
        if (this.trustScore != other.trustScore) {
            return Integer.compare(other.trustScore, this.trustScore);
        }

        // Case-insensitive code comparison
        int codeComp = this.canteenCode.compareToIgnoreCase(other.canteenCode);
        if (codeComp != 0) {
            return codeComp;
        }

        // Preserve case deterministic order if codes differ only in letter case
        int exactCodeComp = this.canteenCode.compareTo(other.canteenCode);
        if (exactCodeComp != 0) {
            return exactCodeComp;
        }

        // Name length
        int lenComp = Integer.compare(this.canteenName.length(), other.canteenName.length());
        if (lenComp != 0) {
            return lenComp;
        }

        return this.canteenName.compareTo(other.canteenName);
    }

    /**
     * Custom insertion sort ranking engine without calling built-in sort.
     */
    public static Canteen[] rankCanteens(Canteen[] canteens) {
        if (canteens == null) return new Canteen[0];
        Canteen[] sorted = canteens.clone();

        for (int i = 1; i < sorted.length; i++) {
            Canteen key = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j].compareTo(key) > 0) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = key;
        }
        return sorted;
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public int getTrustScore() {
        return trustScore;
    }

    @Override
    public String toString() {
        return canteenCode;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3: Canteen Ranking Engine Demo ===");
        Canteen[] list = {
            new Canteen("HB3-C", "Spice Junction", 3),
            new Canteen("hb1-c", "Grand Mess", 5),
            new Canteen("HB2-C", "Southern Treats") // defaults to 3
        };

        Canteen[] ranked = rankCanteens(list);
        String[] codes = new String[ranked.length];
        for (int i = 0; i < ranked.length; i++) {
            codes[i] = ranked[i].getCanteenCode();
        }
        System.out.println("Output: " + Arrays.toString(codes));
    }
}
