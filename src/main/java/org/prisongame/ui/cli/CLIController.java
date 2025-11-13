package org.prisongame.ui.cli;

import org.prisongame.ui.Output;

public class CLIController implements Output {

    @Override
    public void print(String text) {
        System.out.print(text);
    }

    @Override
    public void println(String text) {
        System.out.println(text);
    }

    @Override
    public void clear() {

    }
}
