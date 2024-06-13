package controller;
import model.*;
import database.mysql.QuizDAO;
import database.mysql.DBAccess;

import java.util.List;

/*
 * @author Rob JANSEN
 * @project Quizmaster
 * @created 13/06/2024 - 13:30
 */

public class LauncherTestRob {
    public static void main(String[] args) {

        String locationQuestionCSV = "Resources/Quizzes.csv";
        DBAccess dbAccess = new DBAccess("zbakkumm","bakkumm", "1J.cINqCPBBcHJ");
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        Quiz quizMethodHandler = new Quiz(null, null, null, null, null, null);


        List<String> quizInCsv = quizMethodHandler.convertCsvToList(locationQuestionCSV);

        // prints list of converted CSV
        for (String quiz : quizInCsv) {
            System.out.println(quiz);
        }

        List<Quiz> listQuizObjects = quizMethodHandler.convertListToObjects(quizInCsv);

        // prints list of Question objects
        for (Quiz quiz : listQuizObjects) {
            System.out.println(quiz);
        }







        // Quiz quizTestRob2 = new Quiz(1,"Test Quiz 2",2,7,new Course(1,"testCourse",new User(1,"Test Naam","TestPW","Rob","", new Role("Administrator")),new Difficulty("Moeilijk")),new Difficulty("DifQuiz"));

    //    System.out.println(quizTestRob2);


    }
}
