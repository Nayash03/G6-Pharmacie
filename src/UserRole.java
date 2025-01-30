public interface UserRole {
    boolean canManageUsers();
    boolean canManageProducts();
    boolean canViewProducts();
    String getRoleName();
}
