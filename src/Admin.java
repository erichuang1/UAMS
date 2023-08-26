public class Admin extends User {

    public Admin(String userID, String username, String password, String email, String accountStatus,
    boolean loginStatus, boolean isAdmin) {
        createUser(userID, username, password, email, accountStatus, loginStatus, isAdmin);
    }
    
    public void viewAllUsers() { // Displays a list of all users and their status.
    }

    public void resetPassword() { // Resets the password of a given user.
    }
}
