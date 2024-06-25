package constants;

import javafx.scene.control.MenuItem;

import java.util.List;

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
            new MenuItem("Cursusbeheer"),
            new MenuItem("Quiz maken")
    );
    public static final List<MenuItem> COORDINATOR_TASKS = List.of(
            new MenuItem("Coördinator Dashboard"),
            new MenuItem("Vraagbeheer"),
            new MenuItem("Quizbeheer")
    );
    public static final List<MenuItem> ADMINISTRATOR_TASKS = List.of(
            new MenuItem("Cursusbeheer"),
            new MenuItem("Exporteer studentenresultaten")
    );
    public static final List<MenuItem> FB_TASKS = List.of(
            new MenuItem("Gebruikersbeheer")
    );

    // Administrator > cursussenbeheer > voor alle studenten die 1 quiz hebben gedaan een overzicht maken
    // FB > Gebruikersbeheer

    // ? style constants
    public static final String PRIMARY_COLOR = "#156082";
    public static final String SECONDARY_COLOR = "#E89C31";
    public static final String ERROR_COLOR = "#f77167";
    public static final String SUCCESS_COLOR = "#36c95f";

}
