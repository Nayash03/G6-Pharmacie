import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<User> users;

    public Admin(String username, String password) {
        super(username, password, "Admin");
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getUsername());
    }

    public void removeUser(User user) {
        users.remove(user);
        System.out.println("User removed: " + user.getUsername());
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Admin");
    }
}
