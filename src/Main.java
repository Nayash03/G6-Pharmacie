import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Choisissez une option :\n1 - Ajouté un produit aux stocks\n2 - commande\n3 - Liste des stocks\n4 - Exit");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1" , "add" , "ajout":
                    System.out.println("ajout");
                    break;

                case "2" , "command" , "commande" :
                    System.out.println("Choisissez une option :\n1 - Liste des commandes\n2 - Passer une commande\n3 - Exit");
                    String answerScanner = scanner.nextLine();
                    switch (answerScanner) {
                        case "1" :
                            List<Map<String, Object>> commandes = new ArrayList<>();
//                            System.out.println("Liste des commandes");
                            ReadRequest.lireCommandes();
                            ReadRequest.afficherCommandes(commandes);
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
                    ;
                    System.out.println("Liste des stocks");
                    break;

                case "4" , "exit" , "out": System.out.println("Vous quittez l'application"); running = false; break;

            }
        }
    }
}