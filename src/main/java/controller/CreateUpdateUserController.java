package controller;

import database.mysql.RoleDAO;
import database.mysql.UserDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Role;
import model.User;
import view.Main;
import utils.Util;

import java.util.List;


public class CreateUpdateUserController {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    public Button OpslaanButton;
    public Button MenuButton;
    public Button GebruikerslijstButton;

    @FXML
    private Label titelLabel;
    @FXML
    private TextField GebruikersIdTextField;
    @FXML
    private TextField GebruikersNaamTextField;
    @FXML
    private PasswordField WachtwoordTextField;
    @FXML
    private TextField VoornaamTextField;
    @FXML
    private TextField TussenvoegselTextField;
    @FXML
    private TextField AchternaamTextfield;
    @FXML
    public ComboBox<Role> rolComboBox;

    public CreateUpdateUserController() {
        this.roleDAO = new RoleDAO(Main.getdBaccess());
        this.userDAO = new UserDAO(Main.getdBaccess());
    }

    public void setup(User user) {
        List<Role> allRoles = roleDAO.getAll();
        rolComboBox.setItems(FXCollections.observableArrayList(allRoles));
        if (user != null) {
            titelLabel.setText("Wijzig gebruiker");
            populateFields(user);
        }
    }

    // Method to go back to main menu.
    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    // Method to go back to User List.
    public void doGebruikerslijst() {
        Main.getSceneManager().showManageUserScene();
    }

    // Method to create a user if saving button is pressed.
    @FXML
    public void doCreateUpdateUser() {
        User user = createUser();
        if (user != null) {
            if (GebruikersIdTextField.getText().isEmpty()) {
                userDAO.storeOne(user);
                Util.showAlert(Alert.AlertType.INFORMATION, "UserSaved", "Gebruiker is aangemaakt met username\n" + user.getUserName());
                resetForm();
            } else {
                userDAO.updateOne(user);
                Util.showAlert(Alert.AlertType.INFORMATION, "UserSaved", "Gebruiker is gewijzigd");
                Main.getSceneManager().showManageUserScene();
            }
        }
    }

    // Method to create new user with passed values.
    public User createUser() {
        if (!inputIsValid()) {
            Util.showAlert(Alert.AlertType.ERROR, "InvalidInput", "Vul alle gegevens in! Alleen een tussenvoegsel is niet verplicht.");
            return null;
        }
        String userName = GebruikersNaamTextField.getText();
        String password = WachtwoordTextField.getText();
        String firstName = VoornaamTextField.getText();
        String infix = TussenvoegselTextField.getText();
        String lastName = AchternaamTextfield.getText();
        Role role = rolComboBox.getSelectionModel().getSelectedItem();
        User user = new User(userName, password, firstName, infix, lastName, role);
        if (!GebruikersIdTextField.getText().isEmpty()) {
            user.setUserId(Integer.parseInt(GebruikersIdTextField.getText()));
        }
        return user;
    }

    private void resetForm() {
        GebruikersNaamTextField.clear();
        WachtwoordTextField.clear();
        VoornaamTextField.clear();
        TussenvoegselTextField.clear();
        AchternaamTextfield.clear();
        rolComboBox.getSelectionModel().clearSelection();
    }

    // Helper method to populate the fields in the scene.
    private void populateFields(User user) {
        GebruikersIdTextField.setText(String.valueOf(user.getUserId()));
        GebruikersNaamTextField.setText(user.getUserName());
        WachtwoordTextField.setText(user.getPassword());
        VoornaamTextField.setText(user.getFirstName());
        TussenvoegselTextField.setText(user.getInfix());
        AchternaamTextfield.setText(user.getLastName());

        for (Role role : rolComboBox.getItems()) {
            if (role.getRoleId() == user.getRole()) {
                rolComboBox.getSelectionModel().select(role);
                break;
            }
        }
    }

    // Helper method to check if input is valid
    private boolean inputIsValid() {
        return  !WachtwoordTextField.getText().isEmpty() &&
                !VoornaamTextField.getText().isEmpty() &&
                !AchternaamTextfield.getText().isEmpty() &&
                rolComboBox.getValue() != null;
    }
}



