import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> users = new ArrayList<>();
    private static User currentUser;

    static {
        users.add(new Admin("admin", hashPassword("admin123")));
    }

    public static boolean register(String username, String password, String role) {
        if (getUserByUsername(username) != null) {
            return false;
        }

        User newUser;
        String hashedPassword = hashPassword(password);
        if (role.equalsIgnoreCase("admin")) {
            newUser = new Admin(username, hashedPassword);
        } else {
            newUser = new Employee(username, hashedPassword);
        }
        users.add(newUser);
        return true;
    }

    public static User login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            currentUser = user;
            return user;
        }
        return null;
    }

    private static User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return password; 
        }
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
