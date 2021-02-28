package ch.fibuproject.fibu.model;

import java.util.Vector;

/**
 * An extention of Subquestion containing a Vector to store all possible answers to a multiple-choice question.
 *
 * @author Ciro Brodmann
 */

public class MCQuestion extends Subquestion {

    private Vector<MCOption> options;

    /**
     * default constructor
     */
    public MCQuestion() {
        super();
        this.options = new Vector<>();
    }

    /**
     * gets all options
     * @return vector of all options
     */
    public Vector<MCOption> getOptions() {
        return options;
    }

    /**
     * gets the option located at the designated index
     * @param index the location of the option
     * @return MCOption located at [index]
     */
    public MCOption getOption(int index) {
        return this.options.get(index);
    }

    /**
     * adds an option
     * @param option the option to be added
     */
    public void addOption(MCOption option) {
        this.options.add(option);
    }

    /**
     * adds multiple options
     * @param options vector of options to be added
     */
    public void addOptions(Vector<MCOption> options) {
        this.options.addAll(options);
    }
}
