package org.prisongame.character;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.prisongame.game.GameMap;
import org.prisongame.terminal.Item;
import org.prisongame.terminal.Room;

public class Player extends Character{

    ObjectProperty<GameMap> location;
    final int INVENTORY_SIZE = 10;

    public Player(String name, GameMap location) {
        super(name);
        this.location = new SimpleObjectProperty<GameMap>(location);
    }

    @Override
    public GameMap getLocation() {
        return location.get();
    }

    public ObjectProperty<GameMap> getLocationNotifier() {
        return this.location;
    }

    @Override
    public void setLocation(GameMap location) {
        this.location.setValue(location);
    }

    public Room getCurrentRoom() {
        return getLocation().getRoom();
    }

    public String pickUpItem(Item item) {
        if (inventory.size() < INVENTORY_SIZE) {
            getLocation().getRoom().removeItem(item);
            inventory.add(item);
            return "Picked up " + item.getName() + "!";
        }
        return "My inventory is full!";
    }

    public void dropItem(Item item) {
        getCurrentRoom().addItem(item);
        inventory.remove(item);
        System.out.println("Placed " + item.getName() + "!");
    }
}
