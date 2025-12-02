package org.prisongame.map;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        locations.put(Location.HALLWAY_F1, new Room("Hallway", null, Location.Floor.F1));
        locations.put(Location.GUARDS_QUARTERS, new Room("Guards-Quarters", null, Location.Floor.F1));
        locations.put(Location.CANTEEN, new Room("Canteen", "Menu: \n Cookie - $5", Location.Floor.F1));
        locations.put(Location.STAIRS_F1, new Room("Stairs", null, Location.Floor.F2));
        locations.put(Location.YARD, new Room("Yard", null, Location.Floor.F1));
        locations.put(Location.SHOWERS, new Room("Showers", null, Location.Floor.F1));

        locations.put(Location.HALLWAY_F2, new Room("Hallway", null, Location.Floor.F2));
        locations.put(Location.STAIRS_F2, new Room("Stairs", null, Location.Floor.F1));
        locations.put(Location.GYM, new Room("Gym", null, Location.Floor.F2));
        locations.put(Location.COMMON_AREA, new Room("Common-Area", null, Location.Floor.F2));
        locations.put(Location.LIBRARY, new Room("Library", null, Location.Floor.F2));
        locations.put(Location.GUARD_LOOKOUT_PLATFORM, new Room("Guard-Lookout-Platform", null, Location.Floor.F2, true));


        initFloors();

        getRoom(Location.CELL_BLOCK).setExits(new ArrayList<Location>(cells));
        for (Location loc : cells) {
            getRoom(loc).addExits(getCells().toArray(Location[]::new));
        }

        for (Location loc : f1) {
            getRoom(loc).addExits(Location.HALLWAY_F1);
        }

        for (Location loc : f2) {
            getRoom(loc).addExits(Location.HALLWAY_F2);
        }

        getRoom(Location.HALLWAY_F1).setExits(f1);
        getRoom(Location.HALLWAY_F1).removeExits(Location.HALLWAY_F1);
        getRoom(Location.HALLWAY_F2).setExits(f2);
        getRoom(Location.HALLWAY_F2).removeExits(Location.HALLWAY_F2);
        getRoom(Location.STAIRS_F1).setExits(new ArrayList<>(List.of(Location.HALLWAY_F2)));
        getRoom(Location.STAIRS_F2).setExits(new ArrayList<>(List.of(Location.HALLWAY_F1)));

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
        f1.remove(Location.STAIRS_F2);
        f1.add(Location.STAIRS_F1);
        f2.remove(Location.STAIRS_F1);
        f2.add(Location.STAIRS_F2);
    }

    public static Map<Location, Room> getLocations() {
        return locations;
    }

    public static void setState(Map<Location, Room> state) {
        GameMapState.locations = state;
        initFloors();
    }
}
