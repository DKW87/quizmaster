package controller;

import database.mysql.*;
import model.Course;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static utils.Util.*;


/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 13 June 2024 - 11:08
 */
public class TextIOLauncher {
    private final static DBAccess dbAccess = Main.getdBaccess();


    public static void main(String[] args) throws SQLException, IOException {

        // Stap 1: bulk create gebruikers TODO: @MacK
        bulkCreateUsers();

        //Stap 2: bulk create courses TODO: @Ekrem
        bulkCreateCourses();

        // Stap 3: bulk create Quizzen TODO: @Rob
        bulkCreateQuizzes();

        // Stap 4: bulk create vragen TODO: @Danny
        bulkCreateQuestions();

    }

    private static void bulkCreateCourses() throws SQLException {
        CourseDAO courseDao = new CourseDAO(dbAccess);
        List<Course> courses = generateCsvListToCourses(convertCsvToArray("resources/Cursussen.csv"));
        courseDao.bulkCreate(courses);
        System.out.println("Cursussen succesvol toegevoegd");
    }

    private static void bulkCreateQuestions() throws SQLException {
        String questionCsv = "Resources/Vragen.csv";
        QuestionDAO questionDao = new QuestionDAO(dbAccess);
        List<String> questionInCsv = convertCsvToArray(questionCsv);
        List<Question> questionList = convertQuestionListToObjects(questionInCsv);
        for (Question question : questionList) {
            questionDao.storeOne(question);
        }
        System.out.println("Vragen succesvol toegevoegd");
    }

    private static void bulkCreateQuizzes() throws SQLException {
        String locationQuestionCSV = "Resources/Quizzen.csv";
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        List<String> quizInCsv = convertCsvToArray(locationQuestionCSV);
        List<Quiz> quizList = convertQuizListToObjects(quizInCsv);
        for (Quiz quiz : quizList) {
            quizDAO.storeOne(quiz);
        }
        System.out.println("Quizzen succesvol toegevoegd");
    }

    private static void bulkCreateUsers() throws SQLException, IOException {
        UserDAO userDAO = new UserDAO(dbAccess);
        // Small CSV to check import into Java.
        String csvFilePath = "resources/Gebruikers.csv";
        List<User> importUsersTest = convertListToUsers(csvFilePath);
        // For loop to import all users to the DB.
        for (User user : importUsersTest) {
            userDAO.storeOne(user);
        }
        System.out.println("Gebruikers succesvol toegevoegd");
    }
}
