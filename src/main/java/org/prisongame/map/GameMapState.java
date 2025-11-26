package org.prisongame.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMapState implements Serializable {
    private static Map<Location, Room> locations = null;
    private static final ArrayList<Location> f1 = new ArrayList<Location>();
    private static final ArrayList<Location> f2 = new ArrayList<Location>();
    private static final ArrayList<Location> cells = new ArrayList<Location>();

    public static void createMapState() {
        System.out.println("made new state");
        locations = new HashMap<Location, Room>();
        locations.put(Location.CELL_BLOCK, new Room("Cell-Block", "I see five cells I can enter", Location.Floor.F1));
            // Cells
            locations.put(Location.CELL1, new Cell());
            locations.put(Location.CELL2, new Cell());
            locations.put(Location.CELL3, new Cell());
            locations.put(Location.CELL4, new Cell());
            locations.put(Location.CELL5, new Cell());

        locations.put(Location.HALLWAY, new Room("Hallway", null, Location.Floor.F1));
        locations.put(Location.GUARDS_QUARTERS, new Room("Guards-Quarters", null, Location.Floor.F1));
        locations.put(Location.CANTEEN, new Room("Canteen", "Menu: \n Cookie - $5", Location.Floor.F1));
        locations.put(Location.STAIRS, new Room("Stairs", null, Location.Floor.F1));
        locations.put(Location.YARD, new Room("Yard", null, Location.Floor.F1));
        locations.put(Location.SHOWERS, new Room("Showers", null, Location.Floor.F1));

        initFloors();

        getRoom(Location.CELL_BLOCK).setExits(new ArrayList<Location>(cells));
        for (Location loc : cells) {
            getRoom(loc).addExits(getCells().toArray(Location[]::new));
        }

        for (Location loc : f1) {
            getRoom(loc).addExits(Location.HALLWAY);
        }

        getRoom(Location.HALLWAY).setExits(f1);
        getRoom(Location.HALLWAY).removeExits(Location.HALLWAY);
    }

    public static Room getRoom(Location loc) {
        return locations.get(loc);
    }

    public static ArrayList<Location> getCells() {
        return cells;
    }

    public static ArrayList<Location> getF1() {
        return f1;
    }

    public static ArrayList<Location> getF2() {
        return f2;
    }

    private static void initFloors() {
        for(Location loc : Location.values()) {
            switch (getRoom(loc).getFloor()) {
                case F1 -> f1.add(loc);
                case F2 -> f2.add(loc);
                case CellBlock -> cells.add(loc);
            }
        }
    }

    public static Map<Location, Room> getLocations() {
        return locations;
    }

    public static void setState(Map<Location, Room> state) {
        GameMapState.locations = state;
        initFloors();
    }
}
