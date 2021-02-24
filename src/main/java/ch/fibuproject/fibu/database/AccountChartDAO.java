package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.AccountChart;
import ch.fibuproject.fibu.model.DBResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * DAO object for AccountChart
 */

public class AccountChartDAO {

    public AccountChartDAO() {
    }

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

    public DBResult saveAccountChart(AccountChart accountChart) {
        return this.updateStatement(accountChart, true);
    }

    public DBResult updateAccountChart(AccountChart accountChart) {
        return this.updateStatement(accountChart, false);
    }


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
