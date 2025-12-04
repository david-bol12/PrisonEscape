package org.prisongame.minigame;

import org.prisongame.character.Player;
import org.prisongame.character.Processer;
import org.prisongame.commands.Command;
import org.prisongame.ui.Input;
import org.prisongame.ui.Output;

public abstract class Minigame implements Processer {

    protected Output output;
    protected Input input;
    protected Player player;

    public Minigame(Output output, Input input, Player player) {
        this.output = output;
        this.player = player;
        this.input = input;
    }

    @Override
    public abstract Processer processCommand(Command command) throws InterruptedException;
}
