package org.prisongame.ui;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class SoundController {

    private static MediaPlayer backgroundPlayer = null;
    private static boolean isPlaying = false;

    public enum Sound {
        BLACKJACK_MUSIC("/org/prisongame/ui/Luigi'sCasino.mp3"),
        BLACKJACK_WIN("/org/prisongame/ui/BlackjackWin.wav"),
        BLACKJACK_LOSE("/org/prisongame/ui/BlackjackLose.wav"),
        BLACKJACK_DRAW("/org/prisongame/ui/BlackjackDraw.wav"),
        BLACKJACK_START("/org/prisongame/ui/BlackjackStart.wav");

        Sound(String path) {
            String dir = Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
            Platform.runLater(() -> {
                this.sound = new Media(dir);
            });
        }

        private Media sound;

        public Media getSound() {
            return sound;
        }
    }

    public static void playMusic(Sound sound) {
        Platform.runLater(() -> {
            if (backgroundPlayer != null) {
                backgroundPlayer.dispose();
            }
            backgroundPlayer = new MediaPlayer(sound.getSound());
            backgroundPlayer.setOnEndOfMedia(() -> {
                backgroundPlayer.play();
            });
            backgroundPlayer.play();
            isPlaying = true;
        });

    }

    public static void stopPlayer() {
        if (backgroundPlayer != null) {
            backgroundPlayer.stop();
            backgroundPlayer.dispose();
        }
        isPlaying = false;
    }

    public static void pausePlayer() {
        if (backgroundPlayer != null) {
            backgroundPlayer.pause();
        }
        isPlaying = false;
    }

    public static boolean getIsPlaying() {
        return isPlaying;
    }

    public static void playSFX(Sound sound) {
        MediaPlayer sfxPlayer = new MediaPlayer(sound.getSound());
        if (backgroundPlayer != null) backgroundPlayer.pause();
        sfxPlayer.setOnEndOfMedia(() -> {
            sfxPlayer.dispose();
            if (backgroundPlayer != null) backgroundPlayer.play();
        });
        sfxPlayer.play();
    }
}


