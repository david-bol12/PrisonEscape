package org.prisongame.character;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.prisongame.game.GameMap;
import org.prisongame.terminal.Item;
import org.prisongame.terminal.Room;

import java.util.ArrayList;

public abstract class Character {
    protected String name;
    protected ArrayList<Item> inventory;

    public Character(String name) {
        this.name = name;
        this.inventory = new ArrayList<Item>();
    }

    public Character(String name, ArrayList<Item> inventory) {
        this.name = name;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public abstract GameMap getLocation();

    public abstract void setLocation(GameMap location);

    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
