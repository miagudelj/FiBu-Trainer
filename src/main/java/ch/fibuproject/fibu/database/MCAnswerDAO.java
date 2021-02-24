package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.MCAnswer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * DAO class for MCAnswer
 *
 * // DO NOT USE YET. THERE ARE THINGS THAT DON'T YET MAKE SENSE AND THAT I HAVE TO FIGURE OUT.
 * Right, so, at least the important one in getMCAnswer should be resolved. I can't guarantee yet, though, that it'll
 * work. If there's any issue, please don't hesitate to contact me.
 */

public class MCAnswerDAO {

    public static final int FILTERBYUSERID = 1;
    public static final int FILTERBYMCOPTIONID = 2;

    public MCAnswerDAO() {
    }

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

    public Vector<MCAnswer> getAllMCAnswers() {
        return this.getAllMCAnswers(0, 0);
    }


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

    private MCAnswer setValues(ResultSet results) throws SQLException {
        MCAnswer mcAnswer;

        mcAnswer = new MCAnswer();

        mcAnswer.setId(results.getInt("mcAnswerID"));
        mcAnswer.setMcOptionID(results.getInt("mcOptionID"));
        mcAnswer.setUserID(results.getInt("userID"));

        return mcAnswer;
    }
}
