import java.util.HashMap;

// UAMS, or so called the "System" class, named so since Java has a built-in class with the same name, is based on the concept of Windows account management system. 
public class UAMS {
    public UAMS() {
        String salt = User.getSalt("admin", "admin");
        User user = new Admin(0, "admin", salt, true);
        usersList.put(user.username, user);
    }

    public String currentUsername;

    private HashMap<String, User> usersList = new HashMap<>();
    private User currentUser;

    // Private method for actually making the user privileged
    private void setCurrentUser(User user) {
        currentUser = user;
        if (user == null)
            currentUsername = "";
        else {
            // access to this.usersList makes the user privileged
            user.usersList = this.usersList;
            currentUsername = user.username;
        }
    }

    // Allows a user (or admin) to log in using their credentials.
    public ErrorCode login(String username, String password) {
        User user = usersList.get(username.toLowerCase());
        if (user == null)
            return ErrorCode.USER_NON_EXIST;

        switch (user.accountStatus) {
            case Active:
                // using hash+salt can make credentials safer
                if (user.password.equals(User.getSalt(username, password))) {
                    setCurrentUser(user);
                    return ErrorCode.LOGIN_SUCCESSFUL;
                } else
                    return ErrorCode.PASS_INCORRECT;
            case Suspended:
                return ErrorCode.ACCOUNT_SUSPENDED;
            case Deleted:
                return ErrorCode.ACCOUNT_DELETED;
            default:
                return ErrorCode.UNKNOWN_REASON;
        }
    }

    // Allows a logged-in user (or admin) to log out.
    public void logout() {
        currentUser.usersList = null;
        setCurrentUser(null);
    }

    // Passthrough methods
    public void createUser(String username, String password, boolean isAdmin) {
        currentUser.createUser(username, password, isAdmin);
    }

    public void deleteUser(String username) {
        currentUser.deleteUser(username);
    }

    public void suspendUser(String username) {
        currentUser.suspendUser(username);
    }

    public void reactivateUser(String username) {
        currentUser.reactivateUser(username);
    }

    public String viewAllUsers() {
        if (currentUser instanceof Admin)
            return ((Admin) currentUser).viewAllUsers();
        return null;
    }

    public void resetPassword(String username, String password) {
        if (currentUser instanceof Admin)
            ((Admin) currentUser).resetPassword(username, password);
    }

    public void setAccountStatus(String username, AccountStatus accountStatus) {
        currentUser.setAccountStatus(username, accountStatus);
    }

}
