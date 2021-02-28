package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.Account;
import ch.fibuproject.fibu.model.DBResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO Class for Accounts
 *
 * @author Ciro Brodmann
 */

public class AccountDAO {

    /**
     * default constructor
     */
    public AccountDAO() {
    }

    /**
     * Gets the requested Account from the database
     * @param id the ID of the Account
     * @return the requested Account, null if not found
     */
    public Account getAccount(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        Account account = null;

        query = "SELECT * FROM Account" +
                " WHERE accountID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            answer = Database.selectStatement(query, values);
            results = answer.getResults();

            if (results.next()) {
                account = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return account;
    }

    /**
     * gets all accounts from the database
     * @return vector containing all accounts, empty vector if not found
     */
    public Vector<Account> getAllAccounts() {
        return this.getAllAccounts(0);
    }

    /**
     * gets all accounts belonging to a designated accountChart. If accountChartID < 0, it'll just get all available
     * accountCharts
     * @param accountChartID the id of the associated accountChart
     * @return vector containing Accounts, empty vector if not found
     */
    public Vector<Account> getAllAccounts(int accountChartID) {
        String query;
        Vector<Account> accounts;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        Account account;

        values = new HashMap<>();
        accounts = new Vector<>();
        query = "SELECT * FROM Account";

        try {
            if (accountChartID > 0) {
                query = "SELECT a.accountID AS accountID, a.accountNumber AS accountNumber," +
                        " a.accountName AS accountName" +
                        " FROM Account AS a" +
                        " LEFT JOIN Account_AccountChart AS aac" +
                        " ON a.accountID = aac.accountID" +
                        " WHERE aac.accountChartID = ?";
                values.put(1, accountChartID);
                answer = Database.selectStatement(query, values);
            } else {
                answer = Database.simpleSelect(query);
            }

            results = answer.getResults();

            while (results.next()) {
                account = this.setValues(results);

                accounts.add(account);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return accounts;
    }

    /**
     * saves an account to the database
     * @param account the account to be saveed
     * @return the result of the query
     */
    public DBResult saveAccount(Account account) {
        return this.updateStatement(account, true);
    }

    /**
     * saves changes made to an account to the database
     * @param account the account to be updated
     * @return the result of the query
     */
    public DBResult updateAccount(Account account) {
        return this.updateStatement(account, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param account the account to be saved / updated
     * @param isNew true if the Account is new (--> insert), otherwise false (--> update)
     * @return the result of the query
     */
    private DBResult updateStatement(Account account, boolean isNew) {
        String query, middlePart;
        Map<Integer, Object> values;
        middlePart = " SET accountNumber = ?," +
                " accountName = ?";

        values = new HashMap<>();

        values.put(1, account.getNumber());
        values.put(2, account.getName());

        if (isNew) {
            query = "INSERT INTO Account" + middlePart;
        } else {
            query = "UPDATE Account" + middlePart +
                    " WHERE accountID = ?";
            values.put(3, account.getId());
        }

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * deletes an account from the database
     * @param accountID the ID of the account which is supposed to get removed
     * @return the result of the query
     */
    public DBResult deleteAccount(int accountID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM Account" +
                " WHERE accountID = ?";

        values.put(1, accountID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * inserts values from the ResultSet into the Java representation of an account
     * @param results the ResultSet the values are stored in
     * @return the filled account object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private Account setValues(ResultSet results) throws SQLException {
        Account account;

        account = new Account();
        account.setId(results.getInt("accountID"));
        account.setNumber(results.getInt("accountNumber"));
        account.setName(results.getString("accountName"));

        return account;
    }
}
