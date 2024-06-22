package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Role;
import model.User;
import view.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public TableView<User> userTable;
    @FXML
    public TableColumn<User, String> usernameColumn;
    @FXML
    public TableColumn<User, String> firstNameColumn;
    @FXML
    public TableColumn<User, String> infixColumn;
    @FXML
    public TableColumn<User, String> lastNameColumn;
    @FXML
    public TableColumn<User, String> roleColumn;
    @FXML
    public TableColumn<User, Integer> amountInRoleColumn;
    @FXML
    TextField errorField;

    private final Map<Integer, Integer> roleCount = new HashMap<>(); // Store role counts

    public void setup() {
        List<User> users = userDAO.getAll();
        calculateRoleCounts(users);
        for (User user : users) {
            userTable.getItems().add(user);
        }
        generateUserTable();
    }

    // Method to go bac to the main menu.
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    // Method to go to the Create User screen.
    public void doCreateUser() {
        Main.getSceneManager().showCreateUpdateUserScene(null); // Hoe moet ik hier een user doorgeven? Ik moet op een nieuwe scene komen om daar een user aan te maken.
    }

    // Method to go to the Update User screen.
    @FXML
    public void doUpdateUser() {
        User user = (User) userTable.getSelectionModel().getSelectedItem();
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
        User user = (User) userTable.getSelectionModel().getSelectedItem();
        if (user == null) {
            showError("Je moet een gebruiker selecteren om te verwijderen!");
            return;
        }
        if (showConfirmDialog("Gebruiker verwijderen", "Weet u zeker dat u de gebruiker '" + user.getUserName() + "' wilt verwijderen?")) {
            userDAO.deleteOneById(user.getUserId());
            userTable.getItems().remove(user);
            errorField.setVisible(false);
        }
    }

    private void generateUserTable() {
        usernameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getUserName())));
        firstNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName()));
        infixColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInfix()));
        lastNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getLastName())));
        roleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getRoleName())));
        amountInRoleColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            int count = roleCount.getOrDefault(user.getRole(), 0) - 1; // Using - 1 to remove selected user from the size of the list.
            return new SimpleIntegerProperty(count).asObject();
        });
        userTable.getSelectionModel().selectFirst();
    }

    // Method to calculate the rolecount once and store them in a hashmap. Program kept crashing if constantly searching from DB every time.
    private void calculateRoleCounts(List<User> users) {
        roleCount.clear();
        for (User user : users) {
            int roleId = user.getRole();
            roleCount.put(roleId, roleCount.getOrDefault(roleId, 0) + 1);
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



