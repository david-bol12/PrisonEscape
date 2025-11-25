package org.prisongame.character;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.prisongame.items.Eatable;
import org.prisongame.map.GameMap;
import org.prisongame.items.Item;
import org.prisongame.map.Room;

import java.util.ArrayList;
import java.util.Objects;

public class Player extends Character{

    private final ObjectProperty<GameMap> locationNotifier;
    private final ObservableList<Item> inventoryNotifier;
    private final ObjectProperty<Integer> energy;
    private final ObjectProperty<Integer> intellect;
    private final ObjectProperty<Integer> strength;
    private final ObjectProperty<Integer> money;
    final int INVENTORY_SIZE = 10;

    public Player(String name, GameMap location) {
        super(name, location);
        this.locationNotifier = new SimpleObjectProperty<GameMap>(location);
        this.inventoryNotifier = FXCollections.observableArrayList();
        this.energy = new SimpleObjectProperty<Integer>(100);
        this.intellect = new SimpleObjectProperty<Integer>(0);
        this.strength = new SimpleObjectProperty<Integer>(0);
        this.money = new SimpleObjectProperty<Integer>(0);
    }

    public Player(String name, GameMap location, ArrayList<Item> inventory) {
        super(name, location, inventory);
        this.locationNotifier = new SimpleObjectProperty<GameMap>(location);
        this.inventoryNotifier = FXCollections.observableArrayList(inventory);
        this.energy = new SimpleObjectProperty<Integer>(50);
        this.intellect = new SimpleObjectProperty<Integer>(10);
        this.strength = new SimpleObjectProperty<Integer>(0);
        this.money = new SimpleObjectProperty<Integer>(0);
    }

    public Player(String name, GameMap location, ArrayList<Item> inventory, Integer energy, Integer intellect, Integer strength, Integer money) {
        super(name, location, inventory);
        this.locationNotifier = new SimpleObjectProperty<GameMap>(location);
        this.inventoryNotifier = FXCollections.observableArrayList(inventory);
        this.energy = new SimpleObjectProperty<Integer>(energy);
        this.intellect = new SimpleObjectProperty<Integer>(intellect);
        this.strength = new SimpleObjectProperty<Integer>(strength);
        this.money = new SimpleObjectProperty<Integer>(money);
    }

    public ObjectProperty<GameMap> getLocationNotifier() {
        return this.locationNotifier;
    }

    @Override
    public void setLocation(GameMap location) {
        this.location = location;
        this.locationNotifier.setValue(this.location);
    }

    public Room getCurrentRoom() {
        return getLocation().getRoom();
    }

    public void addToInventory(Item item) {
        inventory.add(item);
        inventoryNotifier.add(item);
    }

    public void removeFromInvetory(Item item) {
        inventory.remove(item);
        inventoryNotifier.remove(item);
    }

    public ObservableList<Item> getInventoryNotifier() {
        return inventoryNotifier;
    }

    public Integer getEnergy() {
        return energy.get();
    }

    public Integer getIntellect() {
        return intellect.get();
    }

    public Integer getStrength() {
        return strength.get();
    }

    public Integer getMoney() {
        return money.get();
    }

    public void setEnergy(Integer energy) {
        this.energy.set(energy);
    }

    public void setIntellect(Integer intellect) {
        this.intellect.set(intellect);
    }

    public void setMoney(Integer money) {
        this.money.set(money);
    }

    public void setStrength(Integer strength) {
        this.strength.set(strength);
    }

    public ObjectProperty<Integer> moneyProperty() {
        return money;
    }

    public ObjectProperty<Integer> strengthProperty() {
        return strength;
    }

    public ObjectProperty<Integer> intellectProperty() {
        return intellect;
    }

    public ObjectProperty<Integer> energyProperty() {
        return energy;
    }

    public int addEnergy(int amnt) {
        if (getEnergy() == 100) {
            return 0;
        }
        if (getEnergy() + amnt > 100) {
            int oldEnergy = getEnergy();
            setEnergy(100);
            return 100 - oldEnergy;
        }
        setEnergy(getEnergy() + amnt);
        return amnt;
    }

    // Commands

    public String pickUpItem(Item item) {
        if (inventory.size() < INVENTORY_SIZE) {
            getLocation().getRoom().removeItem(item);
            addToInventory(item);
            return "Picked up " + item.getName() + "!";
        }
        return "My inventory is full!";
    }

    public String dropItem(Item item) {
        getCurrentRoom().addItem(item);
        removeFromInvetory(item);
        return "Placed " + item.getName() + "!";
    }

    public String go(String locName) {
        if (locName == null) {
            return "Go where?";
        }
        for (GameMap loc : GameMap.values()) {
            if (Objects.equals(locName, loc.getRoom().getName().toLowerCase())) {
                if (getCurrentRoom().getExits().contains(loc) && !loc.getRoom().isLocked()) {
                    setLocation(loc);
                    return "Entered " + loc.getRoom().getName();
                }
            }
        }
        return "I can't go there!";
    }

    public String eat(String itemName) {
        if (itemName == null) {
            return "Eat what?";
        }
        Item item = Item.checkItemAvailable(itemName, getInventory());
        if (item != null) {
            if (item instanceof Eatable) {
                int energyInc = addEnergy(((Eatable) item).eat());
                if (energyInc == 0) {
                    return "Already at max energy";
                } else {
                    removeFromInvetory(item);
                    return String.format(
                            "Ate %s! \n" +
                            "Gained %d energy",
                            item.getName(),
                            energyInc
                    );
                }
            }
        }
        return "I can't eat that!";
    }
}
