package org.prisongame.map;

import org.prisongame.character.NPC;
import org.prisongame.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final String[] STANDARD_COMMANDS = new String[] {
            "exit",
            ""
    };

    private String description; // Map direction to neighboring Room
    private ArrayList<Item> items;
    private ArrayList<NPC> npcs;
    private String name;
    final GameMap.Floor floor;
    private boolean locked = false;
    private ArrayList<String> validCommands;
    private ArrayList<GameMap> exits = new ArrayList<GameMap>();

    public Room(String name, String description, ArrayList<Item> items, GameMap.Floor floor, ArrayList<String> validCommands) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.floor = floor;
        this.validCommands = validCommands;
        this.npcs = new ArrayList<NPC>();
    }

    public Room(String name, String description, ArrayList<Item> items, ArrayList<NPC> npcs, GameMap.Floor floor, ArrayList<String> validCommands) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.npcs = npcs;
        this.floor = floor;
        this.validCommands = validCommands;
    }

    public Room(String name, String description, GameMap.Floor floor, ArrayList<Item> items) {
        this.description = description;
        this.floor = floor;
        this.validCommands = new ArrayList<String>(List.of(STANDARD_COMMANDS));
        this.items = new ArrayList<Item>();
    }

    public Room(String name, GameMap.Floor floor) {
        this.name = name;
        this.floor = floor;
        this.validCommands = new ArrayList<String>(List.of(STANDARD_COMMANDS));
        this.description = null;
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

    public GameMap.Floor getFloor() {
        return floor;
    }

    public ArrayList<GameMap> getExits() {
        return exits;
    }

    public void setExits(ArrayList<GameMap> exits) {
        this.exits = exits;
    }

    public void addExits(GameMap... exits) {
        this.exits.addAll(List.of(exits));
    }

    public void removeExits(GameMap... exits) {
        this.exits.removeAll(List.of(exits));
    }

    public void addItems(Item... items) {
        this.items.addAll(List.of(items));
    }
}
