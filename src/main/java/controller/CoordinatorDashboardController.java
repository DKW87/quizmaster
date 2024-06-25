package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;
import view.Main;
import view.SceneManager;
import model.Question;

import java.sql.SQLException;
import java.util.List;

public class CoordinatorDashboardController {

    private final SceneManager sceneManager = Main.getSceneManager();
    private final DBAccess dbAccess = Main.getdBaccess();
    private final CourseDAO courseDao = new CourseDAO(dbAccess);
    private final QuizDAO quizDao = new QuizDAO(dbAccess);
    private final QuestionDAO questionDAO = new QuestionDAO(dbAccess);

    @FXML
    public Button newQuizButton;
    @FXML
    public Button changeQuizButton;
    @FXML
    public Button newQuestionButton;
    @FXML
    public Button changeQuestionButton;
    @FXML
    public Button menuButton;
    @FXML
    public ListView<Course> courseList;
    @FXML
    public ListView<Quiz> quizList;
    @FXML
    public ListView<Question> questionList;
    @FXML
    public TableColumn<Course, String> courseNameColumn;
    @FXML
    public TableColumn<Quiz, String> quizNameColumn;
    @FXML
    public TableColumn<Question, String> questionColumn;


    public void setup() {
        List<Course> courseView = courseDao.getCoursesByCoordinator(Main.getUserSession().getUser().getUserId());
        courseList.getItems().setAll(courseView);
        // Listener for course selection
        courseList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    loadQuizForCourse(newSelection.getCourseId());
                } catch (SQLException sqlErrror) {
                    System.out.println("Error in listener for course  " + sqlErrror.getMessage()
                    + sqlErrror.getErrorCode());
                }
            }
        });
        // Listener for quiz selection
       quizList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    loadQuestionForQuiz(newSelection.getQuizId());
                } catch (SQLException sqlErrror) {
                    System.out.println("Error in listener for quiz  " + sqlErrror.getMessage());
                }
            }
        });
    }

    public void doNewQuiz() {
        sceneManager.showCreateUpdateQuizScene(null);
    }

    public void doEditQuiz() {
        Quiz quiz = (Quiz) quizList.getSelectionModel().getSelectedItem();
        sceneManager.showCreateUpdateQuizScene(quiz);
    }

    public void doNewQuestion() {
        sceneManager.showCreateUpdateQuestionScene(null);
    }

    public void doEditQuestion() {
        Question question = (Question) questionList.getSelectionModel().getSelectedItem();
        sceneManager.showCreateUpdateQuestionScene(question);
    }

    public void doMenu() {
        sceneManager.showWelcomeScene();
    }

    // Method that fills the Course Table based on userId.
    private void generateCourseTable() {
        courseDao.getCoursesByCoordinator(Main.getUserSession().getUser().getUserId());
        quizNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCourse().getCourseId()))); // Methode in CourseDAO maken getByName
    }

    // Method that fills the Quiz Table.
    private void generateQuizTable() {
        quizNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuizName())));
    }

    private void loadQuizForCourse(int courseId) throws SQLException {
        quizList.getItems().clear(); // This will clear previous quizzes once a new course is selected I think
        List<Quiz> quizzes = quizDao.getAllQuizzesByCourseId(courseId);
        quizList.getItems().addAll(quizzes);
    }

    private void loadQuestionForQuiz(int quizId) throws SQLException {
        questionList.getItems().clear(); // This will clear previous quizzes once a new course is selected I think
        List<Question> questions = questionDAO.getAllByQuizId(quizId);
        questionList.getItems().addAll(questions);
    }
}
