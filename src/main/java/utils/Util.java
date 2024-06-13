package utils;

import database.mysql.DifficultyDAO;
import database.mysql.UserDAO;
import model.Course;
import model.Difficulty;
import model.User;

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



}
