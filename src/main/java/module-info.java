module QuizMaster {
  requires javafx.fxml;
  requires javafx.controls;
  requires javafx.graphics;

  opens view to javafx.graphics, javafx.fxml;
  opens controller to javafx.fxml;
  opens controller.shared to javafx.fxml;
}