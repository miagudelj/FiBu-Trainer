package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodman
 *
 * DAO for a Question.
 */

public class QuestionDAO {

    public QuestionDAO() {
    }

    public Question getQuestion(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results;
        Question question = null;

        query = "SELECT * FROM Question" +
                " WHERE questionID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            results = Database.selectStatement(query, values);

            if (results.next()) {
                question = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return question;
    }

    public Vector<Question> getAllQuestions() {
        return this.getAllQuestions(0);
    }

    public Vector<Question> getAllQuestions(int exerciseGroupID) {
        String query;
        Vector<Question> questions;
        Map<Integer, Object> values;
        ResultSet results;
        Question question;

        values = new HashMap<>();
        questions = new Vector<>();
        query = "SELECT * FROM Question";

        try {
            if (exerciseGroupID > 0) {
                query = query + " WHERE exerciseGroupID = ?";
                values.put(1, exerciseGroupID);
                results = Database.selectStatement(query, values);
            } else {
                results = Database.simpleSelect(query);
            }

            while (results.next()) {
                question = this.setValues(results);

                questions.add(question);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return questions;
    }

    public DBResult saveQuestion(Question question, int exerciseGroupID) {
        return this.updateStatement(question, exerciseGroupID, true);
    }

    public DBResult updateQuestion(Question question, int exerciseGroupID) {
        return this.updateStatement(question, exerciseGroupID, false);
    }


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
        } finally {
            Database.closeStatement();
        }
    }

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
        } finally {
            Database.closeStatement();
        }
    }

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
