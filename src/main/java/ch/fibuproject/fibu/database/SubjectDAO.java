package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO object for Subject.
 *
 * @author Ciro Brodmann
 */

public class SubjectDAO {

    /**
     * default constructor
     */
    public SubjectDAO() {
    }

    /**
     * gets the requested subject from the database
     * @param id the ID the subject is identified by
     * @return the requested subject or null if not found
     */
    public Subject getSubject(int id) {
        String query;
        Map<Integer, Object> values;
        ResultSet results;
        DBQueryAnswer answer = null;
        Subject subject = null;

        query = "SELECT * FROM Subject" +
                " WHERE subjectID = ?";

        values = new HashMap<>();

        values.put(1, id);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                subject = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return subject;
    }

    /**
     * gets all subjects from the database
     * @return vector containing all subjects or an empty one if none are found
     */
    public Vector<Subject> getAllSubjects() {
        String query;
        Vector<Subject> subjects;
        ResultSet results;
        DBQueryAnswer answer = null;
        Subject subject;

        subjects = new Vector<>();
        query = "SELECT * FROM Subject";

        try {
            answer = Database.simpleSelect(query);

            results = answer.getResults();

            while (results.next()) {
                subject = this.setValues(results);

                subjects.add(subject);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return subjects;
    }

    /**
     * saves a subject to the database
     * @param subject the subject to be saved
     * @return the results of the query
     */
    public DBResult saveSubject(Subject subject) {
        return this.updateStatement(subject, true);
    }

    /**
     * saves changes made to a subject to the database
     * @param subject the subject to be updated
     * @return the results of the query
     */
    public DBResult updateSubject(Subject subject) {
        return this.updateStatement(subject, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param subject the subject to be saved / updated
     * @param isNew true if the Subject is new (--> insert), otherwise false (--> update)
     * @return the results of the query
     */
    private DBResult updateStatement(Subject subject, boolean isNew) {
        String query, middlePart;
        Map<Integer, Object> values;
        middlePart = " SET subjectName = ?";

        values = new HashMap<>();

        values.put(1, subject.getName());

        if (isNew) {
            query = "INSERT INTO Subject" + middlePart;
        } else {
            query = "UPDATE Subject" + middlePart +
                    " WHERE subjectID = ?";
            values.put(2, subject.getId());
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
     * deletes a subject from the database
     * @param subjectID the ID the subject is identified by
     * @return the results of the query
     */
    public DBResult deleteSubject(int subjectID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM Subject" +
                " WHERE subjectID = ?";

        values.put(1, subjectID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * inserts values from the ResultSet into the Java representation of a Subject and gets all associated
     * Exercisegroups from the database
     * @param results the ResultSet the values are stored in
     * @return the filled Subject object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private Subject setValues(ResultSet results) throws SQLException {
        Subject subject;
        ExerciseGroupDAO exDAO;

        subject = new Subject();

        subject.setId(results.getInt("subjectID"));
        subject.setName(results.getString("subjectName"));

        exDAO = new ExerciseGroupDAO();

        subject.addExerciseGroups(exDAO.getAllExerciseGroups(subject.getId()));

        return subject;
    }
}
