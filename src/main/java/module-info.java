module org.prisongame.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports org.prisongame.ui;
    opens org.prisongame.ui to javafx.fxml;
}