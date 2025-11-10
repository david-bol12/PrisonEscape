package org.prisongame.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GameUIController {
    @FXML
    TextArea terminalOut;
    @FXML
    TextField terminalIn;

    public TextArea getTerminalOut() {
        return terminalOut;
    }
}
