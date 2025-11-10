package org.prisongame.ui;

import javafx.scene.control.TextField;

import java.util.concurrent.SubmissionPublisher;

public class UITerminalInController extends SubmissionPublisher<String> {

    TextField terminalIn;

    public UITerminalInController(TextField terminalIn) {
        this.terminalIn = terminalIn;

        terminalIn.setOnAction(_ -> {
            if (!terminalIn.getText().isEmpty()) submit(terminalIn.getText());
            terminalIn.clear();
        });
    }
}
