package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.DBResult;
import ch.fibuproject.fibu.util.Configuration;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 * @author Felix Reiniger
 *
 * Database class. Handles calls to database. To a certain degree reused from a previous project.
 */

public class Database {

    private static String host, database, username, password;
    private static int port, poolSize;
    private static HikariDataSource hikari;
    private static PreparedStatement pst;
    private static ResultSet results;

    private Database() {

    }
    /**
     * Opens connection to db
     */
    private static void openConnection() {
        readConfig();

        hikari = new HikariDataSource();

        hikari.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);
        hikari.setMaximumPoolSize(poolSize);
    }


    /**
     * Initialises database
     */
    public static void init(){
        openConnection();

        // Note: Due to the size of the current database it no longer generates out of this program. You will have to use
        // an sql dump file and insert it into MySQLWorkbench or PHPMyAdmin.
    }

    /**
     * Creates a preparedstatement
     * @param conn db connection
     * @param sqlString sql command
     * @return preparedstatement
     * @throws SQLException
     */
    public static PreparedStatement createPreparedStatement(Connection conn, String sqlString) throws SQLException {
        return conn.prepareStatement(sqlString);
    }

    /**
     * Closes and reopens db connection/conenctionpool
     */
    public static void reloadDB(){
        readConfig();
        hikari.close();
        init();
    }

    /**
     * Returns connection
     * @return hikariconnection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        return hikari.getConnection();
    }

    /**
     * Reads and saves configuration file
     */
    private static void readConfig(){
        host = Configuration.getConfig().getString("MySQL.host");
        database = Configuration.getConfig().getString("MySQL.database");
        username = Configuration.getConfig().getString("MySQL.username");
        password = Configuration.getConfig().getString("MySQL.password");
        poolSize = Configuration.getConfig().getInt("MySQL.poolsize");
        port = Configuration.getConfig().getInt("MySQL.port");
    }


    // Retrieving methods from here onwards
    // TODO Create Methods to get data from db

    static synchronized ResultSet simpleSelect(String query) throws SQLException {
        Connection conn = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(query);

            results = pst.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            connectionClose(conn);
        }

        return results;
    }

    static synchronized ResultSet selectStatement(String query, Map<Integer, Object> values) throws SQLException{
        Connection conn = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(query);

            setValues(values);

            results = pst.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            connectionClose(conn);
        }
        return results;
    }

    static synchronized DBResult updateStatement(String query, Map<Integer, Object> values) throws SQLException {
        Connection conn = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(query);

            setValues(values);

            int affectedRows = pst.executeUpdate();
            // TODO insert handling of errors like "no affected rows"
            if (affectedRows == 0) {
                return DBResult.NOACTION;
            } else if (affectedRows > 0) {
                return DBResult.SUCCESS;
            } else {
                return DBResult.ERROR;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            connectionClose(conn);
        }

    }

    private static void setValues(Map<Integer, Object> values) throws SQLException{
        for (int i = 1; values.containsKey(i); i++) {
            if (values.get(i) == null) {
                pst.setString(i, null); // unproblematic, as int / double default to 0, not null when not initialised
                                           // however, this may need changing if more data types get introduced.
            } else if (values.get(i) instanceof Double) {
                pst.setBigDecimal(i, BigDecimal.valueOf((double) values.get(i)));
            } else if (values.get(i) instanceof Integer) {
                pst.setInt(i, (int) values.get(i));
            } else {
                pst.setString(i, values.get(i).toString());
            }
        }
    }

    // this may not be used.
    public static void simpleInsert(String insertStatement, String argument) throws SQLException{

        Connection conn = null;

        try {
            conn = getConnection();
            pst = conn.prepareStatement(insertStatement);
            pst.setString(1, argument);

            pst.execute();
        } finally {
            connectionClose(conn);
        }

    }



    public static void closeStatement(){
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        if (results != null) {
            try {
                results.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                results = null;
            }
        }
    }

    private static void connectionClose(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}