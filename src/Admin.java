public class Admin extends User {

    public Admin(long userID, String username, String password, boolean isAdmin) {
        super(userID, username, password, isAdmin);
    }

    // Displays a list of all users and their status
    public String viewAllUsers() {
        if (!this.isAdmin)
            return null;
        String s = "";
        for (String username : usersList.keySet()) {
            User user = usersList.get(username);
            s += "=======================\n" + user.toString() + "\n";
        }
        s += "=======================";
        return s;
    }

    // Resets the password of a given user
    public void resetPassword(String username, String password) {
        if (!this.isAdmin)
            return;
        usersList.get(username).password = User.getSalt(username, password);
    }
}
