package oop.assigment_problems;

class Employee {
    private String empId;
    private String empName;
    private double salary;

    public Employee(String empId, String empName, double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return this.salary;
    }

    public String getEmpName() {
        return this.empName;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        if (teamBonus < 0) {
            throw new IllegalArgumentException("Team bonus cannot be negative.");
        }
        this.teamBonus = teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(String empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        if (stipendCap < 0) {
            throw new IllegalArgumentException("Stipend cap cannot be negative.");
        }
        this.stipendCap = stipendCap;
    }

    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class EmployeeDemo {

    public static void main(String[] args) {
        System.out.println("=== F2. Extending Employee Without Touching It ===");

        // Create three types of employees
        Employee plain = new Employee("EMP-1001", "Karan", 40000.0);
        Employee manager = new ManagerEmployee("MGR-2001", "Divya", 70000.0, 8000.0);
        Employee intern = new InternEmployee("INT-3001", "Meera", 12000.0, 10000.0);

        // Print pay using instanceof to decide behavior
        printPay(plain);
        printPay(manager);
        printPay(intern);
    }

    public static void printPay(Employee emp) {
        if (emp == null) {
            System.out.println("Null employee reference.");
            return;
        }

        if (emp instanceof ManagerEmployee) {
            ManagerEmployee mgr = (ManagerEmployee) emp;
            System.out.println("Manager effective pay: Rs " + mgr.effectiveSalary());
        } else if (emp instanceof InternEmployee) {
            InternEmployee intern = (InternEmployee) emp;
            System.out.println("Intern effective pay: Rs " + intern.effectiveSalary());
        } else {
            System.out.println("Plain employee pay: Rs " + emp.getSalary());
        }
    }
}
