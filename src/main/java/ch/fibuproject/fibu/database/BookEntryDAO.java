package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.BookEntry;
import ch.fibuproject.fibu.model.DBResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * DAO object for book entries
 *
 * @author Ciro Brodmann
 */

public class BookEntryDAO {

    public static final int FILTERBYUSERID = 1;
    public static final int FILTERBYSUBQUESTIONID = 2;

    /**
     * default constructor
     */
    public BookEntryDAO() {
    }

    /**
     * gets a BookEntry from the database
     * @param subquestionID the ID of the subquestion the BookEntry belongs to
     * @param userID the ID of the User it belongs to
     * @return the requested BookEntry, or null if not found
     */
    public BookEntry getBookEntry(int subquestionID, int userID) {
        String query;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        BookEntry bookEntry = null;

        query = "SELECT * FROM BookEntry" +
                " WHERE subquestionID = ?" +
                " AND userID = ?";

        values = new HashMap<>();

        values.put(1, subquestionID);
        values.put(2, userID);

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                bookEntry = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return bookEntry;
    }

    /**
     * gets all BookEntries from the database
     * @return a vector containing all BookEntries, an empty vector if none are found
     */
    public Vector<BookEntry> getAllBookEntries() {
        return this.getAllBookEntries(0, 0);
    }

    /**
     * gets all BookEntries belonging to either a user, a subquestion or simply all, depending on the filter
     * @param id the ID of either the user or the subquestion, depending on filter
     * @param filter the filter to be used (0 = no filter / all, 1 = filter by userID, 2 = filter by subquestionID)
     * @return a vector containing all matching BookEntries, an empty vector if none are found
     */
    public Vector<BookEntry> getAllBookEntries(int id, int filter) {
        String query;
        Vector<BookEntry> bookEntries;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        BookEntry bookEntry;

        values = new HashMap<>();
        values.put(1, id);
        bookEntries = new Vector<>();
        query = "SELECT * FROM BookEntry";

        try {
            switch (filter) {
                case FILTERBYSUBQUESTIONID:
                    query = query + " WHERE subquestionID = ?";
                    answer = Database.selectStatement(query, values);
                    break;
                case FILTERBYUSERID:
                    query = query + " WHERE userID = ?";
                    answer = Database.selectStatement(query, values);
                    break;
                default:
                    answer = Database.simpleSelect(query);
                    break;
            }

            results = answer.getResults();

            while (results.next()) {
                bookEntry = this.setValues(results);

                bookEntries.add(bookEntry);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        return bookEntries;
    }

    /**
     * saves a BookEntry to the database. If one with the same user and subquestion exists already, it gets replaced.
     * @param bookEntry the BookEntry to be saved
     * @return the result of the query
     */
    public DBResult saveBookEntry(BookEntry bookEntry) {
        String query, middlePart;
        int bookEntryID = 0;
        Map<Integer, Object> values;
        ResultSet results = null;
        DBQueryAnswer answer = null;
        middlePart = " SET accountSoll = ?," +
                " accountHaben = ?," +
                " amount = ?," +
                " subquestionID = ?," +
                " userID = ?";

        query = "SELECT bookEntryID" +
                " FROM BookEntry" +
                " WHERE subquestionID = ?" +
                " AND userID = ?";

        values = new HashMap<>();

        values.put(1, bookEntry.getSubquestionID());
        values.put(2, bookEntry.getUserID());

        try {
            answer = Database.selectStatement(query, values);

            results = answer.getResults();

            if (results.next()) {
                bookEntryID = results.getInt("bookEntryID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement(answer);
        }

        values.clear();

        values.put(1, bookEntry.getAccountSoll());
        values.put(2, bookEntry.getAccountHaben());
        values.put(3, bookEntry.getAmount());
        values.put(4, bookEntry.getSubquestionID());
        values.put(5, bookEntry.getUserID());

        if (bookEntryID > 0) {
            query = "UPDATE BookEntry" + middlePart +
                    " WHERE bookEntryID = ?";
            values.put(6, bookEntryID);
        } else {
            query = "INSERT INTO BookEntry" + middlePart;
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
     * deletes a bookEntry from the database
     * @param subquestionID the ID of the subquestion the bookEntry belongs to
     * @param userID the ID of the user the BookEntry belongs to
     * @return the result of the query
     */
    public DBResult deleteBookEntry(int subquestionID, int userID) {
        String query;
        Map<Integer, Object> values;

        values = new HashMap<>();

        query = "DELETE FROM BookEntry" +
                " WHERE subquestionID = ?" +
                " AND userID = ?";

        values.put(1, subquestionID);
        values.put(2, userID);

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
     * @param results the ResultSet the values are stored in
     * @return the filled BookEntry object
     * @throws SQLException throws an sqlexception if anything during the database access process went wrong
     */
    private BookEntry setValues(ResultSet results) throws SQLException {
        BookEntry bookEntry;

        bookEntry = new BookEntry();

        bookEntry.setId(results.getInt("bookEntryID"));
        bookEntry.setAccountSoll(results.getInt("accountSoll"));
        bookEntry.setAccountHaben(results.getInt("accountHaben"));
        bookEntry.setAmount(results.getBigDecimal("amount").doubleValue());
        bookEntry.setSubquestionID(results.getInt("subquestionID"));
        bookEntry.setUserID(results.getInt("userID"));

        return bookEntry;
    }
}
