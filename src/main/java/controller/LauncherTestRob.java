package controller;
import model.*;
import database.mysql.QuizDAO;
import database.mysql.DBAccess;
import view.Main;

import java.util.List;

import static utils.Util.convertCsvToArray;

/*
 * @author Rob JANSEN
 * @project Quizmaster
 * @created 13/06/2024 - 13:30
 */

public class LauncherTestRob {
    private final static DBAccess dbAccess = Main.getdBaccess();
    public static void main(String[] args) {

        String locationQuestionCSV = "Resources/Quizzen.csv";
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        Quiz quizMethodHandler = new Quiz(0, null, 0, 0, null, null);


        List<String> quizInCsv = convertCsvToArray(locationQuestionCSV);

        // prints list of converted CSV
        for (String quiz : quizInCsv) {
            System.out.println(quiz);
        }

//  Onderstaande werkt nog niet omdat uit de DB nog data moet kunnen worden gehaald (CourseId en DifficultyId)
 //       List<Quiz> listQuizObjects = quizMethodHandler.convertListToObjects(quizInCsv);

 //       // prints list of Question objects
 //       for (Quiz quiz : listQuizObjects) {
 //           System.out.println(quiz);
 //       }







        // Quiz quizTestRob2 = new Quiz(1,"Test Quiz 2",2,7,new Course(1,"testCourse",new User(1,"Test Naam","TestPW","Rob","", new Role("Administrator")),new Difficulty("Moeilijk")),new Difficulty("DifQuiz"));

    //    System.out.println(quizTestRob2);


    }
}
