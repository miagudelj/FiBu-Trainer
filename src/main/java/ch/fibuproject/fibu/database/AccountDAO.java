package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.Account;
import ch.fibuproject.fibu.model.DBResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 */

public class AccountDAO {

    public AccountDAO() {
    }

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

    public Vector<Account> getAllAccounts() {
        return this.getAllAccounts(0);
    }

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
                query = query + " WHERE accountChartID = ?";
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

    public DBResult saveAccount(Account account, int accountChartID) {
        return this.updateStatement(account, accountChartID, true);
    }

    public DBResult updateAccount(Account account, int accountChartID) {
        return this.updateStatement(account, accountChartID, false);
    }


    private DBResult updateStatement(Account account, int accountChartID, boolean isNew) {
        String query, middlePart;
        Map<Integer, Object> values;
        middlePart = " SET accountNumber = ?," +
                " accountName = ?," +
                " accountChartID = ?";

        values = new HashMap<>();

        values.put(1, account.getNumber());
        values.put(2, account.getName());
        values.put(3, accountChartID);

        if (isNew) {
            query = "INSERT INTO Account" + middlePart;
        } else {
            query = "UPDATE Account" + middlePart +
                    " WHERE accountID = ?";
            values.put(4, account.getId());
        }

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

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

    private Account setValues(ResultSet results) throws SQLException {
        Account account;

        account = new Account();
        account.setId(results.getInt("accountID"));
        account.setNumber(results.getInt("accountNumber"));
        account.setName(results.getString("accountName"));

        return account;
    }
}
