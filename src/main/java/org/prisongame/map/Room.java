package org.prisongame.map;

import org.prisongame.character.NPC;
import org.prisongame.items.Item;
import org.prisongame.utils.Container;
import org.prisongame.utils.ContainerObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {

    private String roomDescription = null;
    private String onEnterMessage;
    private Container<Item> items = new Container<>();
    private Container<NPC> npcs = new Container<>();
    private String name;
    final Location.Floor floor;
    private boolean locked = false;
    private ArrayList<Location> exits = new ArrayList<Location>();

    public Room(String name, String onEnterMessage, Location.Floor floor) {
        this.name = name;
        this.floor = floor;
        this.onEnterMessage = onEnterMessage;
    }

    public Room(String name, String onEnterMessage, Location.Floor floor, List<NPC> npcs) {
        this.name = name;
        this.floor = floor;
        this.onEnterMessage = onEnterMessage;
        this.npcs = new Container<>(npcs);
    }

    public Room(String name, String onEnterMessage, Location.Floor floor, boolean locked) {
        this.name = name;
        this.floor = floor;
        this.onEnterMessage = onEnterMessage;
        this.locked = locked;
    }

    public String getOnEnterMessage() {
        return this.onEnterMessage;
    }

    public String searchRoom() {

        StringBuilder itemsList = new StringBuilder();
        StringBuilder npcsList = new StringBuilder();
        if (getItemsList().isEmpty() && getNpcsList().isEmpty()) {
            return "This room is empty!";
        }

        if (getItemsList().size() == 1) {
             itemsList.append("I see a ").append(getItemsList().getFirst().getName()).append("! \n");
        } else if (!getItemsList().isEmpty()) {
            itemsList.append("I see ");
            for(int item = 0; item < getItemsList().size() - 1; item++) {
                itemsList.append("a ");
                itemsList.append(getItemsList().get(item).getName());
                itemsList.append(", ");
            }
            itemsList.append("and a ");
            itemsList.append(getItemsList().getLast().getName());
            itemsList.append(". \n");
        }


        if (getNpcsList().size() == 1) {
            npcsList.append(getNpcsList().getFirst().getName()).append(" is in this room!");
        } else if (!getNpcsList().isEmpty()){
            for(int npc = 0; npc < getNpcsList().size() - 1; npc++) {
                npcsList.append(getNpcsList().get(npc).getName());
                npcsList.append(", ");
            }
            npcsList.append("and ");
            npcsList.append(getNpcsList().getLast().getName());
            npcsList.append("are in this room.");
        }


        return itemsList.toString() + npcsList;
    }

    public void removeItem(Item item) {
        items.getObjects().remove(item);
    }

    public void addItem(Item item) {
        items.getObjects().add(item);
    }

    public ArrayList<Item> getItemsList() {
        return items.getObjects();
    }

    public ArrayList<NPC> getNpcsList() {
        return npcs.getObjects();
    }

    public String getName() {
        return name;
    }

    public void addNpc(NPC npc) {
        npcs.getObjects().add(npc);
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
        this.items.getObjects().addAll(List.of(items));
    }

    public Container<NPC> getNpcs() {
        return npcs;
    }
    public Container<Item> getItems() {
        return items;
    }
}
