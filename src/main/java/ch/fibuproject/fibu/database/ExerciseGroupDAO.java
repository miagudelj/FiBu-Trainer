package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.AccountChart;
import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.ExerciseGroup;
import ch.fibuproject.fibu.model.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * DAO for an ExerciseGroup.
 */

public class ExerciseGroupDAO {

    public ExerciseGroupDAO() {
    }

    public ExerciseGroup getExerciseGroup(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results;
        ExerciseGroup exerciseGroup = null;

        query = "SELECT * FROM ExerciseGroup" +
                " WHERE exerciseGroupID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            results = Database.selectStatement(query, values);

            if (results.next()) {
                exerciseGroup = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return exerciseGroup;
    }

    public Vector<ExerciseGroup> getAllExerciseGroups() {
        return this.getAllExerciseGroups(0);
    }

    public Vector<ExerciseGroup> getAllExerciseGroups(int subjectID) {
        String query;
        Vector<ExerciseGroup> exerciseGroups;
        Map<Integer, Object> values;
        ResultSet results;
        ExerciseGroup exerciseGroup;

        values = new HashMap<>();
        exerciseGroups = new Vector<>();
        query = "SELECT * FROM ExerciseGroup";

        try {
            if (subjectID > 0) {
                query = query + " WHERE subjectID = ?";
                values.put(1, subjectID);
                results = Database.selectStatement(query, values);
            } else {
                results = Database.simpleSelect(query);
            }

            while (results.next()) {
                exerciseGroup = this.setValues(results);

                exerciseGroups.add(exerciseGroup);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return exerciseGroups;
    }

    public DBResult saveExerciseGroup(ExerciseGroup exerciseGroup, int subjectID) {
        return this.updateStatement(exerciseGroup, subjectID, true);
    }

    public DBResult updateExerciseGroup(ExerciseGroup exerciseGroup, int subjectID) {
        return this.updateStatement(exerciseGroup, subjectID, false);
    }


    private DBResult updateStatement(ExerciseGroup exerciseGroup, int subjectID, boolean isNew) {
        String query, middlePart;
        Map<Integer, Object> values;
        middlePart = " SET exGroupName = ?," +
                " accountChartID = ?," +
                " questionAmount = ?," +
                " subjectID = ?";

        values = new HashMap<>();

        values.put(1, exerciseGroup.getName());
        values.put(2, exerciseGroup.getAccountChart().getId());
        values.put(3, exerciseGroup.getQuestionAmount());
        values.put(4, subjectID);

        if (isNew) {
            query = "INSERT INTO ExerciseGroup" + middlePart;
        } else {
            query = "UPDATE ExerciseGroup" + middlePart +
                    " WHERE exerciseGroupID = ?";
            values.put(5, exerciseGroup.getId());
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

    public DBResult deleteExerciseGroup(int exerciseGroupID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM ExerciseGroup" +
                " WHERE exerciseGroupID = ?";

        values.put(1, exerciseGroupID);

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

    private ExerciseGroup setValues(ResultSet results) throws SQLException {
        ExerciseGroup exerciseGroup;
        QuestionDAO questionDAO;

        exerciseGroup = new ExerciseGroup();

        exerciseGroup.setId(results.getInt("exerciseGroupID"));
        exerciseGroup.setName(results.getString("exGroupName"));
        exerciseGroup.setQuestionAmount(results.getInt("questionAmount"));

        questionDAO = new QuestionDAO();
        AccountChartDAO acDAO = new AccountChartDAO();

        exerciseGroup.addQuestions(questionDAO.getAllQuestions(exerciseGroup.getId()));
        exerciseGroup.setAccountChart(acDAO.getAccountChart(results.getInt("accountChartID")));

        return exerciseGroup;
    }
}
