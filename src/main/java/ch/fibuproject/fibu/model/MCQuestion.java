package ch.fibuproject.fibu.model;

import java.util.Vector;

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
}
