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

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.prisongame.character.Player;
import org.prisongame.commands.Command;
import org.prisongame.commands.CommandHelp;
import org.prisongame.commands.Parser;
import org.prisongame.items.Item;
import org.prisongame.map.GameMapState;
import org.prisongame.map.Location;
import org.prisongame.minigame.BlackjackGame;
import org.prisongame.minigame.Minigame;
import org.prisongame.ui.GameGUI;
import org.prisongame.ui.Input;
import org.prisongame.ui.Output;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Flow;

public class Game implements Flow.Subscriber<String> {
    private final Player player;
    private final Output output;
    private final Input input;
    private Flow.Subscription subscription;
    private Minigame currentMinigame = null;
    private final ObjectProperty<Boolean> maximiseTerminal = new SimpleObjectProperty<>(false);

    public Game(Output output, Input input, Player player) {
        this.output = output;
        this.input = input;
        this.player = player;
        initCommandHelp();
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

    private void initCommandHelp() {
        new CommandHelp("go", "Go to neighbouring location", () -> {
            StringBuilder roomsList = new StringBuilder();
            roomsList.append("Enter 'go' followed by a location name to go there \nAvailable Locations: \n");
            for (Location location : player.getCurrentRoom().getExits()) {
                roomsList.append("   ").append(GameMapState.getRoom(location).getName()).append("\n");
            }
            return roomsList.toString();
        });
        new CommandHelp("search", "Gives a description of the room and searches for all available items and people");
        new CommandHelp("pickup", "Takes the specified item and adds it to your inventory", "Enter 'pickup' + item name to pickup item");
        new CommandHelp("inventory", "Lists the items in your inventory");
        new CommandHelp("drop", "Drops the specified item out of your inventory into the current location", "Enter 'drop' + item name to drop item");
        new CommandHelp("eat", "Eats the specified item in your inventory regaining energy", "Enter 'eat' + item name to eat item");
        new CommandHelp("study", "Increases intellect by 5 but reduces energy by 20", () -> player.getLocation() == Location.LIBRARY, () -> null);
        new CommandHelp("exercise", "Increases strength by 5 but reduces energy by 20", () -> player.getLocation() == Location.GYM, () -> null);
        new CommandHelp("quit", "Exits + Saves game (Enter 'quit without save' to exit without saving)", () -> player.getLocation() == Location.GYM, () -> null);

    }

    private void processCommand(Command command) throws InterruptedException, IOException {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            output.println("I don't understand your command...");
        } else {

            switch (commandWord) {
                case "newgame":
                    System.out.println("running");
                    GameGUI.startGame(GameGUI.stage);
                    System.out.println("ran");
                    break;
                case "delay":
                    output.println("Waiting...");
                    input.disablePeriod(1000);
                    output.println("Done!");
                case "money":
                    player.setMoney(20);
                    break;
                case "test":
                    output.println("--- Welcome to Blackjack! ---");
                    setCurrentMinigame(new BlackjackGame(output, input, player));
                    break;
                case "study":
                    output.println(player.study());
                    break;
                case "exercise":
                    output.println(player.exercise());
                    break;
                case "help":
                    if (command.hasSecondWord()) {
                        output.println(CommandHelp.getCommandHelp(command.getSecondWord()));
                    } else {
                        output.println(CommandHelp.getCommandHelp());
                    }
                    break;
                case "go":
                    output.println(player.go(command.getSecondWord()));
                    break;
                case "search":
                    output.println(player.getCurrentRoom().searchRoom());
                    break;
                case "pickup":
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
                    output.println(getPlayer().dropItem(command.getSecondWord()));
                    break;
                case "eat":
                    output.println(player.eat(command.getSecondWord()));
                    break;
                case "quit":
                    if (command.hasSecondWord()) {
                        if (Objects.equals(command.getSecondWord(), "without-saving")) {
                            Platform.exit();
                        }
                        output.println("Quit what?");
                    } else {
                        GameGUI.saveGame(this);
                        Platform.exit();
                    }
                    break;
                default:
                    output.println("I don't know what you mean...");
                    break;
            }
        }
    }

    private void setCurrentMinigame(Minigame minigame) {
        if (minigame == null) {
            maximiseTerminal.set(false);
        } else {
            maximiseTerminal.set(true);
        }

        this.currentMinigame = minigame;
    }

    public Player getPlayer() {
        return player;
    }

    public ObjectProperty<Boolean> getMaximiseTerminal() {
        return maximiseTerminal;
    }



    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        output.printCommand(item);
        Command command = Parser.parseCommand(item);
        if (currentMinigame != null) {
            try {
                setCurrentMinigame(currentMinigame.processCommand(command));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                processCommand(command);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
