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


    public static File playerFile;
    public static File mapFile;
    public static Stage stage;
    private static final SoundController soundController = new SoundController();

    @Override
    public void start(Stage inStage) throws Exception {
        stage = inStage;
        startGame(stage);

    }

    public static void saveGame(File playerFile, File mapFile, Game game) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(playerFile))) {
            out.writeObject(game.getPlayer());
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

    public static void saveGame(Game game) {
        saveGame(playerFile, mapFile, game);
    }

    private static Player retrieveGame(File playerFile, File mapFile) throws IOException, ClassNotFoundException {
        ObjectInputStream playerIn = new ObjectInputStream(new FileInputStream(playerFile));
        ObjectInputStream mapIn = new ObjectInputStream(new FileInputStream(mapFile));
        Player player = (Player) playerIn.readObject();
        player.initPlayerUIController();
        GameMapState.setState((Map<Location, Room>) mapIn.readObject());
        return player;
    }

    public static void startGame(Stage stage) throws IOException {
        final Game game;
        stage.setTitle("Prison Game");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/org/prisongame/ui/home.fxml"));
        File saveDir = new File("saves/save1");
        saveDir.mkdirs(); // ensure path exists
        playerFile = new File(saveDir, "player.ser");
        mapFile = new File(saveDir, "map.ser");

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


        game = new Game(homeScreen.getGuiTerminalOutController(), homeScreen.getGuiTerminalInController(), player);
        stage.setOnCloseRequest((_) -> {
            saveGame(playerFile, mapFile, game);
        });
        PlayerUIController playerUIController = player.getPlayerUIController();
        homeScreen.getGuiTerminalInController().subscribe(game);
        homeScreen.setAvatarLocation(playerUIController.getLocationNotifier().get());
        homeScreen.setMapImage(game.getPlayer().getCurrentRoom().getFloor());
        playerUIController.getLocationNotifier().addListener((_, _, newValue) -> {
            Platform.runLater(() -> homeScreen.setAvatarLocation(newValue));
            if (GameMapState.getRoom(newValue).getFloor() == Location.Floor.F2) {
                Platform.runLater(() -> homeScreen.setMapImage(Location.Floor.F2));
            } else {
                Platform.runLater(() -> homeScreen.setMapImage(Location.Floor.F1));
            }
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
        homeScreen.getInventoryController().addItems(player.getInventoryList());
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
        game.play();
    }

    public static SoundController getSoundController() {
        return soundController;
    }
}