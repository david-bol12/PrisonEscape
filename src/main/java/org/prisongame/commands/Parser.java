package org.prisongame.commands;

import org.prisongame.ui.TextOut;

import java.util.Scanner;
import java.util.concurrent.Flow;

public class Parser {
    private CommandWords commands;

    public Parser() {
        commands = new CommandWords();
    }

    public Command parseCommand(String command) {
        String[] words = command.split(" ", 2);
        if (commands.isCommand(words[0])) {
            if (words.length > 1) {
                return new Command(words[0], words[1]);
            } else {
                return new Command(words[0], null);
            }
        } else return new Command(null, null);
    }

    public void showCommands() {
        commands.showAll();
    }
}
