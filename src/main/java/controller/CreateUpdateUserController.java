package controller;

import database.mysql.DBAccess;
import database.mysql.RoleDAO;
import database.mysql.UserDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Role;
import model.User;
import view.Main;

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
        rolComboBox.getItems().addAll(FXCollections.observableArrayList(allRoles));
        if (user != null) {
            titelLabel.setText("Wijzig gebruiker");
            GebruikersIdTextField.setText(String.valueOf(user.getUserId()));
            GebruikersNaamTextField.setText(String.valueOf(user.getUserName()));
            WachtwoordTextField.setText(String.valueOf(user.getPassword()));
            VoornaamTextField.setText(String.valueOf(user.getFirstName()));
            TussenvoegselTextField.setText(String.valueOf(user.getInfix()));
            AchternaamTextfield.setText(String.valueOf(user.getLastName()));

            allRoles.stream() // Added a Stream to search allRoles and match this with the users given role in getRole
                    .filter(role -> role.getRoleId() == user.getRole())
                    .findFirst().ifPresent(userRole -> rolComboBox.getSelectionModel().select(userRole));
        }
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    public void doGebruikerslijst() {
        Main.getSceneManager().showManageUserScene();
    }

    @FXML
    public void doCreateUpdateUser() {
        User user = createUser();
        if (user != null) {
            userDAO.storeOne(user);
            Alert savedUser = new Alert(Alert.AlertType.INFORMATION);
            savedUser.setContentText("Gebruiker opgeslagen");
            savedUser.show();
        }
    }

    public User createUser() {
        StringBuilder error = new StringBuilder();
        boolean correctInput = true;

        String userName = GebruikersNaamTextField.getText();
        String password = WachtwoordTextField.getText();
        String firstName = VoornaamTextField.getText();
        String infix = TussenvoegselTextField.getText();
        String lastName = AchternaamTextfield.getText();
        Role role = rolComboBox.getSelectionModel().getSelectedItem();
        if (userName.isEmpty()) {
            error.append("Vul alle waardes in! Alleen een tussenvoegsel is niet nodig.\n");
            correctInput = false;
        }
        return new User(userName, password, firstName, infix, lastName, role);
    }
}



