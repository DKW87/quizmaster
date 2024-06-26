package view;

import database.couchdb.CouchDBaccess;
import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.stage.Stage;
import model.UserSession;

import static constants.Constant.*;

public class Main extends Application {



    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    private static DBAccess dBaccess = null;
    private static CouchDBaccess couchDBaccess = null;
    private static UserSession userSession = null;

    private static  boolean TEST_MODE = false;

    public static void main(String[] args) {
    //    TEST_MODE = false;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Make IT Work | D-rem Stacks - Quizmaster");
        primaryStage.setResizable(false);
        getSceneManager().showLoginScene();
        primaryStage.show();
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

    /**
     * Returns an instance of the DBAccess class, creating it if it doesn't already exist.
     *
     * @return an instance of the DBAccess class
     */
    public static DBAccess getdBaccess() {
        if (dBaccess == null) {
            // Create an instance of the DBAccess class
            dBaccess = new DBAccess(DB_NAME, DB_USER, DB_PASS);
            if (TEST_MODE) {
                dBaccess = new DBAccess("QuizMaster", "userQuizMaster", "pwQuizMaster");
            }
            dBaccess.openConnection();
        }
        return dBaccess;
    }

    /**
     * Returns an instance of the CouchDBaccess class, creating it if it doesn't already exist.
     *
     * @return an instance of the CouchDBaccess class
     */
    public static CouchDBaccess getCouchDBaccess() {
        if (couchDBaccess == null) {
            // Create an instance of the DBAccess class
            couchDBaccess = new CouchDBaccess(COUCH_DB_NAME, COUCH_DB_USER, COUCH_DB_PASS);
            couchDBaccess.getClient();
        }
        return couchDBaccess;
    }

    /**
     * Returns the UserSession object, creating it if it doesn't already exist.
     *
     * @return the UserSession object
     */
    public static UserSession getUserSession() {
        if (userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static boolean getTestMode() {
        return TEST_MODE;
    }
}