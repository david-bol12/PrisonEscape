package org.prisongame.map;

import org.prisongame.items.Item;

import java.util.ArrayList;

public enum GameMap {
        CELL_BLOCK(new Room("Cell-Block", Floor.F1)),
        HALLWAY(new Room("Hallway", Floor.F1)),
        GUARDS_QUARTERS(new Room("Guards-Quarters", Floor.F1)),
        STAIRS(new Room("Stairs", Floor.F1)),
        YARD(new Room("Yard", Floor.F1)),
        SHOWERS(new Room("Showers", Floor.F1)),
        CANTEEN(new Room("Canteen", Floor.F1));

        public enum Floor {
            F1,
            F2
        }

    private final Room room;
    private static final ArrayList<GameMap> f1 = new ArrayList<GameMap>();
    private static final ArrayList<GameMap> f2 = new ArrayList<GameMap>();


    static {
        for(GameMap loc : GameMap.values()) {
            if (loc.getRoom().floor == Floor.F1) {
                f1.add(loc);
            } else {
                f2.add(loc);
            }
        }

        for (GameMap loc : f1) {
            loc.getRoom().addExits(GameMap.HALLWAY);
        }

        GameMap.HALLWAY.getRoom().setExits(f1);
        GameMap.HALLWAY.getRoom().removeExits(GameMap.HALLWAY);

        GameMap.CELL_BLOCK.getRoom().addItem(new Item("Sword", "very sharp"));
    }

    GameMap(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public static ArrayList<GameMap> getF1() {
        return f1;
    }

    public static ArrayList<GameMap> getF2() {
        return f2;
    }
}
