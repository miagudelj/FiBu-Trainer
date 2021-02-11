package ch.fibuproject.fibu.model;

public interface BlockInterface {
    default int getSolved() {
        throw new UnsupportedOperationException();
    }
    default int getCorrect() {
        throw new UnsupportedOperationException();
    }
    default String getName() {
        throw new UnsupportedOperationException();
    }
    default void setSolved(int solved) {
        throw new UnsupportedOperationException();
    }
    default void setCorrect(int correct) {
        throw new UnsupportedOperationException();
    }
    default void setName(String name) {
        throw new UnsupportedOperationException();
    }
}
