package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.MCOption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * Data Access object for MCOption
 */
public class MCOptionDAO {

    public MCOptionDAO() {

    }

    public MCOption getMCOption(int id) {
        String query;
        ResultSet results;
        MCOption option = null;
        Map<Integer, Object> values;

        query = "SELECT * FROM MCOption" +
                " WHERE mcOptionID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            results = Database.selectStatement(query, values);

            if (results.next()) {
                option = new MCOption();

                this.setValues(option, results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return option;
    }

    public Vector<MCOption> getAllMCOptions() {
        return this.getAllMCOptions(0);
    }

    public Vector<MCOption> getAllMCOptions (int subquestionID) {
        String query;
        Vector<MCOption> options;
        Map<Integer, Object> values;
        ResultSet results;
        MCOption option;

        query = "SELECT * FROM MCOption";
        values = new HashMap<>();
        options = new Vector<>();

        try {

            if (subquestionID > 0) {
                query = query + " WHERE subquestionID = ?";
                values.put(1, subquestionID);
                results = Database.selectStatement(query, values);
            } else {
                results = Database.simpleSelect(query);
            }

            while (results.next()) {
                option = new MCOption();

                this.setValues(option, results);

                options.add(option);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return options;

    }

    public DBResult saveMCOption(MCOption option, int subquestionID) {
        return this.updateStatement(option, subquestionID, true);
    }

    public DBResult updateMCOption(MCOption option, int subquestionID) {
        return this.updateStatement(option, subquestionID, false);
    }

    private DBResult updateStatement(MCOption option, int subquestionID, boolean isNew) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        values.put(1, option.getName());
        values.put(2, subquestionID);

        if (isNew) {
            query = "INSERT INTO MCOption" +
                    " SET mcoName = ?," +
                    " subquestionID = ?";
        } else {
            query = "UPDATE MCOption" +
                    " SET mcoName = ?," +
                    " subquestionID = ?" +
                    " WHERE mcOptionID = ?";

            values.put(3, option.getId());
        }

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }
    }

    public DBResult deleteMCOption(int mcoID) {
        String query;
        Map<Integer, Object> values;

        query = "DELETE FROM MCOption" +
                " WHERE mcOptionID = ?";

        values = new HashMap<>();

        values.put(1, mcoID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }
    }

    private void setValues(MCOption option, ResultSet results) throws SQLException {
        option.setId(results.getInt("mcOptionID"));
        option.setName(results.getString("mcoName"));
    }
}
