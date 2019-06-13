module QuizMaster {
  requires javafx.fxml;
  requires javafx.controls;
  requires javafx.graphics;
  requires java.sql;

  opens view to javafx.graphics, javafx.fxml;
  opens controller to javafx.fxml;
}