package org.prisongame.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.prisongame.game.Game;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class GameGUI extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello!");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        HomeScreen homeScreen = new HomeScreen(fxmlLoader);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(homeScreen);
        stage.show();
        Game game = new Game(homeScreen.getGuiTerminalOutController());
        homeScreen.getGuiTerminalInController().subscribe(game);
        game.play();
    }
}