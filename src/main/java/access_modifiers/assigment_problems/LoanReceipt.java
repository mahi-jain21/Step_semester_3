package access_modifiers.assigment_problems;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Problem 5: Immutable Loan Receipt & Nightly Circulation Ledger
 * Truly immutable loan receipt with defensive copying, wither pattern,
 * reference-only subclass, and fault-tolerant nightly batch reconciliation.
 */
public class LoanReceipt {
    private static final Pattern BOOK_ID_PATTERN;

    static {
        // One-time static compilation of book ID format: "BK-" followed by exactly 3 digits
        BOOK_ID_PATTERN = Pattern.compile("^BK-\\d{3}$");
    }

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty.");
        }
        if (bookIds == null) {
            throw new IllegalArgumentException("Book IDs array cannot be null.");
        }

        // Validate all book IDs and defensively copy
        this.bookIds = new String[bookIds.length];
        for (int i = 0; i < bookIds.length; i++) {
            String id = bookIds[i];
            if (id == null || !BOOK_ID_PATTERN.matcher(id.trim()).matches()) {
                throw new IllegalArgumentException("Invalid book ID format: '" + id + "'. Must match BK- followed by 3 digits.");
            }
            this.bookIds[i] = id.trim();
        }

        this.memberId = memberId.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    /**
     * Defensive copy on output.
     */
    public String[] getBookIds() {
        return bookIds.clone();
    }

    /**
     * Wither method returning a new immutable LoanReceipt with the corrected book ID.
     */
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Invalid book index: " + index);
        }
        if (newId == null || !BOOK_ID_PATTERN.matcher(newId.trim()).matches()) {
            throw new IllegalArgumentException("Invalid replacement book ID: " + newId);
        }

        String[] copy = this.bookIds.clone();
        copy[index] = newId.trim();
        return new LoanReceipt(this.memberId, copy);
    }

    /**
     * Problem 5 Subclass: Reference-only loan receipt with room number.
     */
    public static class ReferenceOnlyLoanReceipt extends LoanReceipt {
        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            this.roomNumber = (roomNumber == null) ? "General Reading Room" : roomNumber.trim();
        }

        public String getRoomNumber() {
            return roomNumber;
        }
    }

    /**
     * Nightly batch processor safely handling regular receipts, reference-only receipts,
     * and null entries without throwing exceptions.
     */
    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        if (receipts == null) {
            return "0 processed | 0 null skipped | 0 reference-only | 0 regular";
        }

        int processed = 0;
        int nullSkipped = 0;
        int refOnly = 0;
        int regular = 0;

        for (LoanReceipt r : receipts) {
            if (r == null) {
                nullSkipped++;
                continue;
            }

            if (r instanceof ReferenceOnlyLoanReceipt) {
                refOnly++;
            } else {
                regular++;
            }
            processed++;
        }

        return String.format("%d processed | %d null skipped | %d reference-only | %d regular",
                processed, nullSkipped, refOnly, regular);
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5: LoanReceipt Demo ===");

        // Invalid format test
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("new LoanReceipt('LIB-8841', ['BK-100', 'bad']) -> construction rejected correctly");
        }

        // Defensive copy test
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println("Original receipt remains unmutated: " + r.getBookIds()[0]); // BK-100

        // Wither test
        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-999");
        System.out.println("Corrected receipt: " + Arrays.toString(corrected.getBookIds()));

        // Batch processing
        LoanReceipt[] batch = new LoanReceipt[]{
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println("Batch summary: " + processNightlyCirculation(batch));
    }
}
