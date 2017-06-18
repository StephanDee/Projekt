package nessi.main.ChallengeList;

/**
 * This class represents the Challenge.
 *
 * @author Stephan D
 */
public class Challenge {

    private String challengeId;
    private String challengeTitle;
    private String challengeReward;
    private String challengeText;

    public Challenge() {

    }

    /**
     * Constructor implements the Challenge.
     *
     * @param challengeId Id
     * @param challengeTitle Title
     * @param challengeReward Reward in Points
     * @param challengeText Description
     */
    public Challenge(String challengeId, String challengeTitle, String challengeReward, String challengeText) {
        this.challengeId = challengeId;
        this.challengeTitle = challengeTitle;
        this.challengeReward = challengeReward;
        this.challengeText = challengeText;
    }

    public String getchallengeId() {
        return challengeId;
    }

    public String getchallengeTitle() {
        return challengeTitle;
    }

    public String getchallengeReward() {
        return challengeReward;
    }

    public String getchallengeText() {
        return challengeText;
    }
}
