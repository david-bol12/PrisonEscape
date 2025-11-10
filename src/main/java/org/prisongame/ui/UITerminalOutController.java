package org.prisongame.ui;

import javafx.scene.control.TextArea;

import java.util.concurrent.Flow;

public class UITerminalOutController implements Flow.Subscriber<String> {
    private final TextArea terminalOut;
    private Flow.Subscription subscription;

    public UITerminalOutController(TextArea terminalOut) {
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
}
