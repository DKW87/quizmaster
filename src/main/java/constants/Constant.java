package constants;

import database.mysql.QuizResultDAO;
import javafx.scene.control.MenuItem;
import view.Main;

import java.util.List;
import java.util.Map;

import static utils.Util.createMenuItem;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 15 June 2024 - 11:50
 */
public class Constant {

    private static final QuizResultDAO quizResultDAO = new QuizResultDAO(Main.getdBaccess());

    // ? Database constants
    public static  final String DB_NAME = "zbakkumm";
    public static  final String DB_USER = "bakkumm";
    public static  final String DB_PASS = "1J.cINqCPBBcHJ";

    // ? Test Database constants
    public static  final String TEST_DB_NAME = "QuizMaster";
    public static  final String TEST_DB_USER = "userQuizMaster";
    public static  final String TEST_DB_PASS = "pwQuizMaster";

    // ? couchDB constants
    public static  final String COUCH_DB_NAME = "results";
    public static  final String COUCH_DB_USER = "admin";
    public static  final String COUCH_DB_PASS = "admin";


    // ? roles permission constants
    public static final List<MenuItem> STUDENT_TASKS = List.of(
            createMenuItem("In- en/of uitschrijven voor cursus(sen)",event -> Main.getSceneManager().showStudentSignInOutScene()),
            createMenuItem("Quiz maken",event -> Main.getSceneManager().showSelectQuizForStudent())
    );
    public static final List<MenuItem> COORDINATOR_TASKS = List.of(
            createMenuItem("Coördinator Dashboard",event -> Main.getSceneManager().showCoordinatorDashboard()),
            createMenuItem("Vraagbeheer",event -> Main.getSceneManager().showManageQuestionsScene()),
            createMenuItem("Quizbeheer",event -> Main.getSceneManager().showManageQuizScene())
    );
    public static final List<MenuItem> ADMINISTRATOR_TASKS = List.of(
            createMenuItem("Cursusbeheer",event -> Main.getSceneManager().showManageCoursesScene()),
            createMenuItem("Exporteer studentenresultaten",event -> quizResultDAO.exportResultAdminExport())
//            createMenuItem("Student indelen",event -> Main.getSceneManager().showAssignStudentsToGroupScene())
//            createMenuItem("Beheer groepen",event -> Main.getSceneManager().showManageGroupsScene())
    );
    public static final List<MenuItem> FUNCTIONAL_BEHEERDER_TASKS = List.of(
            createMenuItem("Gebruikersbeheer",event -> Main.getSceneManager().showManageUserScene())
    );
    public static final Map<Integer, List<MenuItem>> ROLE_TASKS =
            Map.of(1,STUDENT_TASKS,2,COORDINATOR_TASKS,4,ADMINISTRATOR_TASKS,5, FUNCTIONAL_BEHEERDER_TASKS);

}