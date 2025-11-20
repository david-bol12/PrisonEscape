package org.prisongame.game;

import org.prisongame.terminal.Room;

public enum GameMap {
    TEST(new Room("test")),
    CELL_BLOCK(new Room("Cell Block")),
    HALLWAY(new Room("Hallway")),
    GUARDS_QUARTERS(new Room("Guards Quarters")),
    STAIRS(new Room("Stairs")),
    YARD(new Room("Yard")),
    SHOWERS(new Room("Showers")),
    CANTEEN(new Room("Canteen"));


    private final Room room;

    GameMap(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
