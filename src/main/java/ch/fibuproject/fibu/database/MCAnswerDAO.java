package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.MCAnswer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO class for MCAnswer
 *
 * @author Ciro Brodmann
 */

public class MCAnswerDAO {

    public static final int FILTERBYUSERID = 1;
    public static final int FILTERBYMCOPTIONID = 2;

    /**
     * default constructor
     */
    public MCAnswerDAO() {
    }

    /**
     * gets the requested MCAnswer from the database
     * @param subquestionID the ID of the associated subquestion
     * @param userID the Id of the associated user
     * @return the requested MCAnswer object or null if not found
     */
    public MCAnswer getMCAnswer(int subquestionID, int userID) {
        String query;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        MCAnswer mcAnswer = null;

        query = "SELECT mco.mcOptionID AS mcOptionID, mca.userID AS userID, mca.mcAnswerID AS mcAnswerID" +
                " FROM MCOption AS mco" +
                " LEFT JOIN MCAnswer AS mca" +
                " ON mco.mcOptionID = mca.mcOptionID" +
                " LEFT JOIN Subquestion AS sq" +
                " ON mco.subquestionID = sq.subquestionID" +
                " WHERE sq.subquestionID = ?" +
                " AND mca.userID = ?"; // Needs to be tested.

        values = new HashMap<>();

        values.put(1, subquestionID);
        values.put(2, userID);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                mcAnswer = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return mcAnswer;
    }

    /**
     * gets all MCAnswers from the database
     * @return vector containing all MCAnswers or an empty one if none are found
     */
    public Vector<MCAnswer> getAllMCAnswers() {
        return this.getAllMCAnswers(0, 0);
    }

    /**
     * gets all MCAnswers matching to the filter from the database
     * @param id the ID of either the associated user or MCOption, depending on filter
     * @param filter the filter to be used (0 = no filter, 1 = filter by userID, 2 = filter by mcOptionID)
     * @return vector containing all matching MCAnswers or an empty one if none are found
     */
    public Vector<MCAnswer> getAllMCAnswers(int id, int filter) {
        String query;
        Vector<MCAnswer> mcAnswers;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        MCAnswer mcAnswer;

        values = new HashMap<>();
        values.put(1, id);
        mcAnswers = new Vector<>();
        query = "SELECT * FROM MCAnswer";

        try {
            switch (filter) {
                case FILTERBYMCOPTIONID:
                    query = query + " WHERE mcOptionID = ?";
                    answer = Database.selectStatement(query, values);
                    break;
                case FILTERBYUSERID:
                    query = query + " WHERE userID = ?";
                    answer = Database.selectStatement(query, values);
                    break;
                default:
                    answer = Database.simpleSelect(query);
                    break;
            }

            results = answer.getResults();

            while (results.next()) {
                mcAnswer = this.setValues(results);

                mcAnswers.add(mcAnswer);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return mcAnswers;
    }

    /**
     * saves an MCAnswer to the Database, replaces it if one with the same ID already exists
     * @param mcAnswer the MCAnswer to be saved
     * @return the result of the query
     */
    public DBResult saveMCAnswer(MCAnswer mcAnswer) {
        String query, middlePart;
        int mcAnswerID = 0;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        middlePart = " SET mcOptionID = ?," +
                " userID = ?";

        query = "SELECT mcAnswerID" +
                " FROM MCAnswer" +
                " WHERE mcOptionID = ?" +
                " AND userID = ?";

        values = new HashMap<>();

        values.put(1, mcAnswer.getMcOptionID());
        values.put(2, mcAnswer.getUserID());

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                mcAnswerID = results.getInt("mcAnswerID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        values.clear();

        values.put(1, mcAnswer.getUserID());
        values.put(2, mcAnswer.getMcOptionID());

        if (mcAnswerID > 0) {
            query = "UPDATE MCAnswer" + middlePart +
                    " WHERE mcAnswerID = ?";
            values.put(5, mcAnswerID);
        } else {
            query = "INSERT INTO MCAnswer" + middlePart;
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
     * deletes an MCAnswer from the database
     * @param mcAnswerID the ID of the MCAnswer
     * @param userID the ID of the corresponding user
     * @return the result of the query
     */
    public DBResult deleteMCAnswer(int mcAnswerID, int userID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM MCAnswer" +
                " WHERE mcAnswerID = ?" +
                " AND userID = ?";

        values.put(1, mcAnswerID);
        values.put(2, userID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * inserts values from the ResultSet into the Java representation of a MCAnswer
     * @param results the ResultSet the values are stored in
     * @return the filled MCAnswer object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private MCAnswer setValues(ResultSet results) throws SQLException {
        MCAnswer mcAnswer;

        mcAnswer = new MCAnswer();

        mcAnswer.setId(results.getInt("mcAnswerID"));
        mcAnswer.setMcOptionID(results.getInt("mcOptionID"));
        mcAnswer.setUserID(results.getInt("userID"));

        return mcAnswer;
    }
}
