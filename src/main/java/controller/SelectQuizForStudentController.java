package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Quiz;
import model.UserSession;
import view.Main;
import view.SceneManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static utils.Util.showAlert;

public class SelectQuizForStudentController {
    private final DBAccess dDacces = Main.getdBaccess();
    private final SceneManager sceneManager = Main.getSceneManager();
    QuizDAO quizDAO = new QuizDAO(dDacces);
    private final UserSession userSession = Main.getUserSession();

    @FXML
    public TableView<Quiz> quizTableStudent;
    @FXML
    public TableColumn<Quiz, String> quizNameColumn;
    @FXML
    public TableColumn<Quiz, String> quizLevelColumn;
    @FXML
    public TableColumn<Quiz, String> courseColumn;
    @FXML
    public TableColumn<Quiz, String> numberQuestionsColumn;
    @FXML
    public TableColumn<Quiz, String> numberMadeColumn;
    @FXML
    public TableColumn<Quiz, String> numberSuccesColumn;
    @FXML
    public TableColumn<Quiz, String> highscoreColumn;

    // Setup voor aanroepen van het scherm
    public void setup() {
        int logedInStudentID = userSession.getUser().getUserId();
        List<Quiz> quizzenPerStudent = quizDAO.getAllQuizzesByStudentId(logedInStudentID);
        if (quizzenPerStudent != null) {
            for (Quiz quiz : quizzenPerStudent) {
                quizTableStudent.getItems().add(quiz);
            }
            generateQuizTable();
        } else {
            generateQuizTable();
        }
    }

    @FXML
    public void doMenu(ActionEvent actionEvent) {
        sceneManager.showWelcomeScene();
    }


    @FXML
    public void doQuiz() {
    }


    @FXML
    public void doGenerateQuizFile() {
        int logedInStudentID = userSession.getUser().getUserId();
        List<Quiz> quizzenPerStudent = quizDAO.getAllQuizzesByStudentId(logedInStudentID);
        if (quizzenPerStudent != null) {
            maakBestandvanQuizLijst(quizzenPerStudent);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Quiz bestand", "Kan niet worden opgeslagen, lijst is leeg!");
        }
    }

    private void generateQuizTable() {
        quizNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizName())));
        quizLevelColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizDifficulty())));
        courseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCourse().getName())));
        numberQuestionsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuestionsInQuizCount())));
        numberMadeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(" ")); // todo Ophalen uit CouchDB?
        numberSuccesColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(" ")); // todo Ophalen uit CouchDB?
        highscoreColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(" ")); // todo Ophalen uit CouchDB?

        quizTableStudent.getSelectionModel().selectFirst();
    }
    public static void maakBestandvanQuizLijst(List<Quiz> quizLijst) {

        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Kies een locatie om het bestand op te slaan");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (PrintWriter printWriter = new PrintWriter(fileToSave)) {
                    StringBuilder printHeaders = new StringBuilder();
                    printHeaders.append(String.format("%-30s, %-20s, %-30s, %-20s, %-20s, %-20s, %-20s", "Quiznaam:" ,"Moeilijkheidsgraad:", "Cursus:", "Aantal vragen:","Aantal keer gemaakt:", "Aantal keer succesvol:", "Highscore:"));
                    printWriter.println(printHeaders);
                    for (Quiz quiz : quizLijst) {
                        printWriter.println(String.format("%-30s, %-20s, %-30s, %-20s, %-20s, %-20s, %-20s", quiz.getQuizName(), quiz.getQuizDifficulty(), quiz.getCourse(), quiz.getQuestionsInQuizCount(),"0","0","0"));
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
