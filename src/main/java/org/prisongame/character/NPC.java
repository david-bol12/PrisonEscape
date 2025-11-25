package org.prisongame.character;

import org.prisongame.map.GameMap;
import org.prisongame.items.Item;

import java.util.ArrayList;

public class NPC extends Character {

    public NPC(String name, GameMap location) {
        super(name, location);
    }

    public NPC(String name, GameMap location, ArrayList<Item> inventory) {
        super(name, location, inventory);
    }

    @Override
    public void setLocation(GameMap location) {
        this.location = location;
        location.getRoom().addNpc(this);
    }
}
