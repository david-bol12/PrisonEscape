package org.prisongame.ui;

public interface Output {

    void print(String text);
    void println(String text);
    void clear();
    void printCommand(String text);
}
