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

import org.prisongame.commands.Command;
import org.prisongame.commands.Parser;
import org.prisongame.terminal.Character;
import org.prisongame.terminal.Item;
import org.prisongame.terminal.Room;
import org.prisongame.ui.Output;

import java.util.ArrayList;
import java.util.concurrent.Flow;

public class Game implements Flow.Subscriber<String> {
    private final Parser parser = new Parser();
    private org.prisongame.terminal.Character player;
    private Output output;
    private Flow.Subscription subscription;

    public Game(Output output) {
        this.output = output;
        createRooms();
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office, iseRoom;
        Item sword;
        ArrayList<Item> labItems = new ArrayList<Item>();

        // create items
        sword = new Item("Sword", "Looks Sharp");
        labItems.add(sword);

        // create rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab", labItems);
        office = new Room("in the computing admin office");
        iseRoom = new Room("in ise room");

        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        iseRoom.setExit("north", pub);

        // create the player character and start outside
        player = new Character("player", outside);
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
        }

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
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
            case "place":
                Item placeItem = Item.checkItemAvailable(command.getSecondWord(), player.getInventory());
                if (placeItem == null) {
                    output.println("I don't have that item!");
                } else {
                    player.placeItem(placeItem);
                }
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    output.println("Quit what?");
                } else {
                     // signal to quit
                }
            default:
                output.println("I don't know what you mean...");
                break;
        }
    }

    private void printHelp() {
        output.println("You are lost. You are alone. You wander around the university.");
        output.print("Your command words are: ");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            output.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            player.setCurrentRoom(nextRoom);
            output.println(player.getCurrentRoom().getLongDescription());
        }
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
