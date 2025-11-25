package org.prisongame.map;

import org.prisongame.items.Item;

import java.util.ArrayList;

public class Cell extends Room {

    int cellNum;

    public Cell(int cellNum, ArrayList<Item> items) {
        super("Cell-" + cellNum, null, GameMap.Floor.F1, items);
    }
}
