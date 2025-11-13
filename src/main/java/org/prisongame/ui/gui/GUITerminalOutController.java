package org.prisongame.ui.gui;

import javafx.animation.KeyFrame;
import javafx.scene.control.TextArea;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.concurrent.Flow;

public class GUITerminalOutController implements Flow.Subscriber<String> {
    private final TextArea terminalOut;
    private Flow.Subscription subscription;

    public GUITerminalOutController(TextArea terminalOut) {
        this.terminalOut = terminalOut;
    }

    public void print(String text) {
        terminalOut.appendText(text);
    }

    public void println(String text) {
        terminalOut.appendText(text + "\n");
    }

    public void printCommand(String command) {
        terminalOut.appendText("> " + command + "\n");
    }

    public void clear() {
        terminalOut.clear();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(String item) {
        printCommand(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    private void typeText(String text) {
        // Clear line to append new animated text
        final StringBuilder displayed = new StringBuilder();

        Timeline timeline = new Timeline();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            KeyFrame frame = new KeyFrame(
                    Duration.millis(30 * i), // 30ms per character (adjust for speed)
                    e -> {
                        displayed.append(text.charAt(index));
                        terminalOut.appendText(String.valueOf(text.charAt(index)));
                    }
            );
            timeline.getKeyFrames().add(frame);
        }
        timeline.play();
    }
}
