package org.prisongame.ui.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GameGUIController {
    @FXML
    TextArea terminalOut;
    @FXML
    TextField terminalIn;

    public TextArea getTerminalOut() {
        return terminalOut;
    }
}
