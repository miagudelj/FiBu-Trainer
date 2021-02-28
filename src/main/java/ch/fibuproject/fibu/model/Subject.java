package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * A group of ExerciseGroups
 *
 * @author Ciro Brodmann
 */

public class Subject {

    private int id;
    private String name;
    private Vector<ExerciseGroup> exerciseGroups;

    /**
     * default constructor
     */
    public Subject() {
        this.exerciseGroups = new Vector<>();
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
     * gets all exerciseGroups
     * @return vector containing all exerciseGroups
     */
    public Vector<ExerciseGroup> getAllExerciseGroups() {
        return this.exerciseGroups;
    }

    /**
     * gets the exerciseGroup at the designated index
     * @param index the location of the requested exerciseGroup
     * @return the exerciseGroup located at [index]
     */
    public ExerciseGroup getExerciseGroup(int index) {
        return this.exerciseGroups.get(index);
    }

    /**
     * adds an exerciseGroup
     * @param exGroup the exerciseGroup to be added
     */
    public void addExerciseGroup(ExerciseGroup exGroup) {
        this.exerciseGroups.add(exGroup);
    }

    /**
     * adds multiple exerciseGroups
     * @param exerciseGroups vector of exerciseGroups to be added
     */
    public void addExerciseGroups(Vector<ExerciseGroup> exerciseGroups) {
        this.exerciseGroups.addAll(exerciseGroups);
    }
}
