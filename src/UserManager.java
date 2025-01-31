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

    public static User login(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            currentUser = user;
            return user;
        }
        return null;
    }

    public static boolean canCurrentUserManageUsers() {
        return currentUser != null && currentUser instanceof Admin;
    }

    public static List<User> getAllUsers() {
        if (canCurrentUserManageUsers()) {
            return new ArrayList<>(users);
        }
        return new ArrayList<>();
    }

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

    public static User getCurrentUser() {
        return currentUser;
    }
}
