package ch.fibuproject.fibu.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class contains all objects related to a database query. This was implemented, because it would've got hard to
 * keep hold of all relevant objects when returning from the central Database class.
 *
 * @author Ciro Brodmann
 */
public class DBQueryAnswer {

    private ResultSet results;
    private Connection conn;
    private PreparedStatement pst;

    /**
     * default constructor
     */
    public DBQueryAnswer() {

    }

    /**
     * gets the results
     * @return the ResultSet object
     */
    public ResultSet getResults() {
        return results;
    }

    /**
     * sets the results
     * @param results the ResultSet to be set
     */
    public void setResults(ResultSet results) {
        this.results = results;
    }

    /**
     * gets the connection
     * @return the connection object
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * sets the connection
     * @param conn the connection to be set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * gets the preparedStatement
     * @return the preparedStatement object stored in pst
     */
    public PreparedStatement getPst() {
        return pst;
    }

    /**
     * sets the preparedStatement
     * @param pst the preparedStatement to be set
     */
    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }
}
