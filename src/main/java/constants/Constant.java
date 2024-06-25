package constants;

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

    // ? Database constants
    public static  final String DB_NAME = "zbakkumm";
    public static  final String DB_USER = "bakkumm";
    public static  final String DB_PASS = "1J.cINqCPBBcHJ";

    // ? couchDB constants
    public static  final String COUCH_DB_NAME = "results";
    public static  final String COUCH_DB_USER = "admin";
    public static  final String COUCH_DB_PASS = "admin";

    // ? roles permission constants
    public static final List<MenuItem> STUDENT_TASKS = List.of(
            createMenuItem("Beheer cursussen",event -> Main.getSceneManager().showStudentSignInOutScene()),
            createMenuItem("Beheer quizzent",event -> Main.getSceneManager().showSelectQuizForStudent())

    );
    public static final List<MenuItem> COORDINATOR_TASKS = List.of(
            createMenuItem("Beheer quizzent",event -> Main.getSceneManager().showManageQuizScene()),
            createMenuItem("Beheer vragen",event -> Main.getSceneManager().showManageQuestionsScene()),
            createMenuItem("Dashboard",event -> Main.getSceneManager().showCoordinatorDashboard())

    );
    public static final List<MenuItem> ADMIN_TASKS = List.of(
            createMenuItem("Beheer cursussen",event -> Main.getSceneManager().showManageCoursesScene()),
            createMenuItem("Student indelen",event -> Main.getSceneManager().showAssignStudentsToGroupScene()),
            createMenuItem("Beheer groepen",event -> Main.getSceneManager().showManageGroupsScene())

    );
    public static final List<MenuItem> FUNCTIONAL_BEHEEDER_TASKS = List.of(
            createMenuItem("Beheer gebruikers",event -> Main.getSceneManager().showManageUserScene())

    );
    public static final Map<Integer, List<MenuItem>> ROLE_TASKS =
            Map.of(1,STUDENT_TASKS,3,COORDINATOR_TASKS,4,ADMIN_TASKS,5,FUNCTIONAL_BEHEEDER_TASKS);


}
