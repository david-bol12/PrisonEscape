package org.prisongame.terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String description;
    private Map<String, Room> exits; // Map direction to neighboring Room
    private ArrayList<Item> items;

    public Room(String description, ArrayList<Item> items) {
        this.description = description;
        this.items = items;
        exits = new HashMap<>();
    }

    public Room(String description) {
        this.description = description;
        this.items = new ArrayList<Item>();
        exits = new HashMap<>();
    }

    public String getDescription() {
        return description;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (String direction : exits.keySet()) {
            sb.append(direction).append(" ");
        }
        return sb.toString().trim();
    }

    public String searchRoom() {
        StringBuilder itemsList = new StringBuilder();
        if (getItems().isEmpty()) {
            return "This room is empty!";
        }
        if (getItems().size() == 1) {
            return "I see a " + getItems().getFirst().getName() + "!";
        }
        itemsList.append("I see ");
        for(int item = 0; item < getItems().size() - 1; item++) {
            itemsList.append("a ");
            itemsList.append(getItems().get(item).getName());
            itemsList.append(", ");
        }
        itemsList.append("and a ");
        itemsList.append(getItems().getLast().getName());
        itemsList.append("!");
        return itemsList.toString();
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getLongDescription() {
        return "You are " + description + ".\nExits: " + getExitString();
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
