package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * A group of exercises belonging to a subject
 *
 * @author Ciro Brodmann
 */

public class ExerciseGroup {

    private int id;
    private String name;
    private AccountChart accountChart;
    private int questionAmount;
    private Vector<Question> questions;

    /**
     * default constructor
     */
    public ExerciseGroup() {
        this.questionAmount = 0;
        this.questions = new Vector<>();
    }

    /**
     * gets the ID
     * @return value of id
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
     * @param name the value to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the accountChart
     * @return AccountChart-Object
     */
    public AccountChart getAccountChart() {
        return accountChart;
    }

    /**
     * sets an accountChart
     * @param accountChart the value to be set
     */
    public void setAccountChart(AccountChart accountChart) {
        this.accountChart = accountChart;
    }

    /**
     * gets the questionAmount
     * @return value of questionAmount
     */
    public int getQuestionAmount() {
        return questionAmount;
    }

    /**
     * sets the questionAmount
     * @param questionAmount the value to be set
     */
    public void setQuestionAmount(int questionAmount) {
        this.questionAmount = questionAmount;
    }

    /**
     * gets a Question
     * @param index index of question in the Vector
     * @return question at the selected index
     */
    public Question getQuestion(int index) {
        return this.questions.get(index);
    }

    /**
     * gets all Questions
     * @return Vector of questions
     */
    public Vector<Question> getQuestions() {
        return questions;
    }

    /**
     * Adds multiple questions to the collection
     * @param questions the Questions to be added
     */
    public void addQuestions(Vector<Question> questions) {
        this.questions.addAll(questions);
    }

    /**
     * Adds a question to the collection
     * @param question
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}
