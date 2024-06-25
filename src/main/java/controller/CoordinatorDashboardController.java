package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.*;
import view.Main;
import view.SceneManager;

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
    public TableView<Course> selectCourseTable;
    @FXML
    public TableView<Quiz> selectQuizTable;
    @FXML
    public TableView<Question> selectQuestionTable;


    public void setup() {
        List<Course> courseView = courseDao.getCoursesByCoordinator(Main.getUserSession().getUser().getUserId());
        for (Course course : courseView) {
            selectCourseTable.getItems().add(course);
        }
        generateCourseTable();
    }

    public void doNewQuiz() {
        sceneManager.showCreateUpdateQuizScene(null);
    }

    public void doEditQuiz() {
        Quiz quiz = (Quiz) selectQuizTable.getSelectionModel().getSelectedItem();
        sceneManager.showCreateUpdateQuizScene(quiz);
    }

    public void doNewQuestion() {
        sceneManager.showCreateUpdateQuestionScene(null);
    }

    public void doEditQuestion() {
        Question question = (Question) selectQuestionTable.getSelectionModel().getSelectedItem();
        sceneManager.showCreateUpdateQuestionScene(question);
    }

    public void doMenu() {
        sceneManager.showWelcomeScene();
    }

    private void generateCourseTable() {
        courseDao.getCoursesByCoordinator(Main.getUserSession().getUser().getUserId());
    }

    private void loadQuizForCourse(int courseId) throws SQLException {
        selectQuizTable.getItems().clear(); // This will clear previous quizzes once a new course is selected I think
        List<Quiz> quizzes = quizDao.getAllQuizzesByCourseId(courseId);
        selectQuizTable.getItems().addAll(quizzes);
    }

    private void loadQuestionForQuiz(int quizId) throws SQLException {
        selectQuestionTable.getItems().clear();
        List<Question> questions = questionDAO.getAllByQuizId(quizId);
        selectQuestionTable.getItems().addAll(questions);
    }

}
