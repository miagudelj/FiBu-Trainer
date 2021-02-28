package ch.fibuproject.fibu.model;

/**
 * Represents a User.
 *
 * @author Ciro Brodmann
 *
 * Changed by Vinicius Navarra
 */

public class User {

    private int id;
    private String username;
    private String password;
    private String passwordHash;
    private UserType type;

    /**
     * default constructor
     */
    public User() {

    }

    /**
     * constructor which sets username and password
     * @param username the username to be set
     * @param password the password to be set
     */
    public User(String username, String password){
        this.username=username;
        this.password=password;
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

    /**¨
     * gets the username
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username
     * @param username the username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the passwordHash
     * @return value of passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * sets the passwordHash
     * @param passwordHash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * gets the UserType
     * @return value of type
     */
    public UserType getType() {
        return type;
    }

    /**
     * sets the UserType
     * @param type the type to be set
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * gets the password
     * @return value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password
     * @param password the password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
