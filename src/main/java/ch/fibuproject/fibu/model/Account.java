package ch.fibuproject.fibu.model;

/**
 * This class represents a bookkeeping account
 *
 * @author Ciro Brodmann
 */

// TODO create javadocs

public class Account {
    private int id;
    private int number;
    private String name;

    /**
     * default constructor
     */
    public Account() {
    }

    /**
     * sets the ID
     * @param id the id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the ID
     * @return value of ID
     */
    public int getId() {
        return id;
    }

    /**
     * gets the number
     * @return value of number
     */
    public int getNumber() {
        return number;
    }

    /**
     * sets the number
     * @param number the number to be set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * gets the name
     * @return the value of name
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
