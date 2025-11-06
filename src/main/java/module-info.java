module org.prisongame.ui {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.prisongame.ui;
    opens org.prisongame.ui to javafx.fxml;
}