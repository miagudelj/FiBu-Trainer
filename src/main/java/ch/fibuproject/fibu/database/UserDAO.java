package ch.fibuproject.fibu.database;

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

    public UserDAO() {
    }

    public User getUser(String username) {
        User user = null;
        String sqlStatement;
        Map<Integer, Object> values;
        ResultSet results;

        values = new HashMap<>();

        sqlStatement = "SELECT * FROM User" +
                " WHERE username = ?";


        values.put(1, username);

        try {
            results = Database.selectStatement(sqlStatement, values);

            user = new User();

            if (results.next()) {
                setValues(user, results);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return user;
    }

    public Vector<User> getAllUsers() {
        return this.getAllUsers(0, NOFILTER);
    }

    public Vector<User> getAllUsers(int id, int filteroption) {
        Vector<User> users = null;
        User user;
        String sqlStatement;
        Map<Integer, Object> values;
        ResultSet results = null;
        values = new HashMap<>();

        sqlStatement = "SELECT * FROM User";

        try {
            switch (filteroption) {
                case NOFILTER:
                    results = Database.simpleSelect(sqlStatement);
                    break;
                case FILTERBYCLASSID:
                    sqlStatement = sqlStatement + " WHERE classID = ?";
                    values.put(1, id);
                    results = Database.selectStatement(sqlStatement, values);
                    break;
                case FILTERBYTYPEID:
                    sqlStatement = sqlStatement + " WHERE typeID = ?";
                    values.put(1, id);
                    results = Database.selectStatement(sqlStatement, values);
                    break;
            }
            users = new Vector<>();

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
            Database.closeStatement();
        }

        return users;
    }

    public void saveUser(User user) {
        saveUser(user, 0);
    }

    public void saveUser(User user, int classID) {

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
            Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }
    }

    public void updateUser(User user) {
        updateUser(user, 0);
    }

    public void updateUser(User user, int classID) {
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
            Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }
    }

    public void deleteUser(int userID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM User" +
                " WHERE userID = ?";

        values.put(1, userID);

        try {
            Database.updateStatement(query, values);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }
    }

    private void setValues(User user, ResultSet results) throws SQLException {
        user.setId(results.getInt("userID"));
        user.setUsername(results.getString("username"));
        user.setPasswordHash(results.getString("passwordHash"));
        user.setType(UserType.valueOf(results.getString("type")));
    }


}
