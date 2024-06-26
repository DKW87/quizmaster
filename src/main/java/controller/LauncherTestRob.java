package controller;

import com.google.gson.JsonObject;
import database.couchdb.CouchDBaccess;
import database.couchdb.QuizCouchDBDAO;
import model.*;
import database.mysql.QuizDAO;
import database.mysql.DBAccess;
import view.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.gson.Gson;

import static utils.Util.convertCsvToArray;

/*
 * @author Rob JANSEN
 * @project Quizmaster
 * @created 13/06/2024 - 13:30
 */

public class LauncherTestRob {
    private final static DBAccess dbAccess = Main.getdBaccess();
    private static CouchDBaccess couchDBAccess;
    private static QuizCouchDBDAO quizCouchDBDAO;

    public static void main(String[] args) {

        // onderstaand uitvoering eigen opdracht voor werken met lokale NOSQL DB (COUCHDB)
        // Haalt eerst alle Quizzen op zoals die in de MySQL Remote DB zijn opgeslagen en slaat ze daarna 1-voor-1 op in de CouchDB
        // Verder nog wat tests om een single object van Quiz op te slaan in de COUCH DB | Quiz object omzetten naar een JSON formaat
        // test om een Quiz op te halen uit de CouchDB op basis van DocID

        // toegang tot de lokale CouchDB
        couchDBAccess = new CouchDBaccess("quizzes", "admin", "admin");
        quizCouchDBDAO = new QuizCouchDBDAO(couchDBAccess);

        // ophalen van alle Quizzen in de MySQLDB:
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        List<Quiz> testQuizzes = new ArrayList<>();
        testQuizzes = quizDAO.getAll();

        // print de testQuizzes lijst
        System.out.println(" ----- Lijst met Quizzes in de DB ");
        for (Quiz quiz : testQuizzes) {
            System.out.println(quiz.getQuizId() + " " + quiz.getQuizName() + " " + quiz.getQuizDifficulty() + " " + quiz.getQuizPoints() + " " + quiz.getCourse() + " " + quiz.getQuestionsInQuizCount());
        }

        // testQuizzes omzetten naar een JSON formaat:
        Gson gson = new Gson();
        String jsonTest = gson.toJson(testQuizzes);
        System.out.println(" ----- JSON STRING VAN ALLE QUIZ OBJECTEN:");
        System.out.println(jsonTest);

        // JSON testQuizzes omzetten naar Quiz objecten
        Quiz[] quizJsonToObjectArray = gson.fromJson(jsonTest, Quiz[].class);
        List<Quiz> quizJsonToObjectList = Arrays.asList(quizJsonToObjectArray);

        // printen van de objecten uit de omgezette JSON String van alle Quizzes naar QuizObjecten
        System.out.println(" ");
        System.out.println("-------QUIZZES JSON OMGEZET NAAR OBJECTEN VAN QUIZ ");
        for (Quiz quiz : quizJsonToObjectList) {
            System.out.println(quiz);
        }

        // een object van klasse Quiz aanmaken:
        Role testRole = new Role("JSONTESTROLE");
        Difficulty testDifficulty = new Difficulty("JSONTESTDIFFICULTY");
        User testUserJSON = new User("UserName", "1234", "RobTest", "", "Jansen", testRole);
        Course testCourse = new Course(2, "TestJSONCourse", testUserJSON, testDifficulty);
        Quiz testQuiz = new Quiz(1, "TestQuiz", 10, testCourse, testDifficulty);

        System.out.println(" ");
        System.out.println("------ nieuw object van Quiz voor test: -------");
        System.out.println(testQuiz);

        // testQuiz omzetten naar JSON formaat
        String quizObjectJSON = gson.toJson(testQuiz);
        System.out.println(" ");
        System.out.println(" ---- QuizTest Object in JSON format ----- ");
        System.out.println(quizObjectJSON);

        // JSON formaat van testQuiz weer omgezet naar een object van Quiz
        Quiz test2JSONTOOBJECT = gson.fromJson(quizObjectJSON, Quiz.class);
        System.out.println(" ");
        System.out.println(" ----- JSON QuizTest weer omgezet naar object van Quiz: ---- ");
        System.out.println(test2JSONTOOBJECT.getQuizId() + " " + test2JSONTOOBJECT.getQuizName() + " " + test2JSONTOOBJECT.getQuizPoints() + " " + test2JSONTOOBJECT.getQuizDifficulty());

        // TestQuiz opslaan in COUCH DB (1 object van Quiz):
        System.out.println(" ");
        System.out.println(" -- Quiz opslaan in DB ");
        quizCouchDBDAO.saveSingleQuiz(testQuiz);

        // Test ophalen van Document op basis van DOCID
        System.out.println(" ");
        System.out.println(" ---- Ophalen van Document op basis van DocID: ------ ");
        Quiz quizFromCouchDBonID = quizCouchDBDAO.getSingleQuizByDocId("bdbd957c2e4a4611b3be59c4b1084660");
        System.out.println(quizFromCouchDBonID.getQuizId() + " " + quizFromCouchDBonID.getQuizName() + " " + quizFromCouchDBonID.getQuizPoints() + " " + quizFromCouchDBonID.getQuizDifficulty() + " " + quizFromCouchDBonID.getCourse());

        // Test opslaan van alle Quizzes uit de MySQLDB in de CouchDB
        System.out.println(" ");
        System.out.println(" ----- opslaan van alle Quizzes uit de MySQLDB in de CouchDB ------");
        for (Quiz quiz : testQuizzes) {
            quizCouchDBDAO.saveSingleQuiz(quiz);
        }


        // Alle Quiz documenten ophalen uit de CouchDB
        List<JsonObject> quizListFromCouchDB = quizCouchDBDAO.getAllDocuments();

        for (JsonObject quiz : quizListFromCouchDB) {
            System.out.println(quiz);
        }

        // Alle opgehaalde Quizzen uit de CouchDB weer omzetten naar een List van Quizzen
       List<Quiz> jsonToQuizList = new ArrayList<>();
        for (JsonObject quiz : quizListFromCouchDB) {
            jsonToQuizList.add(gson.fromJson(quiz, Quiz.class));
        }

        // printen van alle QuizObjecten die hierboven zijn omgezet:
        System.out.println(" ");
        System.out.println(" ------ Alle Quizzen uit de CouchDB omgezet naar een List van Quizzen: -----");
        for (Quiz quiz : jsonToQuizList) {
            System.out.println(quiz);
        }


        /*
        // --- onderstaand een aantal oude testen - outcomment omdat niet telkens gebruikt.


        // test of SQL Query werkt om op basis van StudentId Quizzen op te halen waarvoor er bij de cursus is ingeschreven
        System.out.println();
        System.out.println("------ Test lijst met Quizzen die student (met UserId=6 kan maken) -------");
        QuizDAO quizDAO = new QuizDAO(dbAccess);

        List<Quiz> ingeladenQuizStudentidTest = new ArrayList<>();
        ingeladenQuizStudentidTest = quizDAO.getAllQuizzesByStudentId(6);
        for (Quiz quiz : ingeladenQuizStudentidTest) {
            System.out.println(quiz);
        }

        System.out.println(" ");
        System.out.println("------ Test aanmaak object van Quiz-------");

        Role testRole = new Role("Student");
        User testUser = new User("testUserName","1234","TestRob","TestJansen",testRole);
        Difficulty testDifficulty = new Difficulty("Beginner");
        Course testCourse = new Course("testCourseName",testUser,testDifficulty);
        Quiz testQuiz = new Quiz(1,"testQuiz",10,testCourse,testDifficulty);

        System.out.println(testQuiz);

        System.out.println();
        System.out.println("-----test aantal keer gemaakt" );
        int aantalGemaakt;
        aantalGemaakt =quizDAO.getNumberMadeQuizzes(206,1);
        System.out.println(aantalGemaakt);

        System.out.println();
        System.out.println("-----test aantal keer gehaald" );
        int aantalGehaald;
        aantalGehaald =quizDAO.getNumberSuccesQuizzes(206,1);
        System.out.println(aantalGehaald);

        System.out.println();
        System.out.println("-----test highscore" );
        int highscore;
        highscore =quizDAO.getQuizHighscore(206,1);
        System.out.println(highscore);

*/


    }
}
