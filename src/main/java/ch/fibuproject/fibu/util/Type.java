package ch.fibuproject.fibu.util;

public enum Type {

    MULTIPLECHOICE(0),
    FILLINTHEBLANKS(1),
    JOURNALENTRY(2);

    private int code;

    Type(int code) {
        this.setCode(code);
    }

    public void setCode(int code) {
        this.code = code;
    }
}
