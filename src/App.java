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
                String command = scanner.nextLine().trim();
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
                                System.out.println("Welcome, " + system.currentUsername + "! \n");
                                stage1 = false;
                                break;
                            default:
                                System.out.println("You have not been logged in. Reason: " + errorCode + "\n");
                        }
                        break;
                    default:
                        System.out.println("You've entered an incorrect command! \nHere's a list of available commands: \n    login\n");
                }
            } while (stage1);

            // stage 2 (logged in)
            boolean stage2 = true;
            do {
                System.out.print("Enter a command: ");
                String command = scanner.nextLine().trim();
                switch (command.toLowerCase()) {
                    case "createuser":
                        // enter isAdmin prompt
                        boolean isAdmin1;
                        do { // retry when response is invalid
                            System.out.print("isAdmin (true/false): ");
                            String input1 = scanner.nextLine().trim();
                            if (input1.toLowerCase().equals("true")) {
                                isAdmin1 = true;
                                break;
                            } else if (input1.toLowerCase().equals("false")) {
                                isAdmin1 = false;
                                break;
                            } else {
                                System.out.println("Input not acceptable! \n");
                            }
                        } while (true);

                        // prompt enter username
                        System.out.print("Enter username: ");
                        String username1 = scanner.nextLine().trim();

                        // prompt enter password 
                        Console console1 = System.console();
                        char[] passwordArray1 = console1.readPassword("Enter password: ");
                        String password1 = new String(passwordArray1);

                        // UAMS action (create user)
                        ErrorCode err1 = system.createUser(username1, password1, isAdmin1);
                        System.out.println("Status: " + err1 + "\n");
                        break;
                    case "deleteuser":
                        System.out.print("Enter username: ");
                        String input2 = scanner.nextLine().trim();

                        // UAMS action (delete user)
                        ErrorCode err2 = system.deleteUser(input2);

                        System.out.println("Status: " + err2 + "\n");
                        break;
                    case "suspenduser":
                        System.out.print("Enter username: ");
                        String input3 = scanner.nextLine().trim();

                        // UAMS action (suspend user)
                        ErrorCode err3 = system.suspendUser(input3);

                        System.out.println("Status: " + err3 + "\n");
                        break;
                    case "reactivateuser":
                        // prompt enter username
                        System.out.print("Enter username: ");
                        String input4 = scanner.nextLine().trim();

                        // UAMS action (reactivate user)
                        ErrorCode err4 = system.reactivateUser(input4);

                        System.out.println("Status: " + err4 + "\n");
                        break;
                    case "viewallusers":
                        // UAMS action (view all users)
                        Result<String> result5 = system.viewAllUsers();

                        System.out.println(result5.value);
                        System.out.println("Status: " + result5.errorCode + "\n");
                        break;
                    case "resetpassword":
                        // prompt enter username
                        System.out.print("Enter username: ");
                        String username6 = scanner.nextLine().trim();

                        // prompt enter password
                        Console console6 = System.console();
                        char[] passwordArray6 = console6.readPassword("Enter password: ");
                        String password6 = new String(passwordArray6);

                        // UAMS action (reset password)
                        ErrorCode err6 = system.resetPassword(username6, password6);

                        System.out.println("Status: " + err6 + "\n");
                        break;
                    case "logout":
                        system.logout();
                        stage2 = false;
                        break;
                    default:
                        System.out.println("You've entered an incorrect command! \nHere's a list of available commands: \n    createuser\n    deleteuser\n    suspenduser\n    reactivateuser\n    viewallusers\n    resetpassword\n    logout\n");
                }
            } while (stage2);
        }

        // scanner.close();
    }
}
