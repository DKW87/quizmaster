package controller.launcher;

import database.couchdb.CouchDBaccess;
import database.couchdb.CourseCouchDBDAO;
import database.mysql.*;
import model.Course;
import view.Main;

import java.util.List;

import static constants.Constant.*;
import static utils.Util.convertCsvToArray;
import static utils.Util.generateCsvListToCourses;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 15:33
 */
public class CouchDBCourseTestLauncher {

    private static final CouchDBaccess couchDBaccess = new CouchDBaccess("courses",
            COUCH_DB_USER, COUCH_DB_PASS);
    private static final DBAccess dbAccess= Main.getdBaccess();

    private static final CourseCouchDBDAO courseCouchDBDAO = new CourseCouchDBDAO(couchDBaccess);
    private static final CourseDAO courseDAO = new CourseDAO(dbAccess);

    public static void main(String[] args) {

//        saveCourses(buildCourseListCvs());
        saveCourses(buildCourseListDb());
        deleteCourses(courseCouchDBDAO.getAllCourses());


    }

    private static List<Course> buildCourseListDb() {
        // get all courses from database
        return courseDAO.getAll();
    }  private static List<Course> buildCourseListCvs() {
        // get all courses from csv
        return generateCsvListToCourses(convertCsvToArray("resources/Cursussen.csv"));
    }

    private static void saveCourses(List<Course> courseList) {
        if (couchDBaccess.getClient() != null) {
            System.out.println("Connectie gemaakt");
            for (Course course : courseList) {
                courseCouchDBDAO.saveCourse(course);
            }
        }
    }
    private static void deleteCourses(List<Course> courseList) {
        if (couchDBaccess.getClient() != null) {
            System.out.println("Connectie gemaakt");
            for (Course course : courseList) {
                courseCouchDBDAO.deleteCourse(course);
            }
        }
    }

}

