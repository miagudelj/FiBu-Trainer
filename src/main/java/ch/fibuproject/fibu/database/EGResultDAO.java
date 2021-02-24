package ch.fibuproject.fibu.database;


import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.EGResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * DAO for EGResult.
 */

public class EGResultDAO {

    public static final int FILTERBYUSERID = 1;
    public static final int FILTERBYEXERCISEGROUPID = 2;

    public EGResultDAO() {
    }

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

    public Vector<EGResult> getAllEGResults() {
        return this.getAllEGResults(0, 0);
    }


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
