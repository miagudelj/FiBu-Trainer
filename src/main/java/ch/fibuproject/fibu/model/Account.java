package ch.fibuproject.fibu.model;

/**
 * @author Ciro Brodmann
 *
 * This class represents a bookkeeping account
 */

// TODO create javadocs

public class Account {
   private int id;
   private int number;
   private String name;

   public Account() {
   }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
