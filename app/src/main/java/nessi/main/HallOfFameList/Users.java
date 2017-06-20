package nessi.main.HallOfFameList;

public class Users {

    private String userId;
    private String userEmail;
    private String userName;
    private String userPassword;
    private Integer userPoints;
    private String userRank;

    public Users(){
        // Empty Constructor for database value event listener
    }

    /**
     * Constructor implements the User.
     *
     * @param userId Id
     * @param userEmail Email
     * @param userName Name
     * @param userPassword Password
     *                     @param userPoints Points, Rewards will be added here after successful challenge
     * @param userRank Rank
     */
    public Users(String userId, String userEmail, String userName,String userPassword, Integer userPoints, String userRank) {

        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPoints = userPoints;
        this.userRank = userRank;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() { return userEmail; }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Integer getUserPoints() { return userPoints; }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

}
