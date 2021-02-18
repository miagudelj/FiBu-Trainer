package ch.fibuproject.fibu.model;

/**
 * @author Ciro Brodmann
 *
 * A multiple-choice answer and who it belongs to.
 */

public class MCAnswer {

    private int id;
    private int mcOptionID;
    private int userID;

    public MCAnswer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMcOptionID() {
        return mcOptionID;
    }

    public void setMcOptionID(int mcOptionID) {
        this.mcOptionID = mcOptionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
