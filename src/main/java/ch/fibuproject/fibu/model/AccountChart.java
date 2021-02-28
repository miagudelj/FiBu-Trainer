package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * AccountChart represents a collection of accounts to use within one exercise group
 *
 * @author Ciro Brodmann
 */

public class AccountChart {
    private int id;
    private String name;
    private Vector<Account> allAccounts;

    /**
     * default constructor
     */
    public AccountChart() {
        allAccounts = new Vector<>();
    }

    /**
     * sets the id
     * @param id the id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the id
     * @return value of id
     */
    public int getId() {
        return id;
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

    /**
     * gets all accounts
     * @return Vector of all accounts
     */
    public Vector<Account> getAllAccounts() {
        return allAccounts;
    }

    /**
     * gets an account at the specified index
     * @param index the index of the requested account
     * @return account at [index]
     */
    public Account getAccount(int index) {
        return allAccounts.get(index);
    }

    /**
     * adds an account to the collection
     * @param account the account to be added
     */
    public void addAccount(Account account) {
        this.allAccounts.add(account);
    }

    /**
     * adds multiple accounts to the collection
     * @param accounts the vector of accounts to be added
     */
    public void addAccounts(Vector<Account> accounts) {
        this.allAccounts.addAll(accounts);
    }
}
