import java.util.HashMap;

// Or so called the "System" class, 
// since Java has a built-in class with the same name
public class UAMS {
    private HashMap<String, User> usersList = new HashMap<>();

    public UAMS() {
        Admin admin = new Admin("0", "admin", User.getSalt("admin", "admin"), "", "Active", false, true);
        usersList.put("admin", admin);
    }

    public boolean login(String username, String password) { // Allows a user (or admin) to log in using their
                                                             // credentials.
        User user = usersList.get(username.toLowerCase());
        // using hash+salt can make credentials safer
        return user.login(User.getSalt(username, password));
    }

    public void logout() { // Allows a logged-in user (or admin) to log out.
    }
}
