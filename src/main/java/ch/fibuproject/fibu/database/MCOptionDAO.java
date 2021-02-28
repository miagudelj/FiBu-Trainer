package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.MCOption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Data Access object for MCOption
 *
 * @author Ciro Brodmann
 */
public class MCOptionDAO {

    /**
     * default constructor
     */
    public MCOptionDAO() {

    }

    /**
     * gets the matching MCOption from the database
     * @param id the ID the MCOption is identified by
     * @return the matching MCOption or null if it isn't is found
     */
    public MCOption getMCOption(int id) {
        String query;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        MCOption option = null;
        Map<Integer, Object> values;

        query = "SELECT * FROM MCOption" +
                " WHERE mcOptionID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                option = new MCOption();

                this.setValues(option, results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return option;
    }

    /**
     * gets all MCOptions from the database
     * @return vector containing all MCOptions or an empty vector if none are found
     */
    public Vector<MCOption> getAllMCOptions() {
        return this.getAllMCOptions(0);
    }

    /**
     * gets all MCOptions associated with Subquestion from the database or all if ID < 1
     * @param subquestionID the ID the associated Subquestion is identified by
     * @return vector containing all matching MCOptions or an empty one if none are found
     */
    public Vector<MCOption> getAllMCOptions (int subquestionID) {
        String query;
        Vector<MCOption> options;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        MCOption option;

        query = "SELECT * FROM MCOption";
        values = new HashMap<>();
        options = new Vector<>();

        try {

            if (subquestionID > 0) {
                query = query + " WHERE subquestionID = ?";
                values.put(1, subquestionID);
                answer = Database.selectStatement(query, values);
            } else {
                answer = Database.simpleSelect(query);
            }

            results = answer.getResults();

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
            Database.closeStatement(answer);
        }

        return options;

    }

    /**
     * saves ab MCOption to the database
     * @param option the option to be saved
     * @param subquestionID the ID of the associated Subquestion
     * @return the results of the query
     */
    public DBResult saveMCOption(MCOption option, int subquestionID) {
        return this.updateStatement(option, subquestionID, true);
    }

    /**
     * saves changes made to an MCOption to the database
     * @param option the option to be updated
     * @param subquestionID the Id of the associated Subquestion
     * @return the results of the query
     */
    public DBResult updateMCOption(MCOption option, int subquestionID) {
        return this.updateStatement(option, subquestionID, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param option the MCOption to be saved / updated
     * @param subquestionID the ID of the associated Subquestion
     * @param isNew true if the MCOption is new (--> insert), otherwise false (--> update)
     * @return the results of the query
     */
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
        }
    }

    /**
     * deletes an MCOption from the database
     * @param mcoID the ID the MCOption is identified by
     * @return the results of the query
     */
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
        }
    }

    /**
     * inserts values from the ResultSet into the Java representation of an ExerciseGroup
     * @param option the MCOption the values are supposed to be inserted into
     * @param results the ResultSet the values are stored in
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private void setValues(MCOption option, ResultSet results) throws SQLException {
        option.setId(results.getInt("mcOptionID"));
        option.setName(results.getString("mcoName"));
    }
}
