package ch.fibuproject.fibu.database;


import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.EGResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO for EGResult.
 *
 * @author Ciro Brodmann
 */

public class EGResultDAO {

    public static final int FILTERBYUSERID = 1;
    public static final int FILTERBYEXERCISEGROUPID = 2;

    /**
     * default constructor
     */
    public EGResultDAO() {
    }

    /**
     * gets an EGResult from the database
     * @param exerciseGroupID the ID of the ExerciseGroup the EGResult is associated with
     * @param userID the ID of the user the EGResult is associated with
     * @return the corresponding EGResult, or null if not found
     */
    public EGResult getEGResult(int exerciseGroupID, int userID) {
        String query;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        EGResult egResult = null;

        query = "SELECT * FROM EGResult" +
                " WHERE exerciseGroupID = ?" +
                " AND userID = ?";

        values = new HashMap<>();

        values.put(1, exerciseGroupID);
        values.put(2, userID);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                egResult = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return egResult;
    }

    /**
     * gets all EGResults from the database
     * @return Vector containing all EGResults, or empty vector if none are found
     */
    public Vector<EGResult> getAllEGResults() {
        return this.getAllEGResults(0, 0);
    }

    /**
     * gets all EGResults which comply with the filter restrictions
     * @param id the ID of either a user or exercise group, depending on filter
     * @param filter the filter to be used (0 = no filter / all, 1 = filter by userID, 2 = filter by exerciseGroupID)
     * @return Vector with all matching EGResults, or all EGResults, depending on filter
     */
    public Vector<EGResult> getAllEGResults(int id, int filter) {
        String query;
        Vector<EGResult> egResults;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        EGResult egResult;

        values = new HashMap<>();
        values.put(1, id);
        egResults = new Vector<>();
        query = "SELECT * FROM EGResult";

        try {
            switch (filter) {
                case FILTERBYEXERCISEGROUPID:
                    query = query + " WHERE exerciseGroupID = ?";
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
                egResult = this.setValues(results);

                egResults.add(egResult);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return egResults;
    }

    /**
     * saves an EGResult to the database, replaces it if one with the same ID already exists
     * @param egResult the EGResult to be saved
     * @return the result of the query
     */
    public DBResult saveEGResult(EGResult egResult) {
        String query, middlePart;
        int egResultID = 0;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        middlePart = " SET userID = ?," +
                " exerciseGroupID = ?," +
                " solved = ?," +
                " correct = ?";

        query = "SELECT egResultID" +
                " FROM EGResult" +
                " WHERE exerciseGroupID = ?" +
                " AND userID = ?";

        values = new HashMap<>();

        values.put(1, egResult.getExerciseGroupID());
        values.put(2, egResult.getUserID());

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                egResultID = results.getInt("egResultID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        values.clear();

        values.put(1, egResult.getUserID());
        values.put(2, egResult.getExerciseGroupID());
        values.put(3, egResult.getSolved());
        values.put(4, egResult.getCorrect());

        if (egResultID > 0) {
            query = "UPDATE EGResult" + middlePart +
                    " WHERE egResultID = ?";
            values.put(5, egResultID);
        } else {
            query = "INSERT INTO EGResult" + middlePart;
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
     * deletes an EGResult from the database
     * @param exerciseGroupID the ID of the associated ExerciseGroup
     * @param userID the ID of the associated user
     * @return the result of the query
     */
    public DBResult deleteEGResult(int exerciseGroupID, int userID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM EGResult" +
                " WHERE exerciseGroupID = ?" +
                " AND userID = ?";

        values.put(1, exerciseGroupID);
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
     * inserts values from the ResultSet into the Java representation of a BookEntry
     * @param results the ResultSet the values are stored in
     * @return the filled EGResult object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private EGResult setValues(ResultSet results) throws SQLException {
        EGResult egResult;

        egResult = new EGResult();

        egResult.setId(results.getInt("egResultID"));
        egResult.setUserID(results.getInt("userID"));
        egResult.setExerciseGroupID(results.getInt("exerciseGroupID"));
        egResult.setSolved(results.getBigDecimal("solved").doubleValue());
        egResult.setCorrect(results.getBigDecimal("correct").doubleValue());

        return egResult;
    }
}
