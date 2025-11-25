package org.prisongame.map;

import org.prisongame.items.Item;

import java.util.ArrayList;

public class CellBlock extends Room {

    private Cell[] cells = new Cell[10];

    public CellBlock(ArrayList<Item> items, ArrayList<String> validCommands) {
        super("Cell-Block", "A large area full of cells", items, GameMap.Floor.F1, validCommands);
        for(int i = 0; i < cells.length; i++) {
        }
    }
}
