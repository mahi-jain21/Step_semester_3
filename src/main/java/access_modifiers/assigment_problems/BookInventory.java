package access_modifiers.assigment_problems;

/**
 * Problem 3: Book Copy Circulation Guard
 * Encapsulation guard ensuring available copy counts never exceed total or drop below 0.
 */
public class BookInventory {
    private final int copiesTotal;
    private int copiesAvailable;

    /**
     * Constructor rejecting zero or negative total copies.
     */
    public BookInventory(int copiesTotal) {
        if (copiesTotal <= 0) {
            throw new IllegalArgumentException("Total copies must be positive: " + copiesTotal);
        }
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    /**
     * Checks out a copy. Silently rejects if no copies are available.
     */
    public void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    /**
     * Checks in a copy. Silently rejects if already at total capacity.
     */
    public void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public int getCopiesTotal() {
        return copiesTotal;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3: BookInventory Demo ===");
        try {
            new BookInventory(0);
        } catch (IllegalArgumentException e) {
            System.out.println("new BookInventory(0) -> construction rejected correctly");
        }

        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // 4th checkout rejected
        System.out.println("Available after 4 checkouts: " + b.getCopiesAvailable()); // 0

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // 4th checkin rejected
        System.out.println("Available after 4 checkins: " + b.getCopiesAvailable()); // 3
    }
}
