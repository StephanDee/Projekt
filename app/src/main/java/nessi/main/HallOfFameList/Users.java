package nessi.main.HallOfFameList;

public class Users {

    private String userId;
    private String userName;
    private String userPassword;
    private String userRank;

    public Users(String userId, String userName,String userPassword, String userRank) {

        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRank = userRank;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

}
