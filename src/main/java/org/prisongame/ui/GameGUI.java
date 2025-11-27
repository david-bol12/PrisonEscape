package org.prisongame.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.prisongame.character.Player;
import org.prisongame.game.Game;
import org.prisongame.map.GameMapState;
import org.prisongame.map.Location;
import org.prisongame.items.Item;
import org.prisongame.map.Room;

import java.io.*;
import java.util.List;
import java.util.Map;

public class GameGUI extends Application{

    Game game = null;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello!");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/prisongame/ui/home.fxml"));
        File saveDir = new File("saves/save1");
        saveDir.mkdirs(); // ensure path exists
        File playerFile = new File(saveDir, "player.ser");
        File mapFile = new File(saveDir, "map.ser");

        Player player;
        try {
            player = retrieveGame(playerFile, mapFile);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No Save Found");
            player = new Player("David", Location.CANTEEN);
            GameMapState.createMapState();
        }

        HomeScreen homeScreen = new HomeScreen(fxmlLoader);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.setScene(homeScreen);


        this.game = new Game(homeScreen.getGuiTerminalOutController(), player);
        stage.setOnCloseRequest((_) -> {
            saveGame(playerFile, mapFile);
        });
        PlayerUIController playerUIController = player.getPlayerUIController();
        homeScreen.getGuiTerminalInController().subscribe(this.game);
        homeScreen.setAvatarLocation(playerUIController.getLocationNotifier().get());
        playerUIController.getLocationNotifier().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.setAvatarLocation(newValue));
        });
        homeScreen.getEnergyBar().setStatus(player.getEnergy());
        playerUIController.getEnergyNotifier().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.getEnergyBar().setStatus(newValue));
        });
        homeScreen.getIntellectBar().setStatus(player.getIntellect());
        playerUIController.getIntellectNotifier().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.getIntellectBar().setStatus(newValue));
        });
        homeScreen.getStrengthBar().setStatus(player.getStrength());
        playerUIController.getStrengthNotifier().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.getStrengthBar().setStatus(newValue));
        });
        homeScreen.getMoneyLabel().setText(String.valueOf(player.getMoney()));
        playerUIController.getMoneyNotifier().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.getMoneyLabel().setText(String.valueOf(newValue)));
        });
        game.getMaximiseTerminal().addListener((_, _, newValue) -> {
            if (newValue == true) {
                Platform.runLater(homeScreen::maximiseTerminal);
            } else {
                Platform.runLater(homeScreen::minimiseTerminal);
            }
        });
        homeScreen.getInventoryController().addItems(player.getInventory());
        playerUIController.getInventoryNotifier().addListener((ListChangeListener.Change<? extends Item> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> homeScreen.getInventoryController().addItems((List<Item>) change.getAddedSubList()));
                }
                if (change.wasRemoved()) {
                    Platform.runLater(() -> homeScreen.getInventoryController().removeItems((List<Item>) change.getRemoved()));
                }
            }
        });
        stage.show();
        this.game.play();
    }

    private void saveGame(File playerFile, File mapFile) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(playerFile))) {
            out.writeObject(this.game.getPlayer());
            System.out.println("Object has been serialized to player.ser");
        } catch (IOException e) {
            System.out.println("Save Failed");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(mapFile))) {
            out.writeObject(GameMapState.getLocations());
            System.out.println("Object has been serialized to map.ser");
        } catch (IOException e) {
            System.out.println("Save Failed");
        }
    }

    private Player retrieveGame(File playerFile, File mapFile) throws IOException, ClassNotFoundException {
        ObjectInputStream playerIn = new ObjectInputStream(new FileInputStream(playerFile));
        ObjectInputStream mapIn = new ObjectInputStream(new FileInputStream(mapFile));
        Player player = (Player) playerIn.readObject();
        player.initPlayerUIController();
        GameMapState.setState((Map<Location, Room>) mapIn.readObject());
        return player;
    }
}