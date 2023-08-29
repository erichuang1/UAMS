import java.util.HashMap;

// I assumed admin check is needed when executing certain methods under the user class.
public class User {
    public User(long userID, String username, String password) {
        setAccountInfo(userID, username, password, "", AccountStatus.Active);
    }

    // Calculate salted password hash
    public static String getSalt(String username, String password) {
        int salt = (username.toLowerCase() + password).hashCode();
        return Integer.toString(salt);
    }

    // The attributes of the user class is public for more efficient development
    // of the system. However it's crutial that the UAMS does not directly accept
    // or provide access to any user object.
    public long userID;
    public String username, password, email;
    public AccountStatus accountStatus;
    public HashMap<String, User> usersList;

    // Output user profile as printable string
    public String toString() {
        return "userID: " + userID + "\n"
                + "username: " + username + "\n"
                // + "password: " + password + "\n"
                // + "email: " + email + "\n"
                + "userGroup: " + this.getClass().getName() + "\n"
                + "accountStatus: " + accountStatus;
    }

    public void setAccountInfo(long userID, String username, String password, String email, AccountStatus accountStatus) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    // Helper method for modifying account status
    public ErrorCode setAccountStatus(String username, AccountStatus accountStatus) {
        if (!usersList.containsKey(username.toLowerCase()))
            return ErrorCode.USER_NON_EXIST;
        if (usersList.get(username.toLowerCase()).accountStatus == AccountStatus.Deleted)
            return ErrorCode.ACCOUNT_DELETED;
        usersList.get(username.toLowerCase()).accountStatus = accountStatus;
        return ErrorCode.OK;
    }

    // Adds a new user
    public ErrorCode createUser(String username, String password, boolean isAdmin) {
        if (usersList.containsKey(username.toLowerCase()))
            return ErrorCode.USER_ALREADY_EXISTS;
        // non-admin users can only create non-admin users
        if (!(this instanceof Admin))
            isAdmin = false;
        String salt = User.getSalt(username.toLowerCase(), password);
        User user;
        if (isAdmin)
            user = new Admin(usersList.size(), username, salt);
        else
            user = new User(usersList.size(), username, salt);

        usersList.put(user.username.toLowerCase(), user);
        return ErrorCode.OK;
    }

    // Removes an existing user
    /* A user cannot delete another user's account, BUT can delete itself */
    public ErrorCode deleteUser(String username) {
        if (this instanceof Admin)
            return setAccountStatus(username, AccountStatus.Deleted);
        else if (username.toLowerCase().equals(this.username.toLowerCase()))
            return setAccountStatus(this.username, AccountStatus.Deleted);
        return ErrorCode.INSUFFICIENT_PRIVILEGE;
    }

    // Temporarily disables a user from logging in
    /* Only an admin can suspend any user's account */
    public ErrorCode suspendUser(String username) {
        if (!(this instanceof Admin))
            return ErrorCode.INSUFFICIENT_PRIVILEGE;
        return setAccountStatus(username, AccountStatus.Suspended);
    }

    // Re-activates a previously suspended user
    /*
     * Only an admin can suspend any user's account, AND an account that's deleted
     * cannot be reactivated
     */
    public ErrorCode reactivateUser(String username) {
        if (!(this instanceof Admin))
            return ErrorCode.INSUFFICIENT_PRIVILEGE;
        return setAccountStatus(username, AccountStatus.Active);
    }
}
