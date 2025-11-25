package org.prisongame.ui;

import javafx.scene.control.TextArea;
import org.prisongame.items.Item;


import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    TextArea invetoryLabel;
    ArrayList<Item> inventory;

    public InventoryController(TextArea inventoryLabel) {
        this.invetoryLabel = inventoryLabel;
        this.inventory = new ArrayList<>();
    }

    public InventoryController(TextArea inventoryLabel, ArrayList<Item> inventory) {
        this.invetoryLabel = inventoryLabel;
        this.inventory = inventory;
        generateLabel();
    }

    public void addItem(Item item) {
        inventory.add(item);
        invetoryLabel.appendText(item.getName() + "\n");
    }

    public void removeItem(Item item) {
        inventory.remove(item);
        generateLabel();
    }

    public void addItems(List<Item> items) {
        for (Item item : items) {
            addItem(item);
        }
    }

    public void removeItems(List<Item> items) {
        for (Item item : items) {
            inventory.remove(item);
        }
        generateLabel();
    }

    private void generateLabel() {
        StringBuilder newLabel = new StringBuilder();
        for(Item item : inventory) {
            newLabel.append(item.getName()).append("\n");
        }
        invetoryLabel.setText(newLabel.toString());
    }
}
