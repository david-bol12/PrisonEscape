package org.prisongame.ui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.prisongame.items.Item;
import org.prisongame.map.Location;

import java.util.ArrayList;

public class PlayerUIController {


    private transient ObjectProperty<Location> locationNotifier;
    private transient ObservableList<Item> inventoryNotifier;
    private transient ObjectProperty<Integer> energyNotifier;
    private transient ObjectProperty<Integer> intellectNotifier;
    private transient ObjectProperty<Integer> strengthNotifier;
    private transient ObjectProperty<Integer> moneyNotifier;

    public PlayerUIController(Location location, ArrayList<Item> inventory, int energy, int intellect, int strength, int money) {
        this.locationNotifier = new SimpleObjectProperty<Location>(location);
        this.inventoryNotifier = FXCollections.observableArrayList(inventory);
        this.energyNotifier = new SimpleObjectProperty<Integer>(energy);
        this.intellectNotifier = new SimpleObjectProperty<Integer>(intellect);
        this.strengthNotifier = new SimpleObjectProperty<Integer>(strength);
        this.moneyNotifier = new SimpleObjectProperty<Integer>(money);
    }

    public ObjectProperty<Location> getLocationNotifier() {
        return locationNotifier;
    }

    public ObservableList<Item> getInventoryNotifier() {
        return inventoryNotifier;
    }

    public ObjectProperty<Integer> getEnergyNotifier() {
        return energyNotifier;
    }

    public ObjectProperty<Integer> getIntellectNotifier() {
        return intellectNotifier;
    }

    public ObjectProperty<Integer> getStrengthNotifier() {
        return strengthNotifier;
    }

    public ObjectProperty<Integer> getMoneyNotifier() {
        return moneyNotifier;
    }

    public void setLocationNotifier(Location locationNotifier) {
        this.locationNotifier.set(locationNotifier);
    }

    public void setEnergyNotifier(Integer energyNotifier) {
        this.energyNotifier.set(energyNotifier);
    }

    public void setIntellectNotifier(Integer intellectNotifier) {
        this.intellectNotifier.set(intellectNotifier);
    }

    public void setStrengthNotifier(Integer strengthNotifier) {
        this.strengthNotifier.set(strengthNotifier);
    }

    public void setMoneyNotifier(Integer moneyNotifier) {
        this.moneyNotifier.set(moneyNotifier);
    }
}
