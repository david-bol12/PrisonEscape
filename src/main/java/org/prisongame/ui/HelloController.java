package org.prisongame.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController {
    @FXML
    private ImageView image1;

    @FXML
    protected void onHelloButtonClick() {image1.setImage(new Image("@20250328_115506.jpg"));
    }
}
