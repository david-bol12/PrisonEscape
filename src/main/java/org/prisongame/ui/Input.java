package org.prisongame.ui;

public interface Input {
    void setDisable(boolean disable);
    void disablePeriod(int millis) throws InterruptedException;
}
