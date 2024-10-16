package controller;

import database.mysql.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.Main;

import static utils.Util.confirmMessage;

public class LoginController {

    private final UserDAO userDao = new UserDAO(Main.getdBaccess());

    @FXML
    public Label loginError;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;


    public void doLogin() {
        String userName = nameTextField.getText();
        String password = passwordField.getText();
        if (!validate(userName, password)) {
            loginError.setVisible(true);
            return;
        }
        if (authenticate(userName, password)) {
            Main.getSceneManager().showWelcomeScene();
        } else {
            loginError.setVisible(true);
        }


    }

    @FXML
    public void doQuit() {
        if (confirmMessage("Afsluiten", "Weet je zeker dat je het programma wil afsluiten?")) {
            Main.getdBaccess().closeConnection();
            Platform.exit();
        }
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
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        }
        // Set session user
        Main.getUserSession().setUser(user);
        return true;
    }
}
