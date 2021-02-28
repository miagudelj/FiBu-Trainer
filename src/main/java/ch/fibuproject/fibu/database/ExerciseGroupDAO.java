package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.ExerciseGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO for an ExerciseGroup.
 *
 * @author Ciro Brodmann
 */

public class ExerciseGroupDAO {

    /**
     * default constructor
     */
    public ExerciseGroupDAO() {
    }

    /**
     * gets the requested ExerciseGroup from the database
     * @param id the ID the ExerciseGroup is identified by
     * @return the requested exerciseGroup or null if not found
     */
    public ExerciseGroup getExerciseGroup(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        ExerciseGroup exerciseGroup = null;

        query = "SELECT * FROM ExerciseGroup" +
                " WHERE exerciseGroupID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                exerciseGroup = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return exerciseGroup;
    }

    /**
     * gets all exerciseGroups from the database
     * @return a vector containing all exerciseGroups or an empty one if none are found
     */
    public Vector<ExerciseGroup> getAllExerciseGroups() {
        return this.getAllExerciseGroups(0);
    }

    /**
     * gets all exerciseGroup associated with a subject
     * @param subjectID the ID of the associated Subject
     * @return a vector containing exerciseGroups or an empty one if none are found
     */
    public Vector<ExerciseGroup> getAllExerciseGroups(int subjectID) {
        String query;
        Vector<ExerciseGroup> exerciseGroups;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        ExerciseGroup exerciseGroup;

        values = new HashMap<>();
        exerciseGroups = new Vector<>();
        query = "SELECT * FROM ExerciseGroup";

        try {
            if (subjectID > 0) {
                query = query + " WHERE subjectID = ?";
                values.put(1, subjectID);
                answer = Database.selectStatement(query, values);
            } else {
                answer = Database.simpleSelect(query);
            }

            results = answer.getResults();

            while (results.next()) {
                exerciseGroup = this.setValues(results);

                exerciseGroups.add(exerciseGroup);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return exerciseGroups;
    }

    /**
     * saves an exerciseGroup to the database
     * @param exerciseGroup the exerciseGroup to be saved
     * @param subjectID the ID of the associated Subject
     * @return the result of the query
     */
    public DBResult saveExerciseGroup(ExerciseGroup exerciseGroup, int subjectID) {
        return this.updateStatement(exerciseGroup, subjectID, true);
    }

    /**
     * saves changes to an exerciseGroup to the database
     * @param exerciseGroup the exerciseGroup to be updated
     * @param subjectID the ID of the associated Subject
     * @return the result of the query
     */
    public DBResult updateExerciseGroup(ExerciseGroup exerciseGroup, int subjectID) {
        return this.updateStatement(exerciseGroup, subjectID, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param exerciseGroup the exerciseGroup to be saved / updated
     * @param subjectID the Id of the associated Subject
     * @param isNew true if the ExerciseGroup is new (--> insert), otherwise false (--> update)
     * @return the results of the query
     */
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
        }
    }

    /**
     * deletes an exerciseGroup from the database
     * @param exerciseGroupID the ID of the exerciseGroup which is supposed to be deleted
     * @return the results of the query
     */
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
        }
    }

    /**
     * inserts values from the ResultSet into the Java representation of an ExerciseGroup and gets all associated
     * Questions and AccountCharts from the database
     * @param results the ResultSet the values are stored in
     * @return the filled ExerciseGroup object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
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
