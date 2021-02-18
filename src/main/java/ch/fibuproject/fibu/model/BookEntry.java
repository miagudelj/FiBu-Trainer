package ch.fibuproject.fibu.model;

/**
 * @author Ciro Brodmann
 *
 * Represents a book entry (DE: Buchungssatz) and who it belongs to
 */
public class BookEntry {

    private int id;
    private int accountSoll;
    private int accountHaben;
    private double amount;
    private int subquestionID;
    private int userID;

    public BookEntry() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountSoll() {
        return accountSoll;
    }

    public void setAccountSoll(int accountSoll) {
        this.accountSoll = accountSoll;
    }

    public int getAccountHaben() {
        return accountHaben;
    }

    public void setAccountHaben(int accountHaben) {
        this.accountHaben = accountHaben;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getSubquestionID() {
        return subquestionID;
    }

    public void setSubquestionID(int subquestionID) {
        this.subquestionID = subquestionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
