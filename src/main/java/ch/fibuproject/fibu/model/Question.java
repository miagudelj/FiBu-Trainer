package ch.fibuproject.fibu.model;

import ch.fibuproject.fibu.util.Type;

/**
 * Vinicius
 * 05.Feb 2021
 * Version: 1.0
 * Project description:
 * Holds question information
 */

public class Question {

    private Answer answer;
    private String text;
    private Type type;

    public Question(){

    }

    public Question(Answer answer, String text, Type type) {
        this.answer = answer;
        this.text = text;
        this.type = type;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}