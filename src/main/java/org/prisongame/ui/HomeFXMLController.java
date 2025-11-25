package org.prisongame.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class HomeFXMLController {

    @FXML
    public ImageView avatar;
    public ImageView map;
    public TextArea terminalOut;
    public TextField terminalIn;
    @FXML
    public TextArea inventoryLabel;
    @FXML
    public Label strengthBarLabel;
    public ProgressBar strengthBar;
    @FXML
    public Label energyBarLabel;
    public ProgressBar energyBar;
    public ProgressBar intellectBar;
    @FXML
    public Label intellectBarLabel;
    public Label inventoryLabel1;

    public String getEnergyBarLabel() {
        inventoryLabel1.textProperty().setValue("hello");
        return energyBarLabel.getText();
    }
}
