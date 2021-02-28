package ch.fibuproject.fibu.model;

/**
 * This class represents a student's results of an exercise group.
 * It also saves his progress (how many questions he's answered.
 * This class's sole purpose is to not make the database retrieve all solutions just to find out how many questions
 * have been answered and how many of those have been correct.
 *
 * @author Ciro Brodmann
 */
public class EGResult {
    private int id;
    private int userID;
    private int exerciseGroupID;
    private double solved;
    private double correct;

    /**
     * default constructor
     */
    public EGResult() {

    }

    /**
     * gets the ID
     * @return value of ID
     */
    public int getId() {
        return id;
    }

    /**
     * sets the ID
     * @param id the ID to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the ID of the associated user
     * @return value of userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * sets the ID of the associated user
     * @param userID the userID to be set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * gets the ID of the associated exerciseGroup
     * @return value of exerciseGroupID
     */
    public int getExerciseGroupID() {
        return exerciseGroupID;
    }

    /**
     * sets the ID of the associated exerciseGroup
     * @param exerciseGroupID the exerciseGroupID to be set
     */
    public void setExerciseGroupID(int exerciseGroupID) {
        this.exerciseGroupID = exerciseGroupID;
    }

    /**
     * gets the amount of solved questions
     * @return value of solved
     */
    public double getSolved() {
        return solved;
    }

    /**
     * sets the amount of solved questions
     * @param solved the amount to be set
     */
    public void setSolved(double solved) {
        this.solved = solved;
    }

    /**
     * gets the amount of correct questions
     * @return value of correct
     */
    public double getCorrect() {
        return correct;
    }

    /**
     * sets the amount of correct questions
     * @param correct the amount to be set
     */
    public void setCorrect(double correct) {
        this.correct = correct;
    }
}
