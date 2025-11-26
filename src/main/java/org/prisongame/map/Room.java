package org.prisongame.map;

import org.prisongame.character.NPC;
import org.prisongame.items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {

    private final String[] STANDARD_COMMANDS = new String[] {
            "exit",
            ""
    };

    private String description; // Map direction to neighboring Room
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<NPC> npcs;
    private String name;
    final Location.Floor floor;
    private boolean locked = false;
    private ArrayList<String> validCommands;
    private ArrayList<Location> exits = new ArrayList<Location>();

    public Room(String name, String description, Location.Floor floor, ArrayList<String> validCommands) {
        this.name = name;
        this.description = description;
        this.floor = floor;
        this.validCommands = validCommands;
        this.npcs = new ArrayList<NPC>();
    }

    public Room(String name, String description, ArrayList<NPC> npcs, Location.Floor floor, ArrayList<String> validCommands) {
        this.name = name;
        this.description = description;
        this.npcs = npcs;
        this.floor = floor;
        this.validCommands = validCommands;
    }

    public Room(String name, String description, Location.Floor floor) {
        this.name = name;
        this.floor = floor;
        this.validCommands = new ArrayList<String>(List.of(STANDARD_COMMANDS));
        this.description = description;
        this.items = new ArrayList<Item>();
    }

    public String getDescription() {
        return description;
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
        return "You are " + description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    public String getName() {
        return name;
    }

    public void addNpc(NPC npc) {
        npcs.add(npc);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Location.Floor getFloor() {
        return floor;
    }

    public ArrayList<Location> getExits() {
        return exits;
    }

    public void setExits(ArrayList<Location> exits) {
        this.exits = exits;
    }

    public void addExits(Location... exits) {
        this.exits.addAll(List.of(exits));
    }

    public void removeExits(Location... exits) {
        this.exits.removeAll(List.of(exits));
    }

    public void addItems(Item... items) {
        this.items.addAll(List.of(items));
    }
}
