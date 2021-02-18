package ch.fibuproject.fibu.model;

import java.util.Vector;

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
