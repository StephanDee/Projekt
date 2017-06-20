package nessi.main.ChallengeList;

/**
 * This class represents the Challenge.
 *
 * @author Stephan D
 */
public class Challenge {

    private String challengeId;
    private String userId;
    private String challengeCreator;
    private String challengeTitle;
    private Integer challengeReward;
    private String challengeText;

    public Challenge() {
        // Empty Constructor for database value event listener
    }

    /**
     * Constructor implements the Challenge.
     *
     * @param challengeId      Id
     * @param userId           user id
     * @param challengeCreator Creator
     * @param challengeTitle   Title
     * @param challengeReward  Reward in Points
     * @param challengeText    Description
     */
    public Challenge(String challengeId, String userId, String challengeCreator, String challengeTitle, Integer challengeReward, String challengeText) {
        this.challengeId = challengeId;
        this.userId = userId;
        this.challengeCreator = challengeCreator;
        this.challengeTitle = challengeTitle;
        this.challengeReward = challengeReward;
        this.challengeText = challengeText;
    }

    public String getchallengeId() {
        return challengeId;
    }

    public String getuserId() {
        return userId;
    }

    public String getchallengeCreator() {
        return challengeCreator;
    }

    public String getchallengeTitle() {
        return challengeTitle;
    }

    public Integer getchallengeReward() {
        return challengeReward;
    }

    public String getchallengeText() {
        return challengeText;
    }
}
