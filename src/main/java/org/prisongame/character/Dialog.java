package org.prisongame.character;


import org.prisongame.game.Game;
import org.prisongame.ui.Output;

import java.io.Serializable;
import java.util.Random;

public class Dialog implements Serializable {
    private final String[] dialogLines;
    private final DialogType dialogType;
    private int lineCounter = -1;
    public final int MONOLOGUE_DELAY = 2000;

    public Dialog(String[] dialogLines, DialogType dialogType) {
        this.dialogLines = dialogLines;
        this.dialogType = dialogType;
    }

    public String[] getDialogLines() {
        return dialogLines;
    }

    public DialogType getDialogType() {
        return dialogType;
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public void incrementLineCounter() {
        lineCounter++;
    }

    public enum DialogType {
        LINEAR,
        RANDOM,
        MONOLOGUE
    }

}

