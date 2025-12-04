package org.prisongame.items;


import org.prisongame.map.Room;
import org.prisongame.utils.ContainerObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable, ContainerObject {
    private String description;
    private String name;
    private Room location;
    private int id;
    private boolean isVisible;
    private int value = 0;
    private boolean droppable = true;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
        this.isVisible = true;
    }

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.isVisible = true;
        this.value = value;
    }

    public Item(String name, String description, int value, boolean droppable) {
        this.name = name;
        this.description = description;
        this.isVisible = true;
        this.value = value;
        this.droppable = droppable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getValue() {
        return value;
    }

    public boolean isDroppable() {
        return droppable;
    }
}
