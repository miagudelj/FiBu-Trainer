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

/**
 * Database class. Handles calls to database. To a certain degree reused from a previous project.
 *
 * @author Ciro Brodmann
 * @author Felix Reiniger
 */

public class Database {

    private static String host, database, username, password;
    private static int port, poolSize;
    private static HikariDataSource hikari;
    private static PreparedStatement pst;

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
        //Note: Due to the size of the current database it no longer generates out of this program. You will have to use
        //an sql dump file and insert it into MySQLWorkbench or PHPMyAdmin.
//        try {
//            List<String> lines = Files.readAllLines(Paths.get(System.getProperty("user.dir")+"\\src\\main\\resources\\test.sql"), StandardCharsets.UTF_8);
//            StringBuilder sqlDB = new StringBuilder();
//            for (String s:lines) {
//                if (!s.startsWith("-")&&!s.startsWith(System.lineSeparator())){
//                    sqlDB.append(s).append("\n");
//                }
//            }
//            Connection conn = getConnection();
//            PreparedStatement pstDB = createPreparedStatement(conn, sqlDB.toString());
//
//            pstDB.execute();
//
//        }catch (SQLException | IOException e){
//            e.printStackTrace();
//        }
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


    /**
     * executes a select query without variable values
     * @param query the SQL query to be executed
     * @return the answer from this query
     * @throws SQLException throws an sqlexception if something goes wrong with the sql query
     */
    static synchronized DBQueryAnswer simpleSelect(String query) throws SQLException {
        DBQueryAnswer answer = new DBQueryAnswer();

        try {
            answer.setConn(getConnection());
            answer.setPst(answer.getConn().prepareStatement(query));

            answer.setResults(answer.getPst().executeQuery());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

        return answer;
    }

    /**
     * executes a select query with variable values
     * @param query the select query to be executed
     * @param values the variable values to be inserted into the prepared statement
     * @return the answer from this query
     * @throws SQLException throws an sqlexception if something goes wrong with the sql query
     */
    static synchronized DBQueryAnswer selectStatement(String query, Map<Integer, Object> values) throws SQLException {
        DBQueryAnswer answer = new DBQueryAnswer();

        try {

            answer.setConn(getConnection());
            answer.setPst(answer.getConn().prepareStatement(query));

            setValues(values, answer.getPst());

            answer.setResults(answer.getPst().executeQuery());

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return answer;
    }

    /**
     * executes an update, insert or delete statement with variable values
     * @param query the statement to be executed
     * @param values the variable values to be inserted into the prepared statement
     * @return the result of the query
     * @throws SQLException throws an sqlexception if something goes wrong with the sql query
     */
    static synchronized DBResult updateStatement(String query, Map<Integer, Object> values) throws SQLException {
        DBQueryAnswer answer = new DBQueryAnswer();

        try {

            answer.setConn(getConnection());
            answer.setPst(answer.getConn().prepareStatement(query));

            setValues(values, answer.getPst());

            int affectedRows = answer.getPst().executeUpdate();
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
            closeStatement(answer);
        }

    }

    /**
     * inserts variable values into the prepared statement. Can only handle NULL, Integer, Double and String. For all
     * else, it just uses the toString() method.
     * @param values the values to be inserted
     * @param pst the prepared statement the values are supposed to be inserted into
     * @throws SQLException throws an sqlexception if something goes wrong with the insertion
     */
    private static void setValues(Map<Integer, Object> values, PreparedStatement pst) throws SQLException{
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

    /**
     * closes all objects related to the query in correct order
     * @param answer the answer containing all query-related objects
     */
    public static void closeStatement(DBQueryAnswer answer) {
        if (answer != null) {

            ResultSet results = answer.getResults();
            PreparedStatement pst = answer.getPst();
            Connection conn = answer.getConn();

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

            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }

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
}