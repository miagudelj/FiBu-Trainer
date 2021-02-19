package ch.fibuproject.fibu.database;

import ch.fibuproject.fibu.model.BookEntry;
import ch.fibuproject.fibu.model.DBResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * DAO object for book entries
 */

public class BookEntryDAO {

    public static final int FILTERBYUSERID = 1;
    public static final int FILTERBYSUBQUESTIONID = 2;

    public BookEntryDAO() {
    }

    public BookEntry getBookEntry(int subquestionID, int userID) {
        String query;
        Map<Integer, Object> values;
        ResultSet results;
        BookEntry bookEntry = null;

        query = "SELECT * FROM BookEntry" +
                " WHERE subquestionID = ?" +
                " AND userID = ?";

        values = new HashMap<>();

        values.put(1, subquestionID);
        values.put(2, userID);

        try {
            results = Database.selectStatement(query, values);

            if (results.next()) {
                bookEntry = this.setValues(results);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return bookEntry;
    }

    public Vector<BookEntry> getAllBookEntries() {
        return this.getAllBookEntries(0, 0);
    }


    public Vector<BookEntry> getAllBookEntries(int id, int filter) {
        String query;
        Vector<BookEntry> bookEntries;
        Map<Integer, Object> values;
        ResultSet results;
        BookEntry bookEntry;

        values = new HashMap<>();
        values.put(1, id);
        bookEntries = new Vector<>();
        query = "SELECT * FROM BookEntry";

        try {
            switch (filter) {
                case FILTERBYSUBQUESTIONID:
                    query = query + " WHERE subquestionID = ?";
                    results = Database.selectStatement(query, values);
                    break;
                case FILTERBYUSERID:
                    query = query + " WHERE userID = ?";
                    results = Database.selectStatement(query, values);
                    break;
                default:
                    results = Database.simpleSelect(query);
                    break;
            }

            while (results.next()) {
                bookEntry = this.setValues(results);

                bookEntries.add(bookEntry);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
        }

        return bookEntries;
    }

    public DBResult saveBookEntry(BookEntry bookEntry) {
        String query, middlePart;
        int bookEntryID = 0;
        Map<Integer, Object> values;
        ResultSet results;
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
            results = Database.selectStatement(query, values);

            if (results.next()) {
                bookEntryID = results.getInt("bookEntryID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        } finally {
            Database.closeStatement();
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
        } finally {
            Database.closeStatement();
        }
    }

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
        } finally {
            Database.closeStatement();
        }
    }

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
