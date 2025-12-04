package org.prisongame.character;

import org.prisongame.commands.Command;
import org.prisongame.game.Game;
import org.prisongame.map.Location;
import org.prisongame.minigame.BlackjackGame;

public class BlackjackNPC extends NPC implements Processer{
    public BlackjackNPC() {
        super("Luigi", Location.YARD);
    }

    @Override
    public void talk() {
        if (Game.getPlayer().getMoney() == 0) {
            Game.getPlayer().setMoney(10);
            Game.getOutput().println("Hey you're looking pretty low on money \nLuigi: Here take this... You look like you need it...");
        }
        Game.setSubProcess(this);
    }

    @Override
    public Processer processCommand(Command command) {
        return switch (command.getCommandWord()) {
            case "yes" -> new BlackjackGame(Game.getOutput(), Game.getInput(), Game.getPlayer());
            case "no" -> {
                Game.getOutput().println("Luigi: Oh ok bye then");
                yield null;
            }
            default -> {
                Game.getOutput().println("Please enter 'yes' or 'no'");
                yield this;
            }
        };
    }
}
