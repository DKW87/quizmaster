package utils;

import model.Course;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Juni 2024 - 21:13
 */
public class Util {
    //    private final UserDAO  userDao = new UserDAO(dbAccess);
    //    private final DifficultyDAO difficultyDao = new DifficultyDAO(dbAccess);

    /**
     * Converts a CSV file into a list of strings.
     *
     * @param  cvsPath  the path to the CSV file
     * @return            a list of strings representing each line in the CSV file
     */
    public static List<String> convertCvsToArray(String cvsPath) {
        List<String> cvsList = new ArrayList<>();
        File cvsFile = new File(cvsPath);
        try {
            Scanner fileReader   = new Scanner(cvsFile);
            while (fileReader.hasNextLine()) {
                cvsList.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println( e.getMessage());
        }
        return cvsList;
    }

    /**
     * Generates a list of Course objects from a list of CSV strings.
     *
     * @param  cvsList  a list of CSV strings representing courses
     * @return          a list of Course objects created from the CSV strings
     */
    public static List<Course> generateCvsListToCourses(List<String> cvsList) {
        List<Course> cursusLijst = new ArrayList<>();
        if (!cvsList.isEmpty()) {
            for (String string : cvsList) {
                String[] regelArray = string.split(",");
                String name = regelArray[0];
                String difficultyName = regelArray[1];
                // FIXME : userDao and difficultyDao not implemented
//                User coordinator = userDao.getByName((regelArray[2]));
//                Difficulty difficulty = difficultyDao.getByName(difficultyName);
//                cursusLijst.add(new Course(name, difficulty, coordinator));
            }
        }
        return cursusLijst;
    }

}
