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
 * DAO for a Subquestion
 *
 * @author Ciro Brodmann
 */

public class SubquestionDAO {

    /**
     * default constructor
     */
    public SubquestionDAO() {
    }

    /**
     * gets the requested subquestion from the database
     * @param id the ID the Subquestion is identified by
     * @return the requested Subquestion or null if not found
     */
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

    /**
     * gets all Subquestions
     * @return vector containing all subquestions or an empty one if none are found
     */
    public Vector<Subquestion> getAllSubquestions() {
        return this.getAllSubquestions(0);
    }

    /**
     * gets all Subquestions associated with a Question
     * @param questionID the ID of the associated Question
     * @return vector with all matching subquestions or an empty one if none are found
     */
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

    /**
     * saves a subquestion to the database
     * @param subquestion the subquestion to be saved
     * @param questionID the ID of the associated Question
     * @return the result of the query
     */
    public DBResult saveSubquestion(Subquestion subquestion, int questionID) {
        return this.updateStatement(subquestion, questionID, true);
    }

    /**
     * saves changes made to a subquestion to the database
     * @param subquestion the subquestion to be updated
     * @param questionID the ID of the associated Question
     * @return the result of the query
     */
    public DBResult updateSubquestion(Subquestion subquestion, int questionID) {
        return this.updateStatement(subquestion, questionID, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param subquestion the Subquestion to be saved / updated
     * @param questionID the ID of the associated Question
     * @param isNew true if the Subquestion is new (--> insert), otherwise false (--> update)
     * @return the results of the query
     */
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

    /**
     * deletes a subquestion from the database
     * @param subquestionID the ID the subquestion is identified by
     * @return the results of the query
     */
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

    /**
     * inserts values from the ResultSet into the Java representation of a Subquestion and gets all associated
     * MCOptions from the database. Creates an MCQuestion if the type suggests that it should.
     * @param results the ResultSet the values are stored in
     * @return the filled Subquestion object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
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
