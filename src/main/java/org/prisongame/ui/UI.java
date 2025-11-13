package org.prisongame.ui;

import org.prisongame.ui.cli.CLIController;
import org.prisongame.ui.gui.GameGUI;

public class UI implements Output{
    boolean initCLI;
    Output[] outputs;
    GameGUI gui;
    CLIController cli;

    public UI(String[] args) {
        cli = new CLIController();
        outputs = new Output[]{gui, cli};
    }

    public void print(String text) {
        for (Output output : outputs) {
            output.print(text);
        }
    }

    public void println(String text) {
        for (Output output : outputs) {
            output.println(text);
        }
    }

    public void clear() {
        for (Output output : outputs) {
            output.clear();
        }
    }
}
