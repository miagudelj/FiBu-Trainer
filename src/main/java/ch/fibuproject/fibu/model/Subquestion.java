package ch.fibuproject.fibu.model;

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
