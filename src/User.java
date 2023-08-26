import java.util.HashMap;

// I assumed all the methods under the user class is executable by them.

// Though in common sense, a user shouldn't be able to delete, suspend or
// reactivate another user, or even an Admin.

// If I'd implement this in the way I've talked about, I might move all
// sensetive actions to the admin class, to prevent any unauthorized
// action/executions by user itself.
public class User {
    public User(long userID, String username, String password,
            boolean isAdmin) {
        setAccountInfo(userID, username, password, "", AccountStatus.Active, isAdmin);
    }

    // Calculate salted password hash
    public static String getSalt(String username, String password) {
        int salt = (username.toLowerCase() + password).hashCode();
        return Integer.toString(salt);
    }

    public long userID;
    public String username, password, email;
    public AccountStatus accountStatus;
    public boolean isAdmin;
    public HashMap<String, User> usersList;

    public void setAccountInfo(long userID, String username, String password, String email, AccountStatus accountStatus,
            boolean isAdmin) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountStatus = accountStatus;
        this.isAdmin = isAdmin;
    }

    // Adds a new user
    public void createUser(String username, String password, boolean isAdmin) {
        if (!this.isAdmin)
            isAdmin = false;
        String salt = User.getSalt(username, password);
        User user = new User(usersList.size(), username, salt, isAdmin);
        usersList.put(user.username, user);
    }

    // Removes an existing user
    public void deleteUser(String username) {
        setAccountStatus(username, AccountStatus.Deleted);
    }

    // Temporarily disables a user from logging in
    public void suspendUser(String username) {
        setAccountStatus(username, AccountStatus.Suspended);
    }

    // Re-activates a previously suspended user
    public void reactivateUser(String username) {
        setAccountStatus(username, AccountStatus.Active);
    }

    // Helper method to modify account status
    public void setAccountStatus(String username, AccountStatus accountStatus) {
        if (!this.isAdmin)
            return;
        usersList.get(username).accountStatus = accountStatus;
    }

    // Output user profile as printable string
    public String toString() {
        return "userID: " + userID + "\n"
                + "username: " + username + "\n"
                + "password: " + password + "\n"
                + "email: " + email + "\n"
                + "accountStatus: " + accountStatus + "\n"
                + "isAdmin: " + isAdmin;
    }
}
