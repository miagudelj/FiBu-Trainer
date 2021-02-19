package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * @author Ciro Brodmann
 *
 * An extention of Subquestion containing a Vector to store all possible answers to a multiple-choice question.
 */

public class MCQuestion extends Subquestion {

    private Vector<MCOption> options;

    public MCQuestion() {
        super();
        this.options = new Vector<>();
    }

    public Vector<MCOption> getOptions() {
        return options;
    }

    public MCOption getOption(int index) {
        return this.options.get(index);
    }

    public void addOption(MCOption option) {
        this.options.add(option);
    }

    public void addOptions(Vector<MCOption> options) {
        this.options.addAll(options);
    }
}
