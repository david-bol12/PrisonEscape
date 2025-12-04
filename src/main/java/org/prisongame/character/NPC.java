package org.prisongame.character;
import org.prisongame.commands.Parser;
import org.prisongame.game.Game;
import org.prisongame.map.GameMapState;
import org.prisongame.map.Location;
import org.prisongame.items.Item;
import org.prisongame.ui.Output;
import org.prisongame.utils.ContainerObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NPC extends Character implements ContainerObject {

    private final Dialog dialog;

    public NPC(String name, Location location, List<Item> inventory) {
        super(name, location, inventory);
        this.dialog = null;
    }

    public NPC(String name, Location location) {
        super(name, location, new ArrayList<Item>());
        this.dialog = null;
    }

    public NPC(String name, Location location, Dialog dialog) {
        super(name, location);
        this.dialog = dialog;
    }

    public NPC(String name, Location location, Dialog dialog, ArrayList<Item> inventory) {
        super(name, location, inventory);
        this.dialog = dialog;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        GameMapState.getRoom(location);
    }


    public Dialog getConversation() {
        return dialog;
    }

    public void say(String message) {
        Game.getOutput().println(name + ": " + message);
    }

    public void talk() throws InterruptedException {
        if (dialog == null) {
            Game.getOutput().println("No response...");
        } else {
            switch (dialog.getDialogType()) {
                case LINEAR -> {
                    say(dialog.getDialogLines()[dialog.getLineCounter() % dialog.getDialogLines().length]);
                }
                case RANDOM -> {
                    Random random = new Random();
                    say(dialog.getDialogLines()[random.nextInt(dialog.getDialogLines().length)]);
                }
                case MONOLOGUE -> {
                    for (String line : dialog.getDialogLines()) {
                        say(line);
                        Game.getInput().disablePeriod(dialog.MONOLOGUE_DELAY);
                    }
                }
            }
        }
    }
}
