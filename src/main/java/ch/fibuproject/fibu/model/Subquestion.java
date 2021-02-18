package ch.fibuproject.fibu.model;

/**
 * @author Ciro Brodmann
 *
 * A subquestion (The letter part of a question, see JavaDoc of question for a more detailed explanation)
 * Can be of type "multiple choice" or "book entry".
 * Use this class if your question is of type "book entry". For type "multiple choice", use MCQuestion.
 */

public class Subquestion {

    private int id;
    private String text;
    private char letter;
    private SQType type;

    public Subquestion() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public SQType getType() {
        return type;
    }

    public void setType(SQType type) {
        this.type = type;
    }
}
