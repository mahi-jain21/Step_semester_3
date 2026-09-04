package oop.assigment_problems;

class ParkingSlot {
    private String slotNo;
    private int capacity;
    private int occupiedCount;

    public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        if (capacity <= 0 || occupiedCount < 0 || occupiedCount > capacity) {
            throw new IllegalArgumentException("Invalid parking slot configuration.");
        }
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public boolean allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
            return true;
        }
        return false;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedCount() {
        return occupiedCount;
    }
}

public class ParkingAllocation {

    /**
     * Finds the first available slot where occupiedCount < capacity.
     */
    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Null-safe allotment validator.
     */
    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot available = findAvailableSlot(slots);
        
        // Null check to prevent NullPointerException
        if (available == null) {
            System.out.println("No slots available for " + vehicleNo);
        } else {
            available.allot(vehicleNo);
            System.out.println(vehicleNo + " allotted to slot " + available.getSlotNo());
        }
    }

    /*
     * EXPLANATION ON REFERENCE MECHANICS:
     * Passing the ParkingSlot array into these methods does NOT copy the slots themselves. 
     * In Java, object references are passed by value. When the array is passed, the method receives 
     * a copy of the reference pointing to the array object. The array itself contains references pointing 
     * to the actual ParkingSlot objects located in heap memory. Because the method acts on these direct 
     * references, calling 'available.allot(vehicleNo)' modifies the exact same slot object in memory, 
     * rather than a copy.
     */

    public static void main(String[] args) {
        System.out.println("=== F3. Object References, Null Safety, and a Mutating Method ===");

        // Test Case 1: Slots available
        ParkingSlot[] slots1 = new ParkingSlot[] {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        System.out.println("Case 1 Seeding: A1 (3/4), A2 (5/5)");
        safeAllot(slots1, "TN09AB1234"); // Should allot to A1

        System.out.println();

        // Test Case 2: Slots completely full
        ParkingSlot[] slots2 = new ParkingSlot[] {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        System.out.println("Case 2 Seeding: A1 (4/4), A2 (5/5)");
        safeAllot(slots2, "TN09AB1234"); // Should print "No slots available"
    }
}
