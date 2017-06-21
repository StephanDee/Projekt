package nessi.main.ExecutorList;

/**
 * This class represents the Challenge.
 *
 * @author Stephan D
 */
public class Executors {

    private String executorId;
    private String userId;
    private String executorName;
    private Integer executorPoints;

    public Executors() {
        // Empty Constructor for database value event listener
    }

    /**
     * Constructor implements the Executors.
     *
     * @param executorId     Executors Id
     * @param userId         user Id
     * @param executorName   Name
     * @param executorPoints Points
     */
    public Executors(String executorId, String userId, String executorName, Integer executorPoints) {
        this.executorId = executorId;
        this.userId = userId;
        this.executorName = executorName;
        this.executorPoints = executorPoints;
    }

    public String getExecutorId() {
        return executorId;
    }

    public String getUserId() {
        return userId;
    }

    public String getExecutorName() {
        return executorName;
    }

    public Integer getExecutorPoints() {
        return executorPoints;
    }

}
