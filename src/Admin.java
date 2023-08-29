public class Admin extends User {

    public Admin(long userID, String username, String password) {
        super(userID, username, password);
    }

    // Displays a list of all users and their status
    public Result<String> viewAllUsers() {
        if (!(this instanceof Admin))
            return new Result<String>(null, ErrorCode.INSUFFICIENT_PRIVILEGE);
        String s = "";
        for (String username : usersList.keySet()) {
            User user = usersList.get(username);
            s += "=======================\n" + user.toString() + "\n";
        }
        s += "=======================";
        return new Result<String>(s, ErrorCode.OK);
    }

    // Resets the password of a given user
    public ErrorCode resetPassword(String username, String password) {
        if (!usersList.containsKey(username.toLowerCase()))
            return ErrorCode.USER_NON_EXIST;
        if (usersList.get(username.toLowerCase()).accountStatus == AccountStatus.Deleted)
            return ErrorCode.ACCOUNT_DELETED;
        if (!(this instanceof Admin))
            return ErrorCode.INSUFFICIENT_PRIVILEGE;
        usersList.get(username).password = User.getSalt(username, password);
        return ErrorCode.OK;
    }
}
