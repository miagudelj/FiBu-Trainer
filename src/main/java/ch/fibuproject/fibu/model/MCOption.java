package ch.fibuproject.fibu.model;

/**
 * A multiple-choice option
 *
 * @author Ciro Brodmann
 */

public class MCOption {

    private int id;
    private String name;

    /**
     * default constructor
     */
    public MCOption() {

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
     * gets the name
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }
}
