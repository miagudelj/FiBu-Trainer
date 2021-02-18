package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * AccountChart represents a collection of accounts to use within one exercise group
 */
public class AccountChart {
    private int id;
    private Vector<Account> allAccounts;

    public AccountChart() {
        allAccounts = new Vector<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Vector<Account> getAllAccounts() {
        return allAccounts;
    }

    public Account getAccount(int index) {
        return allAccounts.get(index);
    }

    public void addAccount(Account account) {
        this.allAccounts.add(account);
    }
}
