import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Manages user registration, login, and user-related operations.
 */
public class UserManager {
    private static List<User> users = new ArrayList<>();
    private static User currentUser;
    private static final String USER_FILE = "users.json";

    static {
        loadUsersFromFile();
        if (users.isEmpty()) {
            users.add(new Admin("admin", hashPassword("admin123")));
            saveUsersToFile();
        }
    }

    /**
     * Registers a new user.
     * 
     * @param username the username of the new user
     * @param password the password of the new user
     * @param role the role of the new user (admin or employee)
     * @return true if registration is successful, false otherwise
     */
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
        saveUsersToFile();
        return true;
    }

    /**
     * Logs in a user.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @return the logged-in user if credentials are correct, null otherwise
     */
    public static User login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            currentUser = user;
            return user;
        }
        return null;
    }

    /**
     * Checks if the current user can manage other users.
     * 
     * @return true if the current user is an admin, false otherwise
     */
    public static boolean canCurrentUserManageUsers() {
        return currentUser != null && currentUser instanceof Admin;
    }

    /**
     * Gets a list of all users.
     * 
     * @return a list of all users if the current user is an admin, an empty list otherwise
     */
    public static List<User> getAllUsers() {
        if (canCurrentUserManageUsers()) {
            return new ArrayList<>(users);
        }
        return new ArrayList<>();
    }

    /**
     * Deletes a user by username.
     * 
     * @param username the username of the user to delete
     * @return true if the user is successfully deleted, false otherwise
     */
    public static boolean deleteUser(String username) {
        if (!canCurrentUserManageUsers()) {
            return false;
        }
        User userToDelete = getUserByUsername(username);
        if (userToDelete != null && !userToDelete.getRole().equals("Admin")) {
            users.remove(userToDelete);
            saveUsersToFile();
            return true;
        }
        return false;
    }

    /**
     * Promotes an employee to an admin.
     * 
     * @param username the username of the user to promote
     * @return true if the user is successfully promoted, false otherwise
     */
    public static boolean promoteToAdmin(String username) {
        if (!canCurrentUserManageUsers()) {
            return false;
        }
        User userToPromote = getUserByUsername(username);
        if (userToPromote != null && userToPromote.getRole().equals("Employee")) {
            int index = users.indexOf(userToPromote);
            users.set(index, new Admin(userToPromote.getUsername(), userToPromote.getPassword()));
            saveUsersToFile();
            return true;
        }
        return false;
    }

    /**
     * Gets a user by username.
     * 
     * @param username the username of the user to find
     * @return the user if found, null otherwise
     */
    private static User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Hashes a password using SHA-256.
     * 
     * @param password the password to hash
     * @return the hashed password
     */
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

    /**
     * Loads users from a file.
     */
    private static void loadUsersFromFile() {
        try (FileReader reader = new FileReader(USER_FILE)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<HashMap<String, String>>>() {}.getType();
            List<HashMap<String, String>> data = gson.fromJson(reader, listType);
            if (data != null) {
                for (HashMap<String, String> entry : data) {
                    String username = entry.get("username");
                    String password = entry.get("password");
                    String role = entry.get("role");
                    if ("admin".equalsIgnoreCase(role)) {
                        users.add(new Admin(username, password));
                    } else {
                        users.add(new Employee(username, password));
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Saves users to a file.
     */
    private static void saveUsersToFile() {
        try (FileWriter writer = new FileWriter(USER_FILE)) {
            Gson gson = new Gson();
            List<HashMap<String, String>> data = new ArrayList<>();
            for (User u : users) {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", u.getUsername());
                map.put("password", u.getPassword());
                map.put("role", u.getRole());
                data.add(map);
            }
            gson.toJson(data, writer);
        } catch (Exception e) {
        }
    }

    /**
     * Gets the current logged-in user.
     * 
     * @return the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }
}
