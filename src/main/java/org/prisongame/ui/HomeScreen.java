package org.prisongame.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.prisongame.game.GameMap;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Objects;

public class HomeScreen extends Scene {

    private FXMLLoader fxmlLoader;
    private HomeScreenController homeScreenController;
    private GUITerminalOutController guiTerminalOutController;
    private GUITerminalInController guiTerminalInController;
    private AvatarController avatarController;
    private Bounds mapBounds;
    private String inventoryLabel;


    public HomeScreen(FXMLLoader fxmlLoader, GameMap startLocation) throws IOException {
        super(fxmlLoader.load());
        this.fxmlLoader = fxmlLoader;
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        homeScreenController = fxmlLoader.getController();
        guiTerminalOutController = new GUITerminalOutController(homeScreenController.terminalOut);
        guiTerminalInController = new GUITerminalInController(homeScreenController.terminalIn);
        this.mapBounds = homeScreenController.map.boundsInParentProperty().get();
        avatarController = new AvatarController(homeScreenController.avatar, startLocation, mapBounds);
        getMap().boundsInParentProperty().addListener(((_, _, newValue) -> {
            mapBounds = newValue;
            avatarController.setLocation(avatarController.location, mapBounds);
        }));
    }

    public GUITerminalInController getGuiTerminalInController() {
        return guiTerminalInController;
    }

    public GUITerminalOutController getGuiTerminalOutController() {
        return guiTerminalOutController;
    }

    public AvatarController getAvatarController() {
        return avatarController;
    }

    public ImageView getMap() {
        return homeScreenController.map;
    }

}
