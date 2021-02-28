package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * This class represents a school class with students.
 *
 * @author Ciro Brodmann
 */
public class Class {

    private int id;
    private String name;
    private Vector<User> students;

    /**
     * default constructor
     */
    public Class() {
        this.students = new Vector<>();
    }

    /**
     * gets the ID
     * @return value of ID
     */
    public int getId() {
        return id;
    }

    /**
     * sets the ID
     * @param id the ID to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the name
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name
     * @param name the name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets all students
     * @return Vector containing all students
     */
    public Vector<User> getStudents() {
        return students;
    }

    /**
     * adds multiple students
     * @param students Vector of students to be added
     */
    public void addStudents(Vector<User> students) {
        this.students.addAll(students);
    }

    /**
     * adds a single student
     * @param student the student to be added
     */
    public void addStudent(User student) {
        this.students.add(student);
    }

    /**
     * gets the student at the requested index
     * @param index the index of the requested student
     * @return the student at [index]
     */
    public User getStudent(int index) {
        return this.students.get(index);
    }
}
