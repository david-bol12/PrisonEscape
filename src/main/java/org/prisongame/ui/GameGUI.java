package org.prisongame.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.prisongame.game.Game;
import org.prisongame.game.GameMap;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameGUI extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello!");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        HomeScreen homeScreen = new HomeScreen(fxmlLoader, GameMap.CELL_BLOCK);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setScene(homeScreen);
        stage.show();
        Game game = new Game(homeScreen.getGuiTerminalOutController());
        homeScreen.getGuiTerminalInController().subscribe(game);
        game.getPlayer().getLocationNotifier().addListener((_, _, newValue) -> {
            homeScreen.setAvatarLocation(newValue);
        });
        game.play();
    }
}