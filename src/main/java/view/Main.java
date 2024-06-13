package view;

import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static  final String DBNAME = "zbakkumm";
    private static  final String DB_USER = "bakkumm";
    private static  final String DB_PASS = "1J.cINqCPBBcHJ";

    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    private static DBAccess dBaccess = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Make IT Work - Project 1");
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
            dBaccess = new DBAccess(DBNAME, DB_USER, DB_PASS);
            dBaccess.openConnection();
        }
        return dBaccess;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}