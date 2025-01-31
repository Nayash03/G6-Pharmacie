import java.util.ArrayList;
import java.util.List;

/**
 * The Admin class represents an administrator user who can manage other users.
 */
public class Admin extends User {
    private List<User> users;

    /**
     * Constructs a new Admin with the specified username and password.
     *
     * @param username the username of the admin
     * @param password the password of the admin
     */
    public Admin(String username, String password) {
        super(username, password, "Admin");
        this.users = new ArrayList<>();
    }

    /**
     * Adds a user to the admin's list of managed users.
     *
     * @param user the user to be added
     */
    public void addUser(User user) {
        users.add(user);
        System.out.println("User added: " + user.getUsername());
    }

    /**
     * Removes a user from the admin's list of managed users.
     *
     * @param user the user to be removed
     */
    public void removeUser(User user) {
        users.remove(user);
        System.out.println("User removed: " + user.getUsername());
    }

    /**
     * Displays the role of the admin.
     */
    @Override
    public void displayRole() {
        System.out.println("Role: Admin");
    }
}
