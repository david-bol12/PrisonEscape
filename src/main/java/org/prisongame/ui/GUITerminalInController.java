package org.prisongame.ui;

import javafx.scene.control.TextField;
import org.prisongame.commands.Command;
import org.prisongame.commands.Parser;

import java.util.concurrent.SubmissionPublisher;

public class GUITerminalInController extends SubmissionPublisher<String> {

    TextField terminalIn;
    String hintText;
    Parser parser = new Parser();

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

    public void setDisable(boolean disable) {
        terminalIn.setDisable(disable);
    }

    public boolean getDisable() {
        return terminalIn.isDisable();
    }
}
