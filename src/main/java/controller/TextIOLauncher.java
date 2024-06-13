package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import model.Course;
import view.Main;

import java.sql.SQLException;
import java.util.List;

import static utils.Util.convertCvsToArray;
import static utils.Util.generateCvsListToCourses;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 13 June 2024 - 11:08
 */
public  class TextIOLauncher {
    private final static DBAccess dBaccess = Main.getdBaccess();
    private static final CourseDAO courseDao = new CourseDAO(dBaccess);


    public static void main(String[] args) throws SQLException {

        // Stap 1: bulk create gebruikers TODO: @MacK
        //        List<User> users = generateCvsListToUsers(convertCvsToArray("resources/Gebruikers.csv"));
        //        userDao.bulkCreate(courses);

        // Stap 2: bulk create courses
        List<Course> courses = generateCvsListToCourses(convertCvsToArray("resources/Cursussen.csv"));
        courseDao.bulkCreate(courses);

        // Stap 3: bulk create Quizzen TODO: @Rob
        //        List<Quiz> quizs = generateCvsListToQuizzes(convertCvsToArray("resources/Quizzen.csv"));
        //        quizDao.bulkCreate(quizs);

        // Stap 4: bulk create vragen TODO: @Danny
        //        List<Question> questions = generateCvsListToQuestions(convertCvsToArray("resources/Vragen.csv"));
        //        quizDao.bulkCreate(quizzen);
    }
}
