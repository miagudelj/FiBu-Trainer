package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.MCQuestion;
import ch.fibuproject.fibu.model.SQType;
import ch.fibuproject.fibu.model.Subquestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * DAO for a Subquestion
 */

public class SubquestionDAO {

    public SubquestionDAO() {
    }

    public Subquestion getSubquestion(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results;
        DBQueryAnswer answer = null;
        Subquestion subquestion = null;

        query = "SELECT * FROM Subquestion" +
                " WHERE subquestionID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                subquestion = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return subquestion;
    }

    public Vector<Subquestion> getAllSubquestions() {
        return this.getAllSubquestions(0);
    }

    public Vector<Subquestion> getAllSubquestions(int questionID) {
        String query;
        Vector<Subquestion> subquestions;
        Map<Integer, Object> values;
        ResultSet results;
        DBQueryAnswer answer = null;
        Subquestion subquestion;

        values = new HashMap<>();
        subquestions = new Vector<>();
        query = "SELECT * FROM Subquestion";

        try {
            if (questionID > 0) {
                query = query + " WHERE questionID = ?";
                values.put(1, questionID);
                answer = Database.selectStatement(query, values);
            } else {
                answer = Database.simpleSelect(query);
            }

            results = answer.getResults();

            while (results.next()) {
                subquestion = this.setValues(results);

                subquestions.add(subquestion);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return subquestions;
    }

    public DBResult saveSubquestion(Subquestion subquestion, int questionID) {
        return this.updateStatement(subquestion, questionID, true);
    }

    public DBResult updateSubquestion(Subquestion subquestion, int questionID) {
        return this.updateStatement(subquestion, questionID, false);
    }


    private DBResult updateStatement(Subquestion subquestion, int questionID, boolean isNew) {
       String query, middlePart;
       Map<Integer, Object> values;
       middlePart = " SET sqText = ?," +
               " sqLetter = ?," +
               " sqType = ?," +
               " questionID = ?";

       values = new HashMap<>();

       values.put(1, subquestion.getText());
       values.put(2, subquestion.getLetter());
       values.put(3, subquestion.getType().toString());
       values.put(4, questionID);

       if (isNew) {
           query = "INSERT INTO Subquestion" + middlePart;
       } else {
           query = "UPDATE Subquestion" + middlePart +
                   " WHERE subquestionID = ?";
           values.put(5, subquestion.getId());
       }

       try {
           return Database.updateStatement(query, values);
       } catch (SQLException ex) {
           ex.printStackTrace();
           System.out.println(ex.getMessage());
           throw new RuntimeException();
       }
    }

    public DBResult deleteSubquestion(int subquestionID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM Subquestion" +
                " WHERE subquestionID = ?";

        values.put(1, subquestionID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    private Subquestion setValues(ResultSet results) throws SQLException {
        Subquestion subquestion;
        SQType type;
        MCOptionDAO mcoDAO;

        type = SQType.valueOf(results.getString("sqType"));

        switch (type) {
            case MULTIPLECHOICE:
                subquestion = new MCQuestion();
                break;
            default: // Default because otherwise I'd have to check every time if the object != null
                subquestion = new Subquestion();
                break;
        }

        subquestion.setId(results.getInt("subquestionID"));
        subquestion.setText(results.getString("sqText"));
        subquestion.setLetter(results.getString("sqLetter").charAt(0));
        subquestion.setType(SQType.valueOf(results.getString("sqType")));

        if (subquestion instanceof MCQuestion) {
            mcoDAO = new MCOptionDAO();

            ((MCQuestion) subquestion).addOptions(mcoDAO.getAllMCOptions(subquestion.getId()));
        }

        return subquestion;
    }
}
