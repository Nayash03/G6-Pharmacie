import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choisissez une option :\n1 - Ajouté un produit aux stocks\n2 - Commande\n3 - Liste des stocks \n4 - Enlever un produit\n5 - Produit en faible stock\n6 - Exit");
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

                case "6" , "exit" , "out": System.out.println("Vous quittez l'application"); running = false; break;

                case "4" , "remove":
                    DeleteProduct.init();
                    break;

                case "5" , "low stock" :
                    LowStockViewer.init();
                    break;

            }
        }
    }
}