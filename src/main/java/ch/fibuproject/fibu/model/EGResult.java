package ch.fibuproject.fibu.model;

/**
 * @author Ciro Brodmann
 *
 * This class represents a student's results of an exercise group.
 * It also saves his progress (how many questions he's answered.
 * This class's sole purpose is to not make the database retrieve all solutions just to find out how many questions
 * have been answered and how many of those have been correct.
 */

public class EGResult {
    private int id;
    private int userID;
    private int exerciseGroupID;
    private double solved;
    private double correct;

    public EGResult() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getExerciseGroupID() {
        return exerciseGroupID;
    }

    public void setExerciseGroupID(int exerciseGroupID) {
        this.exerciseGroupID = exerciseGroupID;
    }

    public double getSolved() {
        return solved;
    }

    public void setSolved(double solved) {
        this.solved = solved;
    }

    public double getCorrect() {
        return correct;
    }

    public void setCorrect(double correct) {
        this.correct = correct;
    }
}
