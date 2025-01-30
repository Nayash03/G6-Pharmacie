public class Employee extends User {
    public Employee(String username, String password) {
        super(username, password, "Employee");
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Employee");
    }
}
