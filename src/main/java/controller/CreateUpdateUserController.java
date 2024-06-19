package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;
import view.Main;


public class CreateUpdateUserController {

    private final UserDAO userDAO;

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
    private MenuButton rolDropdown;

    public CreateUpdateUserController() {
        this.userDAO = new UserDAO(Main.getdBaccess());
    }

    public void setup(User user) {
        titelLabel.setText("Wijzig gebruiker");
        GebruikersIdTextField.setText(String.valueOf(user.getUserId()));
        GebruikersNaamTextField.setText(String.valueOf(user.getUserName()));
        WachtwoordTextField.setText(String.valueOf(user.getPassword()));
        VoornaamTextField.setText(String.valueOf(user.getFirstName()));
        TussenvoegselTextField.setText(String.valueOf(user.getInfix()));
        AchternaamTextfield.setText(String.valueOf(user.getLastName()));
        // TO DO: Dropdown menu for Role??
    }

    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    @FXML
    public void doCreateUpdateUser() {
        User user = createUser();
        if (user != null) {
            Alert savedUser = new Alert(Alert.AlertType.INFORMATION);
            savedUser.setContentText("Gebruiker opgeslagen");
            savedUser.show();
        }

    }

    private User createUser() {
        StringBuilder error = new StringBuilder();
        boolean correctInput = true;

        String userName = GebruikersNaamTextField.getText();
        String password = WachtwoordTextField.getText();
        String firstName = VoornaamTextField.getText();
        String infix = TussenvoegselTextField.getText();
        String lastName = AchternaamTextfield.getText();
        String Role = rolDropdown.getText();
        if (userName.isEmpty()) {
            error.append("Vul alle waardes in! Alleen een tussenvoegsel is niet nodig.\n");
            correctInput = false;
        }
        if (!correctInput) {
            Alert inputError = new Alert(Alert.AlertType.ERROR);
            inputError.setContentText(error.toString());
            inputError.showAndWait();
            return null;
        } //else {
          //  return new User(userName, password, firstName, infix, lastName, Role);  // HIER MOET ROLE GEIMPLEMENTEERD WORDEN VIA DROPDOWN!
        //}
        return null;
    }
}



