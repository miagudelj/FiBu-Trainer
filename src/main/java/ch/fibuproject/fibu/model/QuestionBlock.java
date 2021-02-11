package ch.fibuproject.fibu.model;

import java.util.List;
import java.util.Map;

/**
 * Vinicius
 * 05.Feb 2021
 * Version: 1.0
 * Project description:
 * Holds multiple Questions
 */

public class QuestionBlock implements BlockInterface{

    private int solved;
    private int correct;
    private String name;
    private Map<Integer, List<Question>> questionMap;

    public QuestionBlock() {

    }

    public QuestionBlock(int solved, int correct, String name, Map<Integer, List<Question>> questionMap) {
        this.solved = solved;
        this.correct = correct;
        this.name = name;
        this.questionMap = questionMap;
    }

    @Override
    public int getSolved() {
        return solved;
    }

    @Override
    public void setSolved(int solved) {
        this.solved = solved;
    }

    @Override
    public int getCorrect() {
        return correct;
    }

    @Override
    public void setCorrect(int correct) {
        this.correct = correct;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, List<Question>> getQuestionMap() {
        return questionMap;
    }

    public void setQuestionMap(Map<Integer, List<Question>> questionMap) {
        this.questionMap = questionMap;
    }

    public void addQuestionList(List<Question> questionList){
        questionMap.put(questionMap.size()+1, questionList);
    }
}