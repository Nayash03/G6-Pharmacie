import java.util.Scanner;

public class UserManagementMenu {
    public static void init() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Gestion des utilisateurs:\n1 - Liste des utilisateurs\n2 - Supprimer un utilisateur\n3 - Promouvoir un utilisateur\n4 - Retour");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    displayUserList();
                    break;

                case "2":
                    deleteUser(scanner);
                    break;

                case "3":
                    promoteUser(scanner);
                    break;

                case "4":
                    running = false;
                    break;
            }
        }
    }

    private static void displayUserList() {
        System.out.println("Liste des utilisateurs:");
        UserManager.getAllUsers().forEach(user -> 
            System.out.println(user.getUsername() + " (" + user.getRole() + ")"));
    }

    private static void deleteUser(Scanner scanner) {
        System.out.println("Entrez le nom d'utilisateur à supprimer:");
        String username = scanner.nextLine();
        if (UserManager.deleteUser(username)) {
            System.out.println("Utilisateur supprimé avec succès");
        } else {
            System.out.println("Impossible de supprimer cet utilisateur");
        }
    }

    private static void promoteUser(Scanner scanner) {
        System.out.println("Entrez le nom d'utilisateur à promouvoir en admin:");
        String username = scanner.nextLine();
        if (UserManager.promoteToAdmin(username)) {
            System.out.println("Utilisateur promu en admin avec succès");
        } else {
            System.out.println("Impossible de promouvoir cet utilisateur");
        }
    }
}
