package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.Class;
import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Data Access Object for Class object.
 *
 * @author Ciro Brodmann
 */

public class ClassDAO {

    /**
     * default constructor
     */
    public ClassDAO() {
    }

    /**
     * gets the requested class from the database
     * @param classID the ID the class is identified by
     * @return the corresponding class, or null if not found
     */
    public Class retrieveClass(int classID) {
        String query;
        Class newClass = null;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        Vector<User> students;
        UserDAO userDAO = new UserDAO();

        query = "SELECT * FROM Class" +
                " WHERE classID = ?";

        values = new HashMap<>();

        values.put(1, classID);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();
            if (results.next()) {
                newClass = new Class();

                this.setValues(newClass, results);

                students = userDAO.getAllUsers(newClass.getId(), UserDAO.FILTERBYCLASSID);
                newClass.addStudents(students);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return newClass;
    }

    /**
     * gets all classes from the database
     * @return a vector containing all classes, an empty vector if none are found
     */
    public Vector<Class> retrieveAllClasses() {
        String query;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        UserDAO userDAO;
        Vector<User> students;
        Vector<Class> classes = null;
        Class newClass;

        query = "SELECT * FROM Class";
        userDAO = new UserDAO();
        classes = new Vector<>();

        try {
            answer = Database.simpleSelect(query);

            results = answer.getResults();

            while (results.next()) {
                newClass = new Class();

                this.setValues(newClass, results);

                students = userDAO.getAllUsers(newClass.getId(), UserDAO.FILTERBYCLASSID);
                newClass.addStudents(students);

                classes.add(newClass);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return classes;
    }

    /**
     * saves a class to the database
     * @param newClass the class to be saved
     * @return the result of the query
     */
    public DBResult newClass(Class newClass) {
        return this.updateStatement(newClass, true);
    }

    /**
     * saves changes to a class to the database
     * @param newClass the class to be updated
     * @return the results of the query
     */
    public DBResult updateClass (Class newClass) {
        return this.updateStatement(newClass, false);
    }

    /**
     * this function handles insert and update statement to the database
     * @param newClass the class to be saved / updated
     * @param isNew true if the Class is new (--> insert), otherwise false (--> update)
     * @return the results of the query
     */
    private DBResult updateStatement(Class newClass, boolean isNew) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        values.put(1, newClass.getName());
        if (isNew) {
            query = "INSERT INTO Class" +
                    " SET className = ?";
        } else {
            query = "UPDATE Class" +
                    " SET className = ?" +
                    " WHERE classID = ?";

            values.put(2, newClass.getId());
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
     * deletes a class from the database
     * @param classID the ID the class is identified by
     * @return the results of the query
     */
    public DBResult deleteClass(int classID) {
        String query;
        Map<Integer, Object> values;

        query = "DELETE FROM Class" +
                " WHERE classID = ?";

        values = new HashMap<>();
        values.put(1, classID);

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
     * @param newClass the class the values are supposed to be stored into
     * @param results the ResultSet the values are stored in
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private void setValues(Class newClass, ResultSet results) throws SQLException {
        newClass.setId(results.getInt("classID"));
        newClass.setName(results.getString("className"));
    }
}
