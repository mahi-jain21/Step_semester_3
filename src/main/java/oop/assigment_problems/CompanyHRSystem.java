package oop.assigment_problems;

class CompanyEmployeeRecord {
    private String name;
    private String empId;
    private Employee employee; // Demonstrates composition (object field is an object reference)
    private ParkingSlot slot;  // Demonstrates composition (can be null-checked)

    // Static counter to track records
    public static int totalRecords = 0;

    public CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        if (name == null || empId == null || employee == null) {
            throw new IllegalArgumentException("Name, empId, and employee fields cannot be null.");
        }
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++; // Increment static counter
    }

    /**
     * Formats the employee profile line.
     * Demonstrates polymorphism (invokes subclass salary behaviors) and null-safety.
     */
    public void fullProfile() {
        double salary = 0;
        
        // Polymorphic check for salary details
        if (employee instanceof ManagerEmployee) {
            salary = ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            salary = ((InternEmployee) employee).effectiveSalary();
        } else {
            salary = employee.getSalary();
        }

        // Null-safe check for parking slot allocation reference
        String slotDisplay = (slot == null) ? "no parking assigned" : slot.getSlotNo();

        System.out.printf("%s | Pay: Rs %.1f | Slot: %s\n", name, salary, slotDisplay);
    }
}

public class CompanyHRSystem {

    public static void main(String[] args) {
        System.out.println("=== F5. Capstone: A Small HR + Parking Allocation Mini-System ===");

        // Create some parking slots
        ParkingSlot slotA1 = new ParkingSlot("A1", 4, 3);
        ParkingSlot slotA2 = new ParkingSlot("A2", 5, 4);

        // Create some employees (plain and manager types)
        Employee divya = new ManagerEmployee("MGR-2001", "Divya", 70000.0, 8000.0);
        Employee karan = new Employee("EMP-1001", "Karan", 40000.0);
        Employee meera = new InternEmployee("INT-3001", "Meera", 12000.0, 10000.0);

        // Perform slot allotment (mutates slot counts in memory)
        slotA1.allot("TN-DIVYA");
        slotA2.allot("TN-KARAN");

        // Build company employee records linking employees with slots
        // Meera does not get parking slot (passed as null on purpose)
        CompanyEmployeeRecord rec1 = new CompanyEmployeeRecord("Divya", "MGR-2001", divya, slotA1);
        CompanyEmployeeRecord rec2 = new CompanyEmployeeRecord("Karan", "EMP-1001", karan, slotA2);
        CompanyEmployeeRecord rec3 = new CompanyEmployeeRecord("Meera", "INT-3001", meera, null);

        // Print profiles
        rec1.fullProfile();
        rec2.fullProfile();
        rec3.fullProfile();

        // Print total count from static counter
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
