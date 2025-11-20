package org.prisongame.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HomeScreenController {

    @FXML
    public ImageView avatar;
    @FXML
    public ImageView map;
    @FXML
    public TextArea terminalOut;
    @FXML
    public TextField terminalIn;

    public TextArea getTerminalOut() {
        return terminalOut;
    }

    public ImageView getAvatar() {
        return avatar;
    }
}
