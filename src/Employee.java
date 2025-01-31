/**
 * The Employee class represents an employee user in the system.
 * It extends the User class and provides specific implementation for an employee.
 */
public class Employee extends User {
    /**
     * Constructs a new Employee with the specified username and password.
     *
     * @param username the username of the employee
     * @param password the password of the employee
     */
    public Employee(String username, String password) {
        super(username, password, "Employee");
    }

    /**
     * Displays the role of the employee.
     */
    @Override
    public void displayRole() {
        System.out.println("Role: Employee");
    }
}
