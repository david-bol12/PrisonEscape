package org.prisongame.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.prisongame.ui.Output;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameGUI extends Application implements Output {

    final FXMLLoader fxmlLoader  = new FXMLLoader(GameGUI.class.getResource("home.fxml"));
    GameGUIController gameGUIController = fxmlLoader.getController();
    final GUITerminalOutController guiTerminalOutController = new GUITerminalOutController(gameGUIController.terminalOut);
    final GUITerminalInController guiTerminalInController = new GUITerminalInController(gameGUIController.terminalIn);


    @Override
    public void start(Stage stage) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        guiTerminalInController.subscribe(guiTerminalOutController);
        stage.show();
    }

    public GUITerminalInController getGuiTerminalInController() {
        return guiTerminalInController;
    }

    public GUITerminalOutController getGuiTerminalOutController() {
        return guiTerminalOutController;
    }

    @Override
    public void print(String text) {
        guiTerminalOutController.print(text);
    }

    @Override
    public void println(String text) {
        guiTerminalOutController.println(text);
    }

    @Override
    public void clear() {
        guiTerminalOutController.clear();
    }
}