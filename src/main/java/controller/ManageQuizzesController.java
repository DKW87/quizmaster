package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Quiz;
import model.UserSession;
import utils.Util;
import view.Main;
import view.SceneManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static utils.Util.showAlert;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 18 juni 2024 - 10:00
 */


public class ManageQuizzesController {
    private final DBAccess dDacces = Main.getdBaccess();
    private final SceneManager sceneManager = Main.getSceneManager();
    QuizDAO quizDAO = new QuizDAO(dDacces);
    private final UserSession userSession = Main.getUserSession();


    @FXML
    public TableView<Quiz> quizTable;
    @FXML
    public TableColumn<Quiz, String> nameColumn;
    @FXML
    public TableColumn<Quiz, String> courseColumn;
    @FXML
    public TableColumn<Quiz, String> difficultyColumn;
    @FXML
    public TableColumn<Quiz, Integer> passMarkColumn;
    @FXML
    public TableColumn<Quiz, Integer> numberQuestionsColumn;


    // setup bij openen van het QuizList scherm waarbij de bestaande Quizzes op basis van ingelogde userID uit de DB worden gehaald.
    public void setup() {
        List<Quiz> quizzen = getSelectedQuizByRoleUserID();
        if (quizzen != null) {
            for (Quiz quiz : quizzen) {
                quizTable.getItems().add(quiz);
            }
            generateQuizTable();

        } else {
            generateQuizTable();
        }
    }

    // action button om terug te gaan naar het hoofdmenu
    @FXML
    public void doMenu(ActionEvent actionEvent) {
        sceneManager.showWelcomeScene();
    }

    // action button om een nieuwe Quiz te maken
    @FXML
    public void doCreateQuiz(ActionEvent event) {
        sceneManager.showCreateUpdateQuizScene(null);
    }

    // // action button om een bestaande Quiz te updaten
    @FXML
    public void doUpdateQuiz() {
        Quiz selectedQuiz = quizTable.getSelectionModel().getSelectedItem();
        if (selectedQuiz == null) {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "Selecteer eerst een quiz!");
        } else {
            sceneManager.showCreateUpdateQuizScene(selectedQuiz);
        }
    }

    // action button om een geselecteerde Quiz uit de lijst te verwijderen --> alleen indien er geen vragen bij een Quiz horen (#vragen is NULL)
    @FXML
    public void doDeleteQuiz(ActionEvent event) {
        Quiz quiz = quizTable.getSelectionModel().getSelectedItem();
        if (quiz == null) {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "Selecteer eerst een quiz!");
        } else if (quiz.getQuestionsInQuizCount() != 0) {
            Util.showAlert(Alert.AlertType.ERROR, "Kan deze Quiz niet verwijderen", "Bij deze Quiz horen nog vragen, verwijder eerst de bijbehorende vragen");
        } else {
            String message = String.format("Weet je zeker dat je de %s wilt verwijderen?", quiz.getQuizName());
            if (Util.confirmMessage("Delete Quiz", message)) {
                quizTable.getItems().remove(quiz);
                quizDAO.deleteOneById(quiz.getQuizId());
            }
        }
    }

    // action button om van de lijst van Quizzen in de tabel een bestand aan te maken en op te slaan
    // roept methode maakBestandvanQuizLijst aan.

    @FXML
    public void doGenerateQuizFile(ActionEvent event) {
        List<Quiz> quizzen = getSelectedQuizByRoleUserID();
        if (quizzen != null) {
            maakBestandvanQuizLijst(quizzen);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Quiz bestand", "Quiz kan niet worden opgeslagen, lijst is leeg!");
        }
    }

    // methode om de QuizTable te vullen -> gebruikt in de Setup
    private void generateQuizTable() {
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizName())));
        courseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCourse().getName()));
        difficultyColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getQuizDifficulty().getName()));
        numberQuestionsColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuestionsInQuizCount()).asObject());
        passMarkColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuizPoints()).asObject());
        quizTable.getSelectionModel().selectFirst();
    }

    // methode om de juiste lijst met Quizzen te genereren op basis van de ingelogde UserRole en indien coordinator alleen Quizzen die bij die coordinator horen
    private List<Quiz> getSelectedQuizByRoleUserID() {
        final int ROLE_ID_STUDENT = 1;
        final int ROLE_ID_COORDINATOR = 2;
        int logedinUser = userSession.getUser().getUserId();
        int logedinRole = userSession.getUser().getRole();
        List<Quiz> selectedQuizes;

        if (logedinRole == ROLE_ID_STUDENT) {
            selectedQuizes = null;
        } else if (logedinRole == ROLE_ID_COORDINATOR) {
            selectedQuizes = quizDAO.getAllQuizzesByCoordinator(logedinUser);
        } else selectedQuizes = quizDAO.getAll();
        return selectedQuizes;
    }

    // methode om van een opgegeven (Quiz) lijst een bestand te maken en op te slaan. Deel van de methode wordt ook ergens anders gebruikt in de applicatie dus zou naar helpers kunnen gaan.
    public static void maakBestandvanQuizLijst(List<Quiz> quizLijst) {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Kies een locatie om het bestand op te slaan");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (PrintWriter printWriter = new PrintWriter(fileToSave)) {
                    StringBuilder printHeaders = new StringBuilder();
                    printHeaders.append(String.format("%-30s, %-30s, %-20s, %-20s, %-20s", "Quiznaam:", "Cursusnaam:", "Moeilijkheidsgraad:", "Cesuur:", "Aantal vragen in Quiz"));
                    printWriter.println(printHeaders);
                    for (Quiz quiz : quizLijst) {
                        printWriter.println(String.format("%-30s, %-30s, %-20s, %-20s, %-20s", quiz.getQuizName(), quiz.getCourse(), quiz.getQuizDifficulty(), quiz.getQuizPoints(), quiz.getQuestionsInQuizCount()));
                    }
                    JOptionPane.showMessageDialog(null, "Bestand succesvol op je computer opgeslagen!", "Bestand opgeslagen", JOptionPane.INFORMATION_MESSAGE);
                } catch (FileNotFoundException fout) {
                    System.err.println("Bestand niet gevonden: " + fout.getMessage());
                } catch (Exception e) {
                    System.err.println("Er is een fout opgetreden: " + e.getMessage());
                }
            }
        });
    }


}
