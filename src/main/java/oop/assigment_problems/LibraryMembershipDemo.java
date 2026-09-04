package oop.assigment_problems;

// Broken version showcasing the overwrite bug
class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    public BrokenLibraryMember(String n, String id, int books) {
        name = n;
        memberId = id;
        booksIssued = books;
    }

    public static void printDetails() {
        System.out.println("Broken Member Name: " + name + " | ID: " + memberId + " | Books: " + booksIssued);
    }
}

// Redesigned correct version
class LibraryMember {
    // Instance fields for unique member data
    private String name;
    private String memberId;
    private int booksIssued;

    // Static fields shared across all instances
    private static final String libraryName = "City Central Library";
    private static int memberCount = 0;

    public LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        // Automatically derive ID from static count
        this.memberId = "LM-" + (1000 + memberCount);
    }

    public void printMemberCard() {
        System.out.println(name + " | " + memberId + " | Library: " + libraryName + " | Books Issued: " + booksIssued);
    }

    public static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

/*
 * EXPLANATION OF STATIC MISUSE:
 * - name: Making this static is incorrect because every member has their own name. Sharing this across the class 
 *   causes a newly registered member to overwrite the names of all previously created members.
 * - memberId: Since member IDs are unique identifier tokens, making this static means every single member will 
 *   share the same ID (the ID of the last member created), which completely breaks identity lookups.
 * - booksIssued: Books checked out belong to a specific individual. Making it static aggregates it globally,
 *   so if one person checks out a book, it updates the checkout count for every single member in the library.
 */

public class LibraryMembershipDemo {

    public static void main(String[] args) {
        System.out.println("=== F4. Designing the Instance/Static Boundary ===");

        System.out.println("--- Running Broken Version ---");
        // Creating two members
        BrokenLibraryMember member1 = new BrokenLibraryMember("Aditi", "LM-1001", 3);
        // Rohan overwrites Aditi's data because fields are static!
        BrokenLibraryMember member2 = new BrokenLibraryMember("Rohan", "LM-1002", 5);

        // Printing both names shows the bug: Rohan overwrote Aditi
        System.out.println("Member 1 name: " + BrokenLibraryMember.name);
        System.out.println("Member 2 name: " + BrokenLibraryMember.name);

        System.out.println("\n--- Running Fixed Version ---");
        // Redesigned clean implementation
        LibraryMember fixed1 = new LibraryMember("Aditi", 3);
        LibraryMember fixed2 = new LibraryMember("Rohan", 5);

        fixed1.printMemberCard();
        fixed2.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
