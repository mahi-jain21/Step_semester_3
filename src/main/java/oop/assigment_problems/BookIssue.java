package oop.assigment_problems;

// Custom Exception
class InvalidDaysException extends IllegalArgumentException {
    public InvalidDaysException(String message) {
        super(message);
    }
}

public class BookIssue {
    // Instance fields
    private String title;
    private String borrowerName;
    private int daysOverdue;

    /**
     * Parameterized Constructor.
     */
    public BookIssue(String title, String borrowerName, int daysOverdue) {
        if (daysOverdue < 0) {
            throw new InvalidDaysException("Days overdue cannot be negative.");
        }
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    /**
     * Computes the fine amount (Rs 5 per day if daysOverdue > 0).
     * This is an INSTANCE method because it operates on the specific state of a single BookIssue object.
     */
    public double fineAmount() {
        if (this.daysOverdue > 0) {
            return this.daysOverdue * 5.0;
        }
        return 0.0;
    }

    /**
     * Checks if a book is overdue by more than 14 days.
     */
    public boolean isSeverelyOverdue() {
        return this.daysOverdue > 14;
    }

    /**
     * Getter for title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Getter for days overdue.
     */
    public int getDaysOverdue() {
        return this.daysOverdue;
    }

    /**
     * Accumulates fines across an array of issues.
     * This is a STATIC method because it is a general utility function that operates on an input array
     * passed as an argument rather than the internal state of any single instance.
     */
    public static double totalFineCollected(BookIssue[] issues) {
        if (issues == null) {
            throw new IllegalArgumentException("Issues array reference cannot be null.");
        }
        double total = 0;
        for (BookIssue issue : issues) {
            if (issue != null) {
                total += issue.fineAmount();
            }
        }
        return total;
    }

    /*
     * JUSTIFICATION FOR STATIC VS INSTANCE BOUNDARY:
     * - fineAmount() is an instance method because the fine depends directly on the unique state 
     *   (specifically, daysOverdue) of an individual book issue object.
     * - totalFineCollected() is static because it is a global utility computation. It does not belong
     *   to any single book issue instance. Instead, it accepts an array of instances as an input parameter
     *   and sums up their collective values.
     */

    public static void main(String[] args) {
        System.out.println("=== F1. From Procedural Mess to a Working Library Fine System ===");

        // Create 5 BookIssue objects
        BookIssue[] issues = new BookIssue[] {
            new BookIssue("Clean Code", "Aditi", 18),
            new BookIssue("Effective Java", "Rohan", 5),
            new BookIssue("Refactoring", "Sunil", 0),
            new BookIssue("DSA Handbook", "Karan", 21),
            new BookIssue("Design Patterns", "Meera", 9)
        };

        // Print details and severity status
        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.printf("%s - %d days - %s\n", issue.getTitle(), issue.getDaysOverdue(), status);
        }

        // Print total fine using class name
        double grandTotal = BookIssue.totalFineCollected(issues);
        System.out.println("Total fine collected: Rs " + grandTotal);
    }
}
