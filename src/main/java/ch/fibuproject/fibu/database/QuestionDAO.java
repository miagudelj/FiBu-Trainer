package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO for a Question.
 *
 * @author Ciro Brodmann
 */

public class QuestionDAO {

    /**
     * default constructor
     */
    public QuestionDAO() {
    }

    /**
     * gets the requested question from the database
     * @param id the ID the question is identified by
     * @return the requested Question or null if not found
     */
    public Question getQuestion(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        Question question = null;

        query = "SELECT * FROM Question" +
                " WHERE questionID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                question = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return question;
    }

    /**
     * gets all questions from the database
     * @return vector containing all questions or an empty one if none are found
     */
    public Vector<Question> getAllQuestions() {
        return this.getAllQuestions(0);
    }

    /**
     * get all Questions associated with the designated ExerciseGroup (or all if exerciseGroupID < 1)
     * @param exerciseGroupID the ID of the associated ExerciseGroup
     * @return vector containing all questions or an empty one if none are found
     */
    public Vector<Question> getAllQuestions(int exerciseGroupID) {
        String query;
        Vector<Question> questions;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        Question question;

        values = new HashMap<>();
        questions = new Vector<>();
        query = "SELECT * FROM Question";

        try {
            if (exerciseGroupID > 0) {
                query = query + " WHERE exerciseGroupID = ?";
                values.put(1, exerciseGroupID);
                answer = Database.selectStatement(query, values);
            } else {
                answer = Database.simpleSelect(query);
            }

            results = answer.getResults();

            while (results.next()) {
                question = this.setValues(results);

                questions.add(question);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return questions;
    }

    /**
     * saves a question to the database
     * @param question the question to be saved
     * @param exerciseGroupID the ID of the associated ExerciseGroup
     * @return the result of the query
     */
    public DBResult saveQuestion(Question question, int exerciseGroupID) {
        return this.updateStatement(question, exerciseGroupID, true);
    }

    /**
     * saves changes made to a question to the database
     * @param question the question to be updated
     * @param exerciseGroupID the ID of the associated ExerciseGroup
     * @return the result of the query
     */
    public DBResult updateQuestion(Question question, int exerciseGroupID) {
        return this.updateStatement(question, exerciseGroupID, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param question the Question to be saved / updated
     * @param exerciseGroupID the ID of the associated ExerciseGroup
     * @param isNew true if the Question is new (--> insert), otherwise false (--> update)
     * @return the results of the query
     */
    private DBResult updateStatement(Question question, int exerciseGroupID, boolean isNew) {
        String query, middlePart;
        Map<Integer, Object> values;
        middlePart = " SET qNumber = ?," +
                " qText = ?," +
                " qDisclaimer = ?," +
                " exerciseGroupID = ?";

        values = new HashMap<>();

        values.put(1, question.getNumber());
        values.put(2, question.getText());
        values.put(3, question.getDisclaimer());
        values.put(4, exerciseGroupID);

        if (isNew) {
            query = "INSERT INTO Question" + middlePart;
        } else {
            query = "UPDATE Question" + middlePart +
                    " WHERE questionID = ?";
            values.put(5, question.getId());
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
     * deletes a question from the database
     * @param questionID the ID the question is identified by
     * @return the results of the query
     */
    public DBResult deleteQuestion(int questionID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM Question" +
                " WHERE questionID = ?";

        values.put(1, questionID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * inserts values from the ResultSet into the Java representation of a Question and gets all associated
     * Subquestions from the database
     * @param results the ResultSet the values are stored in
     * @return the filled Question object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private Question setValues(ResultSet results) throws SQLException {
        Question question;
        SubquestionDAO subquestionDAO;

        question = new Question();

        question.setId(results.getInt("questionID"));
        question.setNumber(results.getInt("qNumber"));
        question.setText(results.getString("qText"));
        question.setDisclaimer(results.getString("qDisclaimer"));

        subquestionDAO = new SubquestionDAO();

        question.addSubquestions(subquestionDAO.getAllSubquestions(question.getId()));

        return question;
    }
}
