/**
 * Abstract class representing a user in the system.
 */
public abstract class User {
    protected String username;
    protected String password;
    protected String role;

    /**
     * Constructs a new User with the specified username, password, and role.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the role of the user.
     *
     * @return the role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the password of the user.
     *
     * @param hashedPassword the new hashed password of the user
     */
    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    /**
     * Displays the role of the user.
     */
    public abstract void displayRole();
}
