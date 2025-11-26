/* This game is a classic text-based adventure set in a university environment.
   The player starts outside the main entrance and can navigate through different rooms like a 
   lecture theatre, campus pub, computing lab, and admin office using simple text commands (e.g., "go east", "go west").
    The game provides descriptions of each location and lists possible exits.

Key features include:
Room navigation: Moving among interconnected rooms with named exits.
Simple command parser: Recognizes a limited set of commands like "go", "help", and "quit".
Player character: Tracks current location and handles moving between rooms.
Text descriptions: Provides immersive text output describing the player's surroundings and available options.
Help system: Lists valid commands to guide the player.
Overall, it recreates the classic Zork interactive fiction experience with a university-themed setting, 
emphasizing exploration and simple command-driven gameplay
*/

package org.prisongame.game;

import org.prisongame.character.Player;
import org.prisongame.commands.Command;
import org.prisongame.commands.Parser;
import org.prisongame.items.Cookie;
import org.prisongame.items.Item;
import org.prisongame.ui.Output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class Game implements Flow.Subscriber<String>, Serializable {
    private final Parser parser = new Parser();
    private Player player;
    private Output output;
    private Flow.Subscription subscription;

    public Game(Output output, Player player) {
        this.output = output;
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Cookie());
        this.player = player;
    }

    public void play() {

        printWelcome();

//        boolean finished = false;
//        while (!finished) {
//            Command command = parser.getCommand();
//            finished = processCommand(command);
//        }
//        System.out.println("Thank you for playing. Goodbye.");
    }

    private void printWelcome() {
        output.println("Welcome to the University adventure!");
    }

    private void processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            output.println("I don't understand your command...");
        } else {

            switch (commandWord) {
                case "intellect":
                    player.setIntellect(player.getIntellect() + 10);
                    break;
                case "help":
                    printHelp();
                    break;
                case "go":
                    output.println(player.go(command.getSecondWord()));
                    break;
                case "search":
                    output.println(player.getCurrentRoom().searchRoom());
                    break;
                case "pick-up":
                    Item pickupItem = Item.checkItemAvailable(command.getSecondWord(), player.getCurrentRoom().getItems());
                    if (pickupItem == null) {
                        output.println("I can't find that item!");
                    } else {
                        output.println(player.pickUpItem(pickupItem));
                    }
                    break;
                case "inventory":
                    output.println("Inventory:");
                    for (Item item : player.getInventory()) {
                        output.println(item.getName());
                    }
                    break;
                case "drop":
                    Item dropItem = Item.checkItemAvailable(command.getSecondWord(), player.getInventory());
                    if (dropItem == null) {
                        output.println("I don't have that item!");
                    } else {
                        output.println(player.dropItem(dropItem));
                    }
                    break;
                case "eat":
                    output.println(player.eat(command.getSecondWord()));
                    break;
                case "quit":
                    if (command.hasSecondWord()) {
                        output.println("Quit what?");
                    } else {

                    }
                default:
                    output.println("I don't know what you mean...");
                    break;
            }
        }
    }

    private void printHelp() {
        output.println("You are lost. You are alone. You wander around the university.");
        output.print("Your command words are: ");
        parser.showCommands();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        output.printCommand(item);
        Command command = parser.parseCommand(item);
        processCommand(command);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
