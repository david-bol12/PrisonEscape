package org.prisongame.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.prisongame.character.Player;
import org.prisongame.game.Game;
import org.prisongame.map.GameMap;
import org.prisongame.items.Item;

import java.util.List;

public class GameGUI extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello!");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/prisongame/ui/home.fxml"));
        HomeScreen homeScreen = new HomeScreen(fxmlLoader, GameMap.CELL_BLOCK);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setScene(homeScreen);
        stage.show();
        Game game = new Game(homeScreen.getGuiTerminalOutController());
        Player player = game.getPlayer();
        homeScreen.getGuiTerminalInController().subscribe(game);
        player.getLocationNotifier().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.setAvatarLocation(newValue));
        });
        homeScreen.getEnergyBar().setStatus(player.getEnergy());
        player.energyProperty().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.getEnergyBar().setStatus(newValue));
        });
        player.intellectProperty().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.getIntellectBar().setStatus(newValue));
        });
        player.strengthProperty().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.getStrengthBar().setStatus(newValue));
        });
        homeScreen.getInventoryController().addItems(player.getInventory());
        player.getInventoryNotifier().addListener((ListChangeListener.Change<? extends Item> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> homeScreen.getInventoryController().addItems((List<Item>) change.getAddedSubList()));
                }
                if (change.wasRemoved()) {
                    Platform.runLater(() -> homeScreen.getInventoryController().removeItems((List<Item>) change.getRemoved()));
                }
            }
        });
        game.play();
    }
}