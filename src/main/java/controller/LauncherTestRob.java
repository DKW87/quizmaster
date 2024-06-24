package controller;
import model.*;
import database.mysql.QuizDAO;
import database.mysql.DBAccess;
import view.Main;

import java.util.ArrayList;
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






    }
}
