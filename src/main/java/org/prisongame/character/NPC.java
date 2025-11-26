package org.prisongame.character;
import org.prisongame.map.GameMapState;
import org.prisongame.map.Location;
import org.prisongame.items.Item;

import java.util.ArrayList;

public class NPC extends Character {

    public NPC(String name, Location location) {
        super(name, location);
    }

    public NPC(String name, Location location, ArrayList<Item> inventory) {
        super(name, location, inventory);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        GameMapState.getRoom(location);
    }
}
