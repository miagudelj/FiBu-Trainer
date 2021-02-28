package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.AccountChart;
import ch.fibuproject.fibu.model.DBResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO object for AccountChart
 *
 * @author Ciro Brodmann
 */

public class AccountChartDAO {

    /**
     * default constructor
     */
    public AccountChartDAO() {
    }

    /**
     * Gets an accountChart and its containing accounts from the database
     * @param id the ID of the accountChart
     * @return the requested AccountChart, null if not found
     */
    public AccountChart getAccountChart(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        AccountChart accountChart = null;

        query = "SELECT * FROM AccountChart" +
                " WHERE accountChartID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            answer = Database.selectStatement(query, values);
            
            results = answer.getResults();

            if (results.next()) {
                accountChart = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return accountChart;
    }

    /**
     * retrieves every single AccountChart from the Database
     * @return a Vector containing all AccountCharts, an empty vector if nothing is found
     */
    public Vector<AccountChart> getAllAccountCharts() {
        String query;
        Vector<AccountChart> accountCharts;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        AccountChart accountChart;

        values = new HashMap<>();
        accountCharts = new Vector<>();
        query = "SELECT * FROM AccountChart";

        try {
            answer = Database.simpleSelect(query);
            
            results = answer.getResults();

            while (results.next()) {
                accountChart = this.setValues(results);

                accountCharts.add(accountChart);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return accountCharts;
    }

    /**
     * saves an AccountChart to the database
     * @param accountChart the accountchart to be saved
     * @return the result of the query
     */
    public DBResult saveAccountChart(AccountChart accountChart) {
        return this.updateStatement(accountChart, true);
    }

    /**
     * updates an existing AccountChart in the database
     * @param accountChart the AccountChart to be updated
     * @return the result of the query
     */
    public DBResult updateAccountChart(AccountChart accountChart) {
        return this.updateStatement(accountChart, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param accountChart the AccountChart to be saved/updated
     * @param isNew true if the AccountChart is new (--> insert), otherwise false (--> update)
     * @return the result of the query
     */
    private DBResult updateStatement(AccountChart accountChart, boolean isNew) {
        String query, middlePart;
        Map<Integer, Object> values;
        middlePart = " SET acName = ?";

        values = new HashMap<>();

        values.put(1, accountChart.getName());

        if (isNew) {
            query = "INSERT INTO AccountChart" + middlePart;
        } else {
            query = "UPDATE AccountChart" + middlePart +
                    " WHERE accountChartID = ?";
            values.put(2, accountChart.getId());
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
     * adds an account to an AccountChart in the Database
     * @param accountID the ID of the account to be added
     * @param accountChartID the ID of the AccountChart the account is supposed to be added to
     * @return the result of the query
     */
    public DBResult addAccount(int accountID, int accountChartID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "INSERT INTO Account_AccountChart" +
                " SET accountID = ?," +
                " accountChartID = ?";

        values.put(1, accountID);
        values.put(2, accountChartID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * removes an account from an accountChart
     * @param accountID the ID of the account to be removed
     * @param accountChartID the ID of the AccountChart the account is supposed to be removed from
     * @return the result of the query
     */
    public DBResult removeAccount(int accountID, int accountChartID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM Account_AccountChart" +
                " WHERE accountID = ?" +
                " AND accountChartID = ?";

        values.put(1, accountID);
        values.put(2, accountChartID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * deletes an AccountChart from the Database
     * @param accountChartID the ID of the AccountChart which should be removed
     * @return the result of the query
     */
    public DBResult deleteAccountChart(int accountChartID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM AccountChart" +
                " WHERE accountChartID = ?";

        values.put(1, accountChartID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * inserts values from a resultSet into the Java object and gets all accounts belonging to the requested
     * AccountChart from the database
     * @param results the ResultSet where the values are stored in
     * @return the Java AccountChart object with the values
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private AccountChart setValues(ResultSet results) throws SQLException {
        AccountChart accountChart;
        AccountDAO accountDAO
                ;
        accountDAO = new AccountDAO();
        accountChart = new AccountChart();

        accountChart.setId(results.getInt("accountChartID"));
        accountChart.setName(results.getString("acName"));

        accountChart.addAccounts(accountDAO.getAllAccounts(accountChart.getId()));

        return accountChart;
    }
}
