package org.prisongame.character;

import org.prisongame.map.Location;
import org.prisongame.items.Item;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Character implements Serializable {
    protected String name;
    protected ArrayList<Item> inventory;
    protected Location location;

    public Character(String name, Location location) {
        this.name = name;
        this.location = location;
        this.inventory = new ArrayList<Item>();
    }

    public Character(String name, Location location, ArrayList<Item> inventory) {
        this.name = name;
        this.location = location;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    };

    public abstract void setLocation(Location location);

    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
