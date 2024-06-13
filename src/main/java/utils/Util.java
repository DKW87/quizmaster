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
     * @param  csvPath  the path to the CSV file
     * @return            a list of strings representing each line in the CSV file
     */
    public static List<String> convertCsvToArray(String csvPath) {
        List<String> csvList = new ArrayList<>();
        File csvFile = new File(csvPath);
        try {
            Scanner fileReader   = new Scanner(csvFile);
            while (fileReader.hasNextLine()) {
                csvList.add(fileReader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println( e.getMessage());
        }
        return csvList;
    }

    /**
     * Generates a list of Course objects from a list of CSV strings.
     *
     * @param  csvList  a list of CSV strings representing courses
     * @return          a list of Course objects created from the CSV strings
     */
    public static List<Course> generateCsvListToCourses(List<String> csvList) {
        List<Course> courses= new ArrayList<>();
        if (!csvList.isEmpty()) {
            for (String string : csvList) {
                String[] line = string.split(",");
                String name = line[0];
                String difficultyName = line[1];
                // FIXME : userDao and difficultyDao not implemented
//                User coordinator = userDao.getByName((line[2]));
//                Difficulty difficulty = difficultyDao.getByName(difficultyName);
//                cursusLijst.add(new Course(name, difficulty, coordinator));
            }
        }
        return courses;
    }

}
