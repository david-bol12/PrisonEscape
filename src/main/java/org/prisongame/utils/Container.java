package org.prisongame.utils;

import org.prisongame.commands.Parser;
import org.prisongame.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Container<T extends ContainerObject> {
    ArrayList<T> objects;

    public Container(ArrayList<T> objects) {
        this.objects = objects;
    }

    public Container() {
        this.objects = new ArrayList<>();
    }

    public Container(List<T> objects) {
        this.objects = new ArrayList<>(objects);
    }

    public T checkObjectAvailable(String name) {
        for (T object : objects) {
            Parser.removeSpaces(object.getName());
            if (Parser.removeSpaces(object.getName()).equalsIgnoreCase(name)) {
                return object;
            }
        }
        return null;
    }

    public ArrayList<T> getObjects() {
        return objects;
    }
}
