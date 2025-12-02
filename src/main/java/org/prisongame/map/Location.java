package org.prisongame.map;

public enum Location {
    //Floor 1
    CELL_BLOCK,
        //Cells
        CELL1,
        CELL2,
        CELL3,
        CELL4,
        CELL5,

    HALLWAY_F1,
    GUARDS_QUARTERS,
    STAIRS_F1,
    YARD,
    SHOWERS,
    CANTEEN,

    HALLWAY_F2,
    STAIRS_F2,
    GYM,
    COMMON_AREA,
    LIBRARY,
    GUARD_LOOKOUT_PLATFORM;

    public enum Floor {
        F1,
        F2,
        CellBlock
    }
}
