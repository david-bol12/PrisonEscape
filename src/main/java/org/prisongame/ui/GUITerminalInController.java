package org.prisongame.ui;

import javafx.scene.control.TextField;

import java.util.concurrent.SubmissionPublisher;

public class GUITerminalInController extends SubmissionPublisher<String> implements Input{

    TextField terminalIn;
    String hintText;

    public GUITerminalInController(TextField terminalIn) {
        this.terminalIn = terminalIn;
        this.hintText = "Enter a command...";

        terminalIn.setPromptText(hintText);

        terminalIn.setOnAction(_ -> {
            if (!terminalIn.getText().isEmpty()) submit(terminalIn.getText());
            terminalIn.clear();
        });
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    @Override
    public void setDisable(boolean disable) {
        terminalIn.setDisable(disable);
    }

    @Override
    public void disablePeriod(int timeMillis) throws InterruptedException {
        setDisable(true);
        Thread.sleep(timeMillis);
        setDisable(false);
    }

}
