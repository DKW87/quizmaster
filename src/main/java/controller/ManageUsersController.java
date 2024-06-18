package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import model.User;
import view.Main;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class ManageUsersController {
    private final DBAccess dbAccess = Main.getdBaccess();
    private final UserDAO userDAO = new UserDAO(dbAccess);

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

    public void doUpdateUser() {
        User user = userList.getSelectionModel().getSelectedItem();
        if (user == null) {
            errorField.setVisible(true);
            errorField.setText("Je moet een gebruiker selecteren om te wijzigen!");
        } else {
            Main.getSceneManager().showCreateUpdateUserScene(user);
        }
    }

    // Method will delete a user from the DB
    public void doDeleteUser() {
        User user = userList.getSelectionModel().getSelectedItem();
        if (user == null) {
            errorField.setVisible(true);
            errorField.setText("Je moet een gebruiker selecteren om te verwijderen!");
        } else {
            userDAO.deleteOneById(user.getUserId());
            userList.getItems().remove(user);
        }
    }
}
