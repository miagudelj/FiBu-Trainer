package ch.fibuproject.fibu.model;

import java.util.Vector;

public class Class {

    private int id;
    private String name;
    private Vector<User> students;

    public Class() {
        this.students = new Vector<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector<User> getStudents() {
        return students;
    }

    public void addStudent(User student) {
        this.students.add(student);
    }

    public User getStudent(int index) {
        return this.students.get(index);
    }
}
