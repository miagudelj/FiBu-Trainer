package ch.fibuproject.fibu.model;

import java.util.Map;

/**
 * Vinicius
 * 05.Feb 2021
 * Version: 1.0
 * Project description:
 * Holds multiple QuestionBlocks
 */

public class ThemeBlock implements BlockInterface{

    private String name;
    private Map<String, QuestionBlock> questionBlockMap;

    public ThemeBlock(){

    }

    public ThemeBlock(String name, Map<String, QuestionBlock> questionBlockMap) {
        this.name = name;
        this.questionBlockMap = questionBlockMap;
    }

    @Override
    public int getSolved() {
        int solved = 0;
        for(QuestionBlock questionBlock : questionBlockMap.values()){
            solved += questionBlock.getSolved();
        }
        return solved;
    }

    @Override
    public int getCorrect() {
        int correct = 0;
        for(QuestionBlock questionBlock : questionBlockMap.values()){
            correct += questionBlock.getCorrect();
        }
        return correct;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Map<String, QuestionBlock> getQuestionBlockMap() {
        return questionBlockMap;
    }

    public void setQuestionBlockMap(Map<String, QuestionBlock> questionBlockMap) {
        this.questionBlockMap = questionBlockMap;
    }
}