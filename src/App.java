import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        UAMS system = new UAMS();

        Scanner scanner = new Scanner(System.in);

        boolean stage1 = true;
        do {
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();
            switch (command.toLowerCase()) {
                case "login":
                    System.out.print("Enter a yout username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter a yout password: ");
                    String password = scanner.nextLine();
                    if (system.login(username, password))
                        stage1 = false;
                    else
                        System.out.print("You have not been logged in. ");
                    break;
                case "logout":
                    system.logout();
                    break;
                default:
                    System.out.println("You've entered an incorrect command! ");
            }
        } while (stage1);

        scanner.close();
    }
}
