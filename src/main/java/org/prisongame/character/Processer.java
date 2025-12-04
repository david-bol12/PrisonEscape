package org.prisongame.character;

import org.prisongame.commands.Command;

public interface Processer {
    Processer processCommand(Command command) throws InterruptedException;
}
