package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;
import view.Main;

import java.util.List;

public class ManageUsersController {
    private final DBAccess dbAccess = Main.getdBaccess();
    private final UserDAO userDAO = new UserDAO(dbAccess);

    @FXML
    public Button updateUserId;
    @FXML
    public Button deleteUserId;
    @FXML
    public Button createUserId;
    @FXML
    TextField errorField;
    @FXML
    ListView<User> userList;

    public void setup() {
        List<User> users = userDAO.getAll();
        for (User user : users) {
            userList.getItems().add(user);
        }
        userList.getSelectionModel().selectFirst();
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    public void doCreateUser() {
        Main.getSceneManager().showCreateUpdateUserScene(null); // Hoe moet ik hier een user doorgeven? Ik moet op een nieuwe scene komen om daar een user aan te maken.
    }

    @FXML
    public void doUpdateUser() {
        User user = userList.getSelectionModel().getSelectedItem();
        if (user == null) {
            errorField.setVisible(true);
            errorField.setText("Je moet een gebruiker selecteren om te wijzigen!");
        } else {
            Main.getSceneManager().showCreateUpdateUserScene(user);
        }
    }

    @FXML
    // Method will delete a user from the DB
    public void doDeleteUser() {
        User user = userList.getSelectionModel().getSelectedItem();
        if (user == null) {
            showError("Je moet een gebruiker selecteren om te verwijderen!");
            return;
        }
        if (showConfirmDialog("Gebruiker verwijderen", "Weet u zeker dat u de gebruiker '" + user.getUserName() + "' wilt verwijderen?")) {
            userDAO.deleteOneById(user.getUserId());
            userList.getItems().remove(user);
            errorField.setVisible(false);
        }
    }

    // Helper method to display an error
    private void showError(String message) {
        errorField.setVisible(true);
        errorField.setText(message);
    }

    // Helper method to show a confirmation dialog
    private boolean showConfirmDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}



