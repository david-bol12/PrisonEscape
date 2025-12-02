package org.prisongame.character;

import org.prisongame.items.Cookie;
import org.prisongame.items.Eatable;
import org.prisongame.map.GameMapState;
import org.prisongame.map.Location;
import org.prisongame.items.Item;
import org.prisongame.map.Room;
import org.prisongame.ui.PlayerUIController;

import java.util.ArrayList;
import java.util.Objects;

public class Player extends Character{

    private transient PlayerUIController playerUIController;
    final int INVENTORY_SIZE = 10;
    private NPC currentConversation = null;
    int energy = 100;
    int intellect = 0;
    int strength = 0;
    int money = 20;

    public Player(String name, Location location) {
        super(name, location);
        playerUIController = new PlayerUIController(location, inventory, energy, intellect, strength, money);
        addToInventory(new Cookie());
    }

    public Player(String name, Location location, ArrayList<Item> inventory) {
        super(name, location, inventory);
        playerUIController = new PlayerUIController(location, inventory, energy, intellect, strength, money);
    }

    public Player(String name, Location location, ArrayList<Item> inventory, Integer energy, Integer intellect, Integer strength, Integer money) {
        super(name, location, inventory);
        playerUIController = new PlayerUIController(location, inventory, energy, intellect, strength, money);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
        playerUIController.setLocationNotifier(this.location);

    }

    public Room getCurrentRoom() {
        return GameMapState.getRoom(location);
    }

    public void addToInventory(Item item) {
        inventory.add(item);
        playerUIController.getInventoryNotifier().add(item);
    }

    public void removeFromInvetory(Item item) {
        inventory.remove(item);
        playerUIController.getInventoryNotifier().remove(item);
    }

    public int getEnergy() {
        return energy;
    }

    public int getIntellect() {
        return intellect;
    }

    public int getStrength() {
        return strength;
    }

    public int getMoney() {
        return money;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        playerUIController.setEnergyNotifier(energy);
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
        playerUIController.setIntellectNotifier(intellect);
    }

    public void setMoney(int money) {
        this.money = money;
        playerUIController.setMoneyNotifier(money);
    }

    public boolean spendMoney(int amnt) {
        if (money >= amnt) {
            setMoney(money - amnt);
            return true;
        }
        return false;
    }

    public void setStrength(int strength) {
        this.strength = strength;
        playerUIController.setStrengthNotifier(strength);
    }

    public PlayerUIController getPlayerUIController() {
        return playerUIController;
    }

    public void initPlayerUIController () {
        playerUIController = new PlayerUIController(location, inventory, energy, intellect, strength, money);
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

    public boolean useEnergy(int amnt) {
        if (energy >= amnt) {
            setEnergy(energy - amnt);
            return true;
        }
        return false;
    }

    public int addIntellect(int amnt) {
        if (intellect == 100) {
            return 0;
        }
        if (intellect + amnt < 100) {
            setIntellect(intellect + amnt);
            return amnt;
        }
        setIntellect(100);
        return 100 - intellect;
    }

    public int addStrength(int amnt) {
        if (strength == 100) {
            return 0;
        }
        if (strength + amnt < 100) {
            setStrength(strength + amnt);
            return amnt;
        }
        setStrength(100);
        return 100 - strength;
    }

    // Commands

    public String pickUpItem(Item item) {
        if (inventory.size() < INVENTORY_SIZE) {
            getCurrentRoom().removeItem(item);
            addToInventory(item);
            return "Picked up " + item.getName() + "!";
        }
        return "My inventory is full!";
    }

    public String dropItem(String itemName) {
        Item dropItem = Item.checkItemAvailable(itemName, getInventory());
        if (dropItem == null) {
            return "I don't have that item!";
        }
        getCurrentRoom().addItem(dropItem);
        removeFromInvetory(dropItem);
        return "Placed " + dropItem.getName() + "!";
    }

    public String go(String locName) {
        if (locName == null) {
            return "Go where?";
        }
        for (Location loc : Location.values()) {
            if (Objects.equals(locName, GameMapState.getRoom(loc).getName().toLowerCase())) {
                if (getCurrentRoom().getExits().contains(loc) && !GameMapState.getRoom(loc).isLocked()) {
                    setLocation(loc);
                    if (GameMapState.getRoom(loc).getOnEnterMessage() != null) {
                        return String.format(
                                "Entered: %s \n" +
                                        "%s",
                                GameMapState.getRoom(loc).getName(),
                                GameMapState.getRoom(loc).getOnEnterMessage()
                        );
                    }
                    return "Entered: " + GameMapState.getRoom(loc).getName();
                }
                if (GameMapState.getRoom(loc).isLocked()) {
                    return "This door is locked! I think i'll need a keycard to get in...";
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

    public String study() {
        if (location == Location.LIBRARY) {
            if (useEnergy(20)) {
                addIntellect(5);
                return "Gained 5 Intellect!";
            }
            return "I'm too tired to do that \n" +
                    "Maybe some food could help...";
        }
        return "I can't do that here!";
    }

    public String exercise() {
        if (location == Location.GYM) {
            if (useEnergy(20)) {
                addStrength(5);
                return "Gained 5 Strength!";
            }
            return "I'm too tired to do that \n" +
                    "Maybe some food could help...";
        }
        return "I can't do that here!";
    }
}
