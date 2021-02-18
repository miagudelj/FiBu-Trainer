package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * A group of exercises belonging to a subject
 */

public class ExerciseGroup {

    private int id;
    private String name;
    private AccountChart accountChart;
    private int questionAmount;
    private Vector<Question> questions;

    public ExerciseGroup() {
        this.questionAmount = 0;
        this.questions = new Vector<>();
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

    public AccountChart getAccountChart() {
        return accountChart;
    }

    public void setAccountChart(AccountChart accountChart) {
        this.accountChart = accountChart;
    }

    public int getQuestionAmount() {
        return questionAmount;
    }

    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    public Vector<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public Question getQuestion(int index) {
        return this.questions.get(index);
    }
}
