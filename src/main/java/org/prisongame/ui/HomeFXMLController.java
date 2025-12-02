package org.prisongame.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.RowConstraints;

public class HomeFXMLController {

    @FXML
    public ImageView avatar;
    public ImageView map;
    public TextArea terminalOut;
    public TextField terminalIn;
    public TextArea inventoryLabel;
    public Label strengthBarLabel;
    public ProgressBar strengthBar;
    public Label energyBarLabel;
    public ProgressBar energyBar;
    public ProgressBar intellectBar;
    public Label intellectBarLabel;
    public Label moneyLabel;
    public RowConstraints mapConstraints;
    public RowConstraints terminalConstraints;
}
