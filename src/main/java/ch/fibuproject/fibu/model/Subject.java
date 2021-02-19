package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * A group of ExerciseGroups
 */

public class Subject {

    private int id;
    private String name;
    private Vector<ExerciseGroup> exerciseGroups;

    public Subject() {
        this.exerciseGroups = new Vector<>();
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

    public Vector<ExerciseGroup> getAllExerciseGroups() {
        return this.exerciseGroups;
    }

    public ExerciseGroup getExerciseGroup(int index) {
        return this.exerciseGroups.get(index);
    }

    public void addExerciseGroup(ExerciseGroup exGroup) {
        this.exerciseGroups.add(exGroup);
    }

    public void addExerciseGroups(Vector<ExerciseGroup> exerciseGroups) {
        this.exerciseGroups.addAll(exerciseGroups);
    }
}
