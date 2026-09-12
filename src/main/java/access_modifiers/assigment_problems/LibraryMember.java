package access_modifiers.assigment_problems;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Problems 1 & 4: LibraryMember JavaBean, Chained Constructors, Access Levels & Security Answer
 * Combines field access level rules, constructor validation, JavaBean conventions,
 * write-once membershipId, and write-only security answer.
 */
public class LibraryMember {
    // Problem 1: 4 fields with tailored visibility
    private String membershipId;
    /* package-private */ String branchCode;
    protected double finesOwed;
    public String displayName;

    // Problem 4 JavaBean fields
    private String name;
    private boolean premiumMember;
    private boolean membershipIdSetOnce = false;
    private String securityAnswerHash; // Write-only hashed state

    /**
     * Problem 4: No-arg constructor chaining to name-only constructor.
     */
    public LibraryMember() {
        this(null, null);
    }

    /**
     * Problem 4: Name-only constructor chaining to id+name constructor.
     */
    public LibraryMember(String name) {
        this(null, name);
    }

    /**
     * Problem 4: Primary id + name constructor.
     */
    public LibraryMember(String membershipId, String name) {
        if (membershipId != null) {
            validateMembershipId(membershipId);
            this.membershipId = membershipId.trim();
            this.membershipIdSetOnce = true;
        }
        this.name = name;
        this.displayName = name;
    }

    /**
     * Problem 1: Parameterized constructor validating membershipId (min 4 chars).
     */
    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        validateMembershipId(membershipId);
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
        this.name = displayName;
        this.membershipIdSetOnce = true;
    }

    private static void validateMembershipId(String id) {
        if (id == null || id.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid membershipId: must be non-blank and at least 4 characters long.");
        }
    }

    // JavaBean getX / setX methods

    public String getMembershipId() {
        return membershipId;
    }

    /**
     * Write-once setter for membershipId.
     * Subsequent calls are silently ignored.
     */
    public void setMembershipId(String id) {
        if (!membershipIdSetOnce && id != null) {
            validateMembershipId(id);
            this.membershipId = id.trim();
            this.membershipIdSetOnce = true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.displayName = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    /**
     * Write-only security answer property.
     * Transforms and stores answer deterministically; no getter exists.
     */
    public void setSecurityAnswer(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            return;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(answer.trim().toLowerCase().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            this.securityAnswerHash = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            this.securityAnswerHash = Integer.toHexString(answer.trim().hashCode());
        }
    }

    public String getBranchCode() {
        return branchCode;
    }

    public double getFinesOwed() {
        return finesOwed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static void main(String[] args) {
        System.out.println("=== Problems 1 & 4: LibraryMember Demo ===");

        // Problem 1: Validation
        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println("new LibraryMember('LB9', ...) -> construction rejected correctly");
        }

        LibraryMember m1 = new LibraryMember("LB94", "BR1", 0, "Priya Nair");
        System.out.println("Valid ID LB94 created: " + m1.getMembershipId());

        // Problem 4: Chaining & JavaBean
        LibraryMember m2 = new LibraryMember("Priya Nair");
        System.out.println("Name-only member ID: " + m2.getMembershipId()); // null

        LibraryMember m3 = new LibraryMember("LIB-8841", "Priya Nair");
        System.out.println("ID+Name member ID: " + m3.getMembershipId()); // LIB-8841

        // Write-once demonstration
        LibraryMember m4 = new LibraryMember();
        m4.setMembershipId("LIB-8841");
        m4.setMembershipId("FAKE-0000");
        System.out.println("Write-once ID result: " + m4.getMembershipId()); // LIB-8841

        m4.setSecurityAnswer("MySecretPet");
        System.out.println("Security answer set (write-only, no getter exists).");
    }
}
