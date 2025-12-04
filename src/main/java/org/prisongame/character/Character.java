package org.prisongame.character;

import org.prisongame.map.Location;
import org.prisongame.items.Item;
import org.prisongame.utils.Container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Character implements Serializable {
    protected String name;
    private Container<Item> inventory = new Container<>();
    protected Location location;

    public Character(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Character(String name, Location location, List<Item> inventory) {
        this.name = name;
        this.location = location;
        this.inventory = new Container<>(inventory);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    };

    public abstract void setLocation(Location location);

    public ArrayList<Item> getInventoryList() {
        return inventory.getObjects();
    }

    public Container<Item> getInventory() {
        return inventory;
    }

    public void removeFromInventory(Item item) {
        getInventoryList().remove(item);
    }
}
