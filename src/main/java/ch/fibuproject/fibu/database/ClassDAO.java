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
 * @author Ciro Brodmann
 *
 * Data Access Object for Class object.
 */

public class ClassDAO {

    public ClassDAO() {
    }

    public Class retrieveClass(int classID) {
        String query;
        Class newClass = null;
        Map<Integer, Object> values;
        ResultSet results;
        Vector<User> students;
        UserDAO userDAO = new UserDAO();

        query = "SELECT * FROM Class" +
                " WHERE classID = ?";

        values = new HashMap<>();

        values.put(1, classID);

        try {
            results = Database.selectStatement(query, values);
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
            Database.closeStatement();
        }

        return newClass;
    }

    public Vector<Class> retrieveAllClasses() {
        String query;
        ResultSet results;
        UserDAO userDAO;
        Vector<User> students;
        Vector<Class> classes = null;
        Class newClass;

        query = "SELECT * FROM Class";
        userDAO = new UserDAO();
        classes = new Vector<>();

        try {
            results = Database.simpleSelect(query);

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
            Database.closeStatement();
        }

        return classes;
    }

    public DBResult newClass(Class newClass) {
        return this.updateClass(newClass, true);
    }


    public DBResult updateClass (Class newClass) {
        return this.updateClass(newClass, false);
    }

    private DBResult updateClass(Class newClass, boolean isNew) {
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
        } finally {
            Database.closeStatement();
        }

    }

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
        } finally {
            Database.closeStatement();
        }
    }



    private void setValues(Class newClass, ResultSet results) throws SQLException {
        newClass.setId(results.getInt("classID"));
        newClass.setName(results.getString("className"));
    }
}
