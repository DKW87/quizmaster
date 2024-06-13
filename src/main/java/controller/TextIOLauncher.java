package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import model.Course;
import view.Main;

import java.sql.SQLException;
import java.util.List;

import static utils.Util.convertCsvToArray;


/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 13 June 2024 - 11:08
 */
public  class TextIOLauncher {
private final static DBAccess dbAccess = Main.getdBaccess();



    public static void main(String[] args) throws SQLException {
        CourseDAO courseDao = new CourseDAO(dbAccess);

        // Stap 1: bulk create gebruikers TODO: @MacK
         //Stap 2: bulk create courses
            bulkCreateCourses();
        // Stap 3: bulk create Quizzen TODO: @Rob
        // Stap 4: bulk create vragen TODO: @Danny
    }

    private static void  bulkCreateCourses() throws SQLException {
        CourseDAO courseDao = new CourseDAO(dbAccess);
        List<Course> courses = courseDao.generateCsvListToCourses(convertCsvToArray("resources/Cursussen.csv"));
        courseDao.bulkCreate(courses);
        System.out.println("Cursussen succesvol toegevoegd");
    }
}
