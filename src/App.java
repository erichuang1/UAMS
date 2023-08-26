import java.util.Scanner;
import java.io.Console;

public class App {
    public static void main(String[] args) {
        UAMS system = new UAMS();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // stage 1 (before login)
            boolean stage1 = true;
            do {
                System.out.print("Enter a command: ");
                String command = scanner.nextLine();
                switch (command.toLowerCase()) {
                    case "login":
                        // enter username prompt
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();

                        // enter password prompt
                        Console console = System.console();
                        char[] passwordArray = console.readPassword("Enter password: ");
                        String password = new String(passwordArray);

                        // login UAMS
                        ErrorCode errorCode = system.login(username, password);
                        switch (errorCode) {
                            case LOGIN_SUCCESSFUL:
                                System.out.println("Welcome, " + system.currentUsername + "! ");
                                stage1 = false;
                                break;
                            default:
                                System.out.println("You have not been logged in. Reason: " + errorCode);
                        }
                        break;
                    default:
                        System.out.println("You've entered an incorrect command! ");
                }
            } while (stage1);

            // stage 2 (logged in)
            boolean stage2 = true;
            do {
                System.out.print("Enter a command: ");
                String command = scanner.nextLine();
                switch (command.toLowerCase()) {
                    case "createuser":
                        // enter isAdmin prompt
                        boolean isAdmin;
                        while (true) {  // loop while response isn't true/false
                            System.out.print("isAdmin (true/false): ");
                            String response = scanner.nextLine();
                            if (response.toLowerCase().equals("true")) {
                                isAdmin = true;
                                break;
                            } else if (response.toLowerCase().equals("false")) {
                                isAdmin = false;
                                break;
                            } else {
                                System.out.println("Input not acceptable! ");
                            }
                        }

                        // enter username prompt
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();

                        // enter password prompt
                        Console console = System.console();
                        char[] passwordArray = console.readPassword("Enter password: ");
                        String password = new String(passwordArray);

                        // create user via UAMS
                        system.createUser(username, password, stage2);
                        break;
                    case "deleteuser":
                        System.out.print("Enter username: ");
                        system.reactivateUser(scanner.nextLine());
                        break;
                    case "suspenduser":
                        System.out.print("Enter username: ");
                        system.reactivateUser(scanner.nextLine());
                        break;
                    case "reactivateuser":
                        // enter username prompt
                        System.out.print("Enter username: ");
                        system.reactivateUser(scanner.nextLine());
                        break;
                    case "viewallusers":
                        System.out.println(system.viewAllUsers());
                        break;
                    case "resetpassword":
                        // enter username prompt
                        System.out.print("Enter username: ");
                        String username2 = scanner.nextLine();

                        // enter password prompt
                        Console console2 = System.console();
                        char[] passwordArray2 = console2.readPassword("Enter password: ");
                        String password2 = new String(passwordArray2);

                        // reset password via UAMS
                        system.resetPassword(username2, password2);
                        break;
                    case "logout":
                        system.logout();
                        stage2 = false;
                        break;
                    default:
                        System.out.println("You've entered an incorrect command! ");
                }
            } while (stage2);
        }

        // scanner.close();
    }
}
