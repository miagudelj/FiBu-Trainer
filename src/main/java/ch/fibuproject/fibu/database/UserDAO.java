package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.model.User;
import ch.fibuproject.fibu.model.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * DAO Object for User
 * TODO write better javadocs
 */
public class UserDAO {

    public static final int NOFILTER = 0;
    public static final int FILTERBYCLASSID = 1;
    public static final int FILTERBYTYPEID = 2;

    /**
     * default constructor
     */
    public UserDAO() {
    }

    /**
     * gets the requested user from the database
     * @param username the username of the requested user
     * @return the requested user or an empty user if not found
     */
    public User getUser(String username) {
        User user;
        String sqlStatement;
        Map<Integer, Object> values;
        ResultSet results;
        DBQueryAnswer answer = null;

        values = new HashMap<>();

        sqlStatement = "SELECT * FROM User" +
                " WHERE username = ?";


        values.put(1, username);

        try {
            answer = Database.selectStatement(sqlStatement, values);

            results = answer.getResults();

            user = new User();

            if (results.next()) {
                setValues(user, results);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return user;
    }

    /**
     * gets all users from the database
     * @return vector of all users or an empty one if none were found
     */
    public Vector<User> getAllUsers() {
        return this.getAllUsers(0, NOFILTER);
    }

    /**
     * gets all users matching to a certain classID or typeID (or all if filter = 0)
     * @param id the ID of either the associated class or type
     * @param filteroption the filteroption to be used (0 = no filter, 1 = filter by classID, 2 = filter by typeID)
     * @return vector of all matching users or an empty one if none were found
     */
    public Vector<User> getAllUsers(int id, int filteroption) {
        Vector<User> users;
        User user;
        String sqlStatement;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        values = new HashMap<>();

        sqlStatement = "SELECT * FROM User";

        users = new Vector<>();

        try {
            switch (filteroption) {
                case NOFILTER:
                    answer = Database.simpleSelect(sqlStatement);
                    break;
                case FILTERBYCLASSID:
                    sqlStatement = sqlStatement + " WHERE classID = ?";
                    values.put(1, id);
                    answer = Database.selectStatement(sqlStatement, values);
                    break;
                case FILTERBYTYPEID:
                    sqlStatement = sqlStatement + " WHERE typeID = ?";
                    values.put(1, id);
                    answer = Database.selectStatement(sqlStatement, values);
                    break;
            }

            results = answer.getResults();

            while (results.next()) {
                user = new User();

                this.setValues(user, results);

                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return users;
    }

    /**
     * saves a user who doesn't belong to any class to the database
     * @param user the user to be saved
     * @return the results of the query
     */
    public DBResult saveUser(User user) {
        return saveUser(user, 0);
    }

    /**
     * saves a user to the database
     * @param user the user to be saved
     * @param classID the class the user belongs to
     * @return the results of the query
     */
    public DBResult saveUser(User user, int classID) {

        String query;
        Map<Integer, Object> values;

        query = "INSERT INTO User" +
                " SET username = ?," +
                " passwordHash = ?," +
                " type = ?";

        values = new HashMap<>();

        values.put(1, user.getUsername());
        values.put(2, user.getPasswordHash());
        values.put(3, user.getType().toString());

        if (classID > 0) {
            query = query + ", classID = ?";

            values.put(4, classID);
        } else {
            query = query + ", classID = NULL";
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
     * saves changes made to a user to the database, doesn't change its class
     * @param user the user to be updated
     * @return the results of the query
     */
    public DBResult updateUser(User user) {
        return updateUser(user, 0);
    }

    /**
     * saves changes made to a user to the database, changes its class if classID > 0
     * @param user the user to be updated
     * @param classID the ID of the associated class
     * @return the results of the query
     */
    public DBResult updateUser(User user, int classID) {
        String query;
        Map<Integer, Object> values;
        int valuesCounter = 1;

        query = "UPDATE User" +
                " SET username = ?," +
                " passwordHash = ?," +
                " type = ?";

        values = new HashMap<>();

        values.put(valuesCounter, user.getUsername());
        valuesCounter++;
        values.put(valuesCounter, user.getPasswordHash());
        valuesCounter++;
        values.put(valuesCounter, user.getType().toString());
        valuesCounter++;

        if (classID > 0) {
            query = query + ", classID = ?";

            values.put(valuesCounter, classID);
            valuesCounter++;
        }

        query = query + " WHERE userID = ?";

        values.put(valuesCounter, user.getId());

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * sets this user's class to NULL ( no class )
     * @param username the user's username
     */
    public DBResult resetUserClass(String username) {
        return setUserClass(0, username);
    }

    /**
     * sets the user's class and saves it to the database
     * @param classID the ID of the Class to be set
     * @param username the username the user is identified by
     * @return the results of the query
     */
    public DBResult setUserClass(int classID, String username) {
        String query;
        Map<Integer, Object> values;
        int indexCounter = 1;

        query = "UPDATE USER";

        values = new HashMap<>();

        if (classID > 0) {
            query = query + " SET classID = ?";

            values.put(indexCounter, classID);
            indexCounter++;
        } else {
            query = query + " SET classID = NULL";
        }

        query = query + " WHERE username = ?";
        values.put(indexCounter, username);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * deletes a user from the database
     * @param userID the userID the user is identified by
     * @return the results of the query
     */
    public DBResult deleteUser(int userID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM User" +
                " WHERE userID = ?";

        values.put(1, userID);

        try {
            return Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * inserts values from the ResultSet into the Java representation of an ExerciseGroup
     * @param user the User the values are supposed to be inserted into
     * @param results the ResultSet the values are stored in
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private void setValues(User user, ResultSet results) throws SQLException {
        user.setId(results.getInt("userID"));
        user.setUsername(results.getString("username"));
        user.setPasswordHash(results.getString("passwordHash"));
        user.setType(UserType.valueOf(results.getString("type")));
    }


}
