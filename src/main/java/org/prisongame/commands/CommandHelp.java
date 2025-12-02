package org.prisongame.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandHelp {
    private static Map<String, CommandHelp> commands = new HashMap<String, CommandHelp>();
    private String command;
    private String description;
    private Supplier<String> help;
    private Supplier<Boolean> isValid;

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getHelp() {
        return help.get();
    }

    public Boolean getIsValid() {
        return isValid.get();
    }

    public CommandHelp(String command, String description, Supplier<Boolean> isValid, Supplier<String> help) {
        this.command = command;
        this.description = description;
        this.isValid = isValid;
        this.help = help;
        commands.put(command, this);
    }

    public CommandHelp(String command, String description, Supplier<String> help) {
        this.command = command;
        this.description = description;
        this.isValid = () -> true;
        this.help = help;
        commands.put(command, this);
    }

    public CommandHelp(String command, String description, String help) {
        this.command = command;
        this.description = description;
        this.isValid = () -> true;
        this.help = () -> help;
        commands.put(command, this);
    }

    public CommandHelp(String command, String description) {
        this.command = command;
        this.description = description;
        this.isValid = () -> true;
        this.help = null;
        commands.put(command, this);
    }

    public static boolean isCommand(String commandWord) {
        return commands.containsKey(commandWord);
    }

    public static String getCommandHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("Current Valid Commands: \n");
        for (String command : commands.keySet()) {
            if (commands.get(command).getIsValid()) {
                builder.append("   ");
                builder.append(command);
                builder.append(" - ");
                builder.append(commands.get(command).getDescription());
                builder.append("\n");
            }
        }
        builder.append("Enter 'help' followed by a command for an additional description");
        return builder.toString();
    }

    public static String getCommandHelp(String command) {
        if (commands.containsKey(command)) {
            return commands.get(command).getCommand() + " - " +
                (commands.get(command).getHelp() != null ? commands.get(command).getHelp() : commands.get(command).getDescription());
        }
        return "Invalid command";
    }
}
