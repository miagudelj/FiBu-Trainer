package ch.fibuproject.fibu.model;

/**
 * Represents a book entry (DE: Buchungssatz) and who it belongs to
 *
 * @author Ciro Brodmann
 */
public class BookEntry {

    private int id;
    private int accountSoll;
    private int accountHaben;
    private double amount;
    private int subquestionID;
    private int userID;

    /**
     * default constructor
     */
    public BookEntry() {

    }

    /**
     * gets the id
     * @return value of id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id
     * @param id the id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the soll-account
     * @return value of accountSoll
     */
    public int getAccountSoll() {
        return accountSoll;
    }

    /**
     * sets the soll-account
     * @param accountSoll the account to be set
     */
    public void setAccountSoll(int accountSoll) {
        this.accountSoll = accountSoll;
    }

    /**
     * gets the haben-account
     * @return value of accountHaben
     */
    public int getAccountHaben() {
        return accountHaben;
    }

    /**
     * sets the haben-account
     * @param accountHaben the account to be set
     */
    public void setAccountHaben(int accountHaben) {
        this.accountHaben = accountHaben;
    }

    /**
     * gets the amount
     * @return value of amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * sets the amount
     * @param amount the amount to be set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * gets the ID of the associated account
     * @return value of subquestionID
     */
    public int getSubquestionID() {
        return subquestionID;
    }

    /**
     * sets the ID of the associated account
     * @param subquestionID the ID to be set
     */
    public void setSubquestionID(int subquestionID) {
        this.subquestionID = subquestionID;
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
     * @param userID ID to be set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
}
