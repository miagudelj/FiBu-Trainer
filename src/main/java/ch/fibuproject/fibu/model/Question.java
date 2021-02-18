package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * A question. It only contains a general description of a question. Use it to give general circumstances of the
 * question. For the actual task, create a subquestion object and add it to "subquestions" in this object.
 *
 * (This represents a group. For example, you may have a group of tasks to solve, which all have common circumstances
 * and thus all belong to question 1). This is that 1). The first subquestion would then be a), so in total 1a).)
 */

public class Question {

    private int id;
    private int number;
    private String text;
    private String disclaimer;
    private Vector<Subquestion> subquestions;

    public Question() {
        this.subquestions = new Vector<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Vector<Subquestion> getSubquestions() {
        return subquestions;
    }

    public Subquestion getSubquestion(int index) {
        return this.subquestions.get(index);
    }

    public void setSubquestion(Subquestion subquestion) {
        this.subquestions.add(subquestion);
    }
}
