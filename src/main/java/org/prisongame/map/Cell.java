package org.prisongame.map;

import org.prisongame.items.Bedsheet;

public class Cell extends Room {

    private static int cellCount = 1;
    int cellNum;

    public Cell() {
        super("Cell-" + cellCount, null, Location.Floor.CellBlock);
        this.cellNum = cellCount;
        cellCount++;
        super.addItem(new Bedsheet());
        addExits(Location.CELL_BLOCK);
    }
}
