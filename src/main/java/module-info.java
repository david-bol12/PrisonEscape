module org.prisongame.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    exports org.prisongame.ui;
    opens org.prisongame.ui to javafx.fxml;
    exports org.prisongame.game;
    opens org.prisongame.game to javafx.fxml;
    exports org.prisongame.map;
    opens org.prisongame.map to javafx.fxml;
}