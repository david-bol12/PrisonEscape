package org.prisongame.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import org.prisongame.map.Location;

import java.io.IOException;
import java.util.Objects;

public class HomeScreen extends Scene {

    private final HomeFXMLController homeFXMLController;
    private final GUITerminalOutController guiTerminalOutController;
    private final GUITerminalInController guiTerminalInController;
    private final AvatarController avatarController;
    private Bounds mapBounds;
    private final InventoryController inventoryController;
    private final StatusBarController energyBar;
    private final StatusBarController intellectBar;
    private final StatusBarController strengthBar;


    public HomeScreen(FXMLLoader fxmlLoader) throws IOException {
        super(fxmlLoader.load());
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        homeFXMLController = fxmlLoader.getController();
        this.guiTerminalOutController = new GUITerminalOutController(homeFXMLController.terminalOut);
        this.guiTerminalInController = new GUITerminalInController(homeFXMLController.terminalIn);
        this.mapBounds = homeFXMLController.map.boundsInParentProperty().get();
        this.avatarController = new AvatarController(homeFXMLController.avatar, Location.CELL_BLOCK, mapBounds);
        this.energyBar = new StatusBarController(100, homeFXMLController.energyBarLabel, homeFXMLController.energyBar);
        this.intellectBar = new StatusBarController(0, homeFXMLController.intellectBarLabel, homeFXMLController.intellectBar);
        this.strengthBar = new StatusBarController(0, homeFXMLController.strengthBarLabel, homeFXMLController.strengthBar);
        this.inventoryController = new InventoryController(homeFXMLController.inventoryLabel);
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

    public void setAvatarLocation(Location location) {
        avatarController.setLocation(location, mapBounds);
    }

    public ImageView getMap() {
        return homeFXMLController.map;
    }

    public InventoryController getInventoryController() {
        return inventoryController;
    }

    public StatusBarController getEnergyBar() {
        return energyBar;
    }

    public StatusBarController getIntellectBar() {
        return intellectBar;
    }

    public StatusBarController getStrengthBar() {
        return strengthBar;
    }
}
