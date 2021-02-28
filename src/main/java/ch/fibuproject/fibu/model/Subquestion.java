package ch.fibuproject.fibu.model;

/**
 * A subquestion (The letter part of a question, see JavaDoc of question for a more detailed explanation)
 * Can be of type "multiple choice" or "book entry".
 * Use this class if your question is of type "book entry". For type "multiple choice", use MCQuestion.
 *
 * @author Ciro Brodmann
 */

public class Subquestion {

    private int id;
    private String text;
    private char letter;
    private SQType type;

    /**
     * default constructor
     */
    public Subquestion() {

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
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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
     * gets the letter
     * @return value of letter
     */
    public char getLetter() {
        return letter;
    }

    /**
     * sets the letter
     * @param letter the value to be set
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * gets the SQType
     * @return value of type
     */
    public SQType getType() {
        return type;
    }

    /**
     * sets the SQType
     * @param type the SQType to be set
     */
    public void setType(SQType type) {
        this.type = type;
    }
}
