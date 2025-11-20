package org.prisongame.character;

import org.prisongame.game.GameMap;

public class NPC extends Character {
    protected GameMap location;

    public NPC(String name, GameMap location) {
        super(name);
        this.location = location;
    }

    @Override
    public void setLocation(GameMap location) {
        this.location = location;
        location.getRoom().addNpc(this);
    }

    @Override
    public GameMap getLocation() {
        return location;
    }
}
