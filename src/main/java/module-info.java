module QuizMaster {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires lightcouch;
    requires com.google.gson;
    requires java.desktop;

    opens view to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    opens model to com.google.gson;
}