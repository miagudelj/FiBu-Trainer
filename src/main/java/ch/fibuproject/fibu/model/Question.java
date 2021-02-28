package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * A question. It only contains a general description of a question. Use it to give general circumstances of the
 * question. For the actual task, create a subquestion object and add it to "subquestions" in this object.
 *
 * (This represents a group. For example, you may have a group of tasks to solve, which all have common circumstances
 * and thus all belong to question 1). This is that 1). The first subquestion would then be a), so in total 1a).)
 *
 * @author Ciro Brodmann
 */

public class Question {

    private int id;
    private int number;
    private String text;
    private String disclaimer;
    private Vector<Subquestion> subquestions;

    /**
     * default constructor
     */
    public Question() {
        this.subquestions = new Vector<>();
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
     * gets the number
     * @return value of number
     */
    public int getNumber() {
        return number;
    }

    /**
     * sets the number
     * @param number the number to be set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * gets the text
     * @return value of text
     */
    public String getText() {
        return text;
    }

    /**
     * sets the text
     * @param text the text to be set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * gets the disclaimer
     * @return value of disclaimer
     */
    public String getDisclaimer() {
        return disclaimer;
    }

    /**
     * sets the disclaimer
     * @param disclaimer the disclaimer to be set
     */
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    /**
     * gets all subquestions
     * @return vector containing all subquestions
     */
    public Vector<Subquestion> getSubquestions() {
        return subquestions;
    }

    /**
     * gets the subquestion located at the designated index
     * @param index the index of the requested subquestion
     * @return the subquestion located at [index]
     */
    public Subquestion getSubquestion(int index) {
        return this.subquestions.get(index);
    }

    /**
     * adds a subquestion
     * @param subquestion the subquestion to be added
     */
    public void addSubquestion(Subquestion subquestion) {
        this.subquestions.add(subquestion);
    }

    /**
     * adds multiple subquestions
     * @param subquestions vector containing all subquestions to be added
     */
    public void addSubquestions(Vector<Subquestion> subquestions) {
        this.subquestions.addAll(subquestions);
    }
}
