package org.prisongame.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeScreenController {
    @FXML
    TextArea terminalOut;
    @FXML
    TextField terminalIn;

    public TextArea getTerminalOut() {
        return terminalOut;
    }
}
