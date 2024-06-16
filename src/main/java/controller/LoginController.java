package controller;

import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.Main;

import static constants.Constant.*;

public class LoginController {

    private final  UserDAO userDao = new UserDAO(Main.getdBaccess());

    @FXML
    public Label errorLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;


    public void doLogin(ActionEvent actionEvent) {
        String userName = nameTextField.getText();
        String password = passwordField.getText();
        if (!validate(userName, password)) {
            errorLabel.setText("Please enter username and password");
            setErrorStyle();
            return;
        }
        if (authenticate(userName, password)) {
            Main.getSceneManager().showWelcomeScene();
        }


    }

    @FXML
    public void doQuit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Afsluiten");
        alert.setHeaderText("Weet u zeker dat u af wilt sluiten?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Main.getdBaccess().closeConnection();
            System.exit(0);
        }
    }

    private void resetStyle() {
        nameTextField.setStyle("-fx-border-color: " + PRIMARY_COLOR);
        passwordField.setStyle("-fx-border-color: " + PRIMARY_COLOR);
    }

    private void setErrorStyle() {
        nameTextField.setStyle("-fx-border-color: " + ERROR_COLOR);
        passwordField.setStyle("-fx-border-color: " + ERROR_COLOR);
    }

    private boolean validate(String userName, String password) {
        return !userName.isEmpty() && !password.isEmpty() && !userName.isBlank() && !password.isBlank();
    }

    /**
     * Authenticates a user by checking if the provided username and password match those in the database.
     *
     * @param userName the username of the user to authenticate
     * @param password the password of the user to authenticate
     * @return true if the username and password are valid and match the database, false otherwise
     */
    private boolean authenticate(String userName, String password) {
        var user = userDao.getByName(userName);
        if (user == null) {
            errorLabel.setText("Invalid Credentials");
            setErrorStyle();
            return false;
        }
        if (!user.getPassword().equals(password)) {
            errorLabel.setText("Invalid Credentials");
            setErrorStyle();
            return false;
        }
        // Set session
        errorLabel.setStyle("-fx-text-fill: " + SUCCESS_COLOR);
        errorLabel.setText("Login Successful");
        resetStyle();
        Main.getUserSession().setUser(user);
        return true;

    }
}
