package org.prisongame.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class HomeScreen extends Scene {

    private FXMLLoader fxmlLoader;
    private HomeScreenController homeScreenController;
    private GUITerminalOutController guiTerminalOutController;
    private GUITerminalInController guiTerminalInController;
    private String inventoryLabel;


    public HomeScreen(FXMLLoader fxmlLoader) throws IOException {
        super(fxmlLoader.load());
        this.fxmlLoader = fxmlLoader;
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        homeScreenController = fxmlLoader.getController();
        guiTerminalOutController = new GUITerminalOutController(homeScreenController.terminalOut);
        guiTerminalInController = new GUITerminalInController(homeScreenController.terminalIn);
    }

    public HomeScreenController getGameGUIController() {
        return homeScreenController;
    }

    public GUITerminalInController getGuiTerminalInController() {
        return guiTerminalInController;
    }

    public GUITerminalOutController getGuiTerminalOutController() {
        return guiTerminalOutController;
    }
}
