module org.prisongame.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    exports org.prisongame.ui;
    opens org.prisongame.ui to javafx.fxml;
    exports org.prisongame.ui.cli;
    opens org.prisongame.ui.cli to javafx.fxml;
}