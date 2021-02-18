package ch.fibuproject.fibu.model;

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
