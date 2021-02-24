package ch.fibuproject.fibu.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Ciro Brodmann
 */
public class DBQueryAnswer {

    private ResultSet results;
    private Connection conn;
    private PreparedStatement pst;

    public DBQueryAnswer() {

    }

    public ResultSet getResults() {
        return results;
    }

    public void setResults(ResultSet results) {
        this.results = results;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getPst() {
        return pst;
    }

    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }
}
