package org.prisongame.minigame;

import org.prisongame.character.Player;
import org.prisongame.commands.Command;
import org.prisongame.ui.Output;

public abstract class Minigame {

    protected Output output;
    protected Player player;

    public Minigame(Output output, Player player) {
        this.output = output;
        this.player = player;
    }

    public abstract Minigame processCommand(Command command);
}
