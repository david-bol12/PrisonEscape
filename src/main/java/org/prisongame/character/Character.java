package org.prisongame.character;

import org.prisongame.map.GameMap;
import org.prisongame.items.Item;
import java.util.ArrayList;

public abstract class Character {
    protected String name;
    protected ArrayList<Item> inventory;
    protected GameMap location;

    public Character(String name, GameMap location) {
        this.name = name;
        this.location = location;
        this.inventory = new ArrayList<Item>();
    }

    public Character(String name, GameMap location, ArrayList<Item> inventory) {
        this.name = name;
        this.location = location;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public GameMap getLocation() {
        return location;
    };

    public abstract void setLocation(GameMap location);

    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
