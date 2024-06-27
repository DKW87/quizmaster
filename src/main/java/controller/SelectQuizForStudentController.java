package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.Course;
import model.Quiz;
import model.UserSession;
import utils.Util;
import view.Main;
import view.SceneManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static utils.Util.showAlert;

/*
 * @author Rob JANSEN
 * @project Quizmaster
 * @created 21/06/2024 - 11:00
 */

public class SelectQuizForStudentController {
    private static final DBAccess dDacces = Main.getdBaccess();
    private final SceneManager sceneManager = Main.getSceneManager();
    static QuizDAO quizDAO = new QuizDAO(dDacces);
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
    public TableColumn<Quiz, Integer> numberQuestionsColumn;
    @FXML
    public TableColumn<Quiz, Integer> numberMadeColumn;
    @FXML
    public TableColumn<Quiz, Integer> numberSuccesColumn;
    @FXML
    public TableColumn<Quiz, Integer> highscoreColumn;


    // Setup voor aanroepen van het scherm
    // alle Quizzen uit de DB worden opgehaald waarvoor een student (op basis van een studentID) een actieve cursus inschrijving heeft
    // generate QuizTable - methode - zie onder om de table te vullen

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

    // action button: ga terug naar hoofdmenu
    @FXML
    public void doMenu(ActionEvent actionEvent) {
        sceneManager.showWelcomeScene();
    }

    // action button: maak een quiz
    @FXML
    public void doQuiz() {
        if (quizTableStudent.getSelectionModel().getSelectedItem() != null) {
            Main.getSceneManager().showFillOutQuiz(quizTableStudent.getSelectionModel().getSelectedItem());
        } else {
            Util.showAlert(Alert.AlertType.ERROR, "Foutmelding", "Selecteer een quiz.");
        }
    }

    // action button om een file te maken van de QuizLijst
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

    // methode om de Quiz Tabel te vullen met de juiste waarden
    private void generateQuizTable() {
        quizNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizName())));
        quizLevelColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizDifficulty())));
        courseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCourse().getName())));

        numberQuestionsColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuestionsInQuizCount()).asObject());

        numberMadeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Quiz, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Quiz, Integer> cellData) {
                int numberMadeQuizzes = quizDAO.getNumberMadeQuizzes(
                        Main.getUserSession().getUser().getUserId(),
                        cellData.getValue().getQuizId()
                );
                return new SimpleIntegerProperty(numberMadeQuizzes).asObject();
            }
        });
        numberSuccesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Quiz, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Quiz, Integer> cellData) {
                int numberSuccesQuizzes = quizDAO.getNumberSuccesQuizzes(
                        Main.getUserSession().getUser().getUserId(),
                        cellData.getValue().getQuizId()
                );
                return new SimpleIntegerProperty(numberSuccesQuizzes).asObject();
            }
        });
        highscoreColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Quiz, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Quiz, Integer> cellData) {
                int quizHighscore = quizDAO.getQuizHighscore(
                        Main.getUserSession().getUser().getUserId(),
                        cellData.getValue().getQuizId()
                );
                return new SimpleIntegerProperty(quizHighscore).asObject();
            }
        });
        quizTableStudent.getSortOrder().add(numberMadeColumn);
        quizTableStudent.getSelectionModel().selectFirst();
    }

    // methode om een bestand te maken van een Quiz lijst
    public static void maakBestandvanQuizLijst(List<Quiz> quizLijst) {
        int logedInStudentID = Main.getUserSession().getUser().getUserId();
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Kies een locatie om het bestand op te slaan");
            fileChooser.setSelectedFile(new File("Quiz_Export_User_" + logedInStudentID + "_" + "Date_" + LocalDate.now() + ".csv"));
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try (PrintWriter printWriter = new PrintWriter(fileToSave)) {
                    StringBuilder printHeaders = new StringBuilder();
                    printHeaders.append(String.format("%-30s, %-20s, %-30s, %-20s, %-20s, %-20s, %-20s", "Quiznaam:", "Moeilijkheidsgraad:", "Cursus:", "Aantal vragen:", "Aantal keer gemaakt:", "Aantal keer succesvol:", "Highscore:"));
                    printWriter.println(printHeaders);
                    for (Quiz quiz : quizLijst) {
                        int numberOfMadeQuizzes = quizDAO.getNumberMadeQuizzes(logedInStudentID,quiz.getQuizId());
                        int numberOfSuccesFullQuizzes = quizDAO.getNumberSuccesQuizzes(logedInStudentID, quiz.getQuizId());
                        int quizHighscore = quizDAO.getQuizHighscore(logedInStudentID, quiz.getQuizId());

                        printWriter.println(String.format("%-30s, %-20s, %-30s, %-20s, %-20s, %-20s, %-20s", quiz.getQuizName(), quiz.getQuizDifficulty(), quiz.getCourse(), quiz.getQuestionsInQuizCount(), numberOfMadeQuizzes, numberOfSuccesFullQuizzes, quizHighscore));
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
