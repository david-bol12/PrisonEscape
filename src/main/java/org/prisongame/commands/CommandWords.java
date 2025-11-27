package org.prisongame.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private Map<String, String> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", "Move to another room");
        validCommands.put("quit", "End the game");
        validCommands.put("help", "Show help");
        validCommands.put("look", "Look around");
        validCommands.put("eat", "Eat something");
        validCommands.put("search", "Search for Items");
        validCommands.put("pick-up", "Pick up item");
        validCommands.put("drop", "Places item in room");
        validCommands.put("inventory", "Checks inventory");
        validCommands.put("intellect", "make smart");
        validCommands.put("test", "to test features");
        validCommands.put("h", "hit in blackjack");
    }

    public boolean isCommand(String commandWord) {
        return validCommands.containsKey(commandWord);
    }

    public void showAll() {
        System.out.print("Valid commands are: ");
        for (String command : validCommands.keySet()) {
            System.out.print(command + " ");
        }
        System.out.println();
    }
}
