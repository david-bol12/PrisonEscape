package org.prisongame.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import org.prisongame.game.GameMap;

import java.io.IOException;
import java.util.Objects;

public class HomeScreen extends Scene {

    private final HomeFXMLController homeScreenController;
    private final GUITerminalOutController guiTerminalOutController;
    private final GUITerminalInController guiTerminalInController;
    private final AvatarController avatarController;
    private Bounds mapBounds;
    private String inventoryLabel;


    public HomeScreen(FXMLLoader fxmlLoader, GameMap startLocation) throws IOException {
        super(fxmlLoader.load());
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        homeScreenController = fxmlLoader.getController();
        this.guiTerminalOutController = new GUITerminalOutController(homeScreenController.terminalOut);
        this.guiTerminalInController = new GUITerminalInController(homeScreenController.terminalIn);
        this.mapBounds = homeScreenController.map.boundsInParentProperty().get();
        this.avatarController = new AvatarController(homeScreenController.avatar, startLocation, mapBounds);
        getMap().boundsInParentProperty().addListener(((_, _, newValue) -> {
            mapBounds = newValue;
            avatarController.setLocation(avatarController.getLocation(), mapBounds);
        }));
    }

    public GUITerminalInController getGuiTerminalInController() {
        return guiTerminalInController;
    }

    public GUITerminalOutController getGuiTerminalOutController() {
        return guiTerminalOutController;
    }

    public void setAvatarLocation(GameMap location) {
        avatarController.setLocation(location, mapBounds);
    }

    public ImageView getMap() {
        return homeScreenController.map;
    }

}
