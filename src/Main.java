import java.util.*;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("1 - Login\n2 - Register\n3 - Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Username:");
                    String username = scanner.nextLine();
                    System.out.println("Password:");
                    String password = scanner.nextLine();
                    
                    User user = UserManager.login(username, password);
                    if (user != null) {
                        System.out.println("Login successful! Welcome " + user.getUsername());
                        loggedIn = true;
                    } else {
                        System.out.println("Invalid credentials!");
                    }
                    break;

                case "2":
                    System.out.println("Username:");
                    String newUsername = scanner.nextLine();
                    System.out.println("Password:");
                    String newPassword = scanner.nextLine();
                    System.out.println("Role (admin/employee):");
                    String role = scanner.nextLine();

                    if (UserManager.register(newUsername, newPassword, role)) {
                        System.out.println("Registration successful! Please login.");
                    } else {
                        System.out.println("Username already exists!");
                    }
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
            }
        }

        boolean running = true;

        while (running) {
            if (UserManager.canCurrentUserManageUsers()) {
                System.out.println("Choisissez une option :\n1 - Ajouter un produit aux stocks\n2 - Commande\n3 - Liste des stocks \n4 - Enlever un produit\n5 - Produit en faible stock\n6 - Rechercher un produit\n7 - Gestion des utilisateurs\n8 - Exit");
            } else {
                System.out.println("Choisissez une option :\n1 - Ajouter un produit aux stocks\n2 - Commande\n3 - Liste des stocks \n4 - Enlever un produit\n5 - Produit en faible stock\n6 - Rechercher un produit\n7 - Exit");
            }
            
            String answer = scanner.nextLine();
            switch (answer) {
                case "1" , "add" , "ajout":
                    AddProduct.init();
                    break;

                case "2" , "command" , "commande" :
                    System.out.println("Choisissez une option :\n1 - Liste des commandes\n2 - Passer une commande\n3 - Exit");
                    String answerScanner = scanner.nextLine();
                    switch (answerScanner) {
                        case "1" :
                            ReadRequest.init();
                            break;

                        case "2" :
                            Map<String,Integer> requestClient = new HashMap<>();
                            System.out.println("Nom du produit");
                            String answerRequest = scanner.nextLine();
                            System.out.println("Quantité du produit");
                            String answerNumber = scanner.nextLine();
                            if (answerNumber.matches("\\d+")){
                                requestClient.put(answerRequest,Integer.parseInt(answerNumber));
                                RequestProduct.registerRequest(requestClient,"Standard");
                            } else {
                                System.out.println("Numéro invalide, procédure annulé");
                            }
                            break;
                    }

                    break;

                case "3" , "list" , "liste" , "stock" :
                    ListProduct.init();
                    break;

                case "4" , "remove":
                    DeleteProduct.init();
                    break;

                case "5" , "low stock" :
                    LowStockViewer.init();
                    break;

                case "6", "search", "rechercher":
                    SearchProduct.init();
                    break;

                case "7", "Exit":
                    if (UserManager.canCurrentUserManageUsers()) {
                        UserManagementMenu.init();
                    } else {
                        System.out.println("Vous quittez l'application");
                        running = false;
                    }
                    break;

                case "8", "exit":
                    if (UserManager.canCurrentUserManageUsers()) {
                        System.out.println("Vous quittez l'application");
                        running = false;
                    }
                    break;

            }
        }
    }
}