/**
 * Interface representing a user role in the system.
 * Defines the permissions associated with the role.
 */
public interface UserRole {
    /**
     * Checks if the user role can manage other users.
     * 
     * @return true if the user role can manage users, false otherwise.
     */
    boolean canManageUsers();

    /**
     * Checks if the user role can manage products.
     * 
     * @return true if the user role can manage products, false otherwise.
     */
    boolean canManageProducts();

    /**
     * Checks if the user role can view products.
     * 
     * @return true if the user role can view products, false otherwise.
     */
    boolean canViewProducts();

    /**
     * Gets the name of the user role.
     * 
     * @return the name of the user role.
     */
    String getRoleName();
}
