package ch.fibuproject.fibu.model;

/**
 * A multiple-choice answer and who it belongs to.
 *
 * @author Ciro Brodmann
 */

public class MCAnswer {

    private int id;
    private int mcOptionID;
    private int userID;

    /**
     * default constructor
     */
    public MCAnswer() {

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
     * gets the ID of the associated MCOption
     * @return value of mcOptionID
     */
    public int getMcOptionID() {
        return mcOptionID;
    }

    /**
     * sets the ID of the associated MCOption
     * @param mcOptionID the ID to be set
     */
    public void setMcOptionID(int mcOptionID) {
        this.mcOptionID = mcOptionID;
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
     * @param userID the ID to be set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
