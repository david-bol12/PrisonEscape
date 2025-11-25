package org.prisongame.ui;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class StatusBarController{
    private int status;
    final int maxStatus;
    private final Label label;
    private final ProgressBar statusBar;

    public StatusBarController(int status, Label label, ProgressBar progressBar) {
        this.status = status;
        this.label = label;
        this.statusBar = progressBar;
        this.maxStatus = 100;
        this.label.setText(String.valueOf(status));
        statusBar.setProgress((double) status / maxStatus);
    }

    public StatusBarController(int status, Label label, ProgressBar progressBar, int maxStatus) {
        this.status = status;
        this.label = label;
        this.statusBar = progressBar;
        this.maxStatus = maxStatus;
        this.label.setText(String.valueOf(status));
        statusBar.setProgress((double) status / maxStatus);
    }

    public void setStatus(int status) {
        this.status = status;
        this.label.setText(String.valueOf(status));
        statusBar.setProgress((double) status / maxStatus);
    }

}
