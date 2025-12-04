package org.prisongame.commands;

public class Parser {

    public static Command parseCommand(String command) {
        String[] words = command.split(" ");
        String firstWord = words[0];
        String secondWord = null;
        if (words.length > 1) {
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < words.length; i++) {
                builder.append(words[i]);
                builder.append("-");
            }
            builder.deleteCharAt(builder.length() - 1);
            secondWord = builder.toString();
        }
        return new Command(firstWord, secondWord);
    }

    public static String removeSpaces(String string) {
        StringBuilder builder = new StringBuilder();
        String[] words = string.split(" ");
        for (String word : words) {
            builder.append(word);
            builder.append("-");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
