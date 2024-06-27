package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.UserSession;
import view.Main;
import view.SceneManager;
import static utils.Util.*;

public class WelcomeController {

    private static final int ROLE_DOCENT = 3;
    private final UserSession userSession = Main.getUserSession();
    private final SceneManager sceneManager = Main.getSceneManager();

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;

    public void setup() {
        // Check if user is logged in
        if (userSession.getUser() == null) {
            sceneManager.showLoginScene();
            return;
        }
        noAccessDocent();
        // Set welcome message
        welcomeLabel.setText(String.format("Ingelogd als %s, welkom %s!", userSession.getUser().getRoleName(),
                userSession.getUser().getFirstName()));

        taskMenuButton.getItems().addAll(configureMenuItems(userSession.getUser().getRole()));
    }

    public void doLogout() {
        if (confirmMessage("Uitloggen", "Weet je zeker dat je wil uitloggen?")) {
            sceneManager.showLoginScene();
            userSession.setUser(null);
        }

    }

    public void noAccessDocent() {
        if (userSession.getUser().getRole() == ROLE_DOCENT) {
            taskMenuButton.setVisible(false);
            if (confirmMessage("Deze functie is niet beschikbaar",
                    "De rol van de DOCENT is nog niet geïmplementeerd in deze versie van de applicatie." + "\nU wordt doorgestuurd naar het login scherm")) {
                sceneManager.showLoginScene();
                userSession.setUser(null);
            }
        }
    }

}

