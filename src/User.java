
public class User {
    private String userID, username, password, email, accountStatus;
    private boolean loginStatus, isAdmin;
    // I assumed all the methods under the user class is executable by them.

    // Though in common sense, a user shouldn't be able to delete, suspend or
    // reactivate another user, or even an Admin.

    // If I'd implement this in the way I've talked about, I might move all
    // sensetive actions to the admin class, to prevent any unauthorized
    // action/executions by user itself.

    public User(String userID, String username, String password, String email, String accountStatus,
            boolean loginStatus, boolean isAdmin) {
        createUser(userID, username, password, email, accountStatus, loginStatus, isAdmin);
    }

    // Allows a user (or admin) to log in using their credentials.
    public boolean login(String salt) {
        loginStatus = (this.password.equals(salt)) && (this.accountStatus.equals("Active"));
        return loginStatus;
    }

    // Adds a new user.
    public void createUser(String userID, String username, String password, String email, String accountStatus,
            boolean loginStatus, boolean isAdmin) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountStatus = accountStatus;
        this.loginStatus = loginStatus;
        this.isAdmin = isAdmin;
    }

    // Removes an existing user.
    public void deleteUser() {
        this.accountStatus = "Deleted";
    }

    // Temporarily disables a user from logging in.
    public void suspendUser() {
        this.accountStatus = "Suspended";
    }

    // Re-activates a previously suspended user.
    public void reactivateUser() {
        this.accountStatus = "Active";
    }

    // Generate salted password hash 
    public static String getSalt(String username, String password) {
        // using hash+salt can make credentials safer
        int salt = (username.toLowerCase() + password).hashCode();
        return Integer.toString(salt);
    }
}
