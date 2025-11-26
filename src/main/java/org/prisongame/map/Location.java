package org.prisongame.map;

import java.util.ArrayList;

public enum Location {
    //Floor 1
    CELL_BLOCK,
        //Cells
        CELL1,
        CELL2,
        CELL3,
        CELL4,
        CELL5,

    HALLWAY,
    GUARDS_QUARTERS,
    STAIRS,
    YARD,
    SHOWERS,
    CANTEEN;

    public enum Floor {
        F1,
        F2,
        CellBlock
    }
}
