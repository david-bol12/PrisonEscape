package org.prisongame.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameUI extends Application {

    static final FXMLLoader fxmlLoader  = new FXMLLoader(GameUI.class.getResource("home.fxml"));

    @Override
    public void start(Stage stage) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        GameUIController gameUIController = fxmlLoader.getController();
        UITerminalOutController uiTerminalOutController = new UITerminalOutController(gameUIController.terminalOut);
        UITerminalInController uiTerminalInController = new UITerminalInController(gameUIController.terminalIn);
        uiTerminalInController.subscribe(uiTerminalOutController);
        stage.show();
    }
}