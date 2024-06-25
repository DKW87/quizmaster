package utils;

import database.mysql.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import model.*;
import view.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static constants.Constant.ROLE_TASKS;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Juni 2024 - 21:13
 */
public class Util {
    private final static DBAccess dbAccess = Main.getdBaccess();
    private final static DifficultyDAO difficultyDao = new DifficultyDAO(dbAccess);
    private final static UserDAO userDao = new UserDAO(dbAccess);
    private final static RoleDAO roleDAO = new RoleDAO(dbAccess);
    private final static CourseDAO courseDAO = new CourseDAO(dbAccess);
    private final static QuizDAO quizDAO = new QuizDAO(dbAccess);



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
                User coordinator = userDao.getByName((line[2]));
                Difficulty difficulty = difficultyDao.getByName(line[1]);
                courses.add(new Course(name, coordinator, difficulty));
            }
        }
        return courses;
    }

    public static List<User> convertListToUsers(String csvFilePath) throws IOException {
        List<User> users = new ArrayList<>();
        final int IMPORT_WITH_INFIX = 6;
        final int IMPORT_NO_INFIX = 5;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Can be used if CSV has a header. It'll skip the first line.
            while ((line = br.readLine()) != null) {
                String[] userValues = line.split(",");
                if (userValues.length == IMPORT_WITH_INFIX) { // For users with infix
                    Role userRole = roleDAO.getByName(userValues[5]);
                    User user = new User(userValues[0], userValues[1], userValues[2], userValues[3], userValues[4], userRole);
                    users.add(user);
                    System.out.println("Added user with infix");
                } if (userValues.length == IMPORT_NO_INFIX) { // For users without infix
                    Role userRole = roleDAO.getByName(userValues[4]);
                    User user = new User(userValues[0], userValues[1], userValues[2], userValues[3], userRole);
                    users.add(user);
                    System.out.println("Added user with no infix");
                }
            }
        }
        return users;
    }

    public static List<Question> convertQuestionListToObjects(List<String> list) {
        List<Question> questionList = new ArrayList<>();
        for (String string : list) {
            String[] splitLine = string.split(";");
            String questionDescription = splitLine[0];
            String answerA = splitLine[1];
            String answerB = splitLine[2];
            String answerC = splitLine[3];
            String answerD = splitLine[4];
            Quiz quiz = quizDAO.getByName(splitLine[5]);
            Question question = new Question(questionDescription, answerA, answerB, answerC, answerD, quiz);
            questionList.add(question);
        }
        return questionList;
    }
    public static List<Quiz> convertQuizListToObjects(List<String> list) {
        List<Quiz> quizList = new ArrayList<>();
        for (String string : list) {
            String[] splitLine = string.split(",");
            String quizName = splitLine[0];
            String difficultyString = splitLine[1];
            String quizPointsString = splitLine[2];
            String courseString = splitLine[3];
            int quizId = 0;
            int quizPoints = Integer.parseInt(quizPointsString);
            var difficulty = difficultyDao.getByName(difficultyString);
            var course = courseDAO.getByName(courseString);
            var questionInQuizCount = quizDAO.getQuestionsInQuizCount(quizId);
            Quiz quiz = new Quiz(quizId,quizName,quizPoints,course,difficulty,questionInQuizCount);
            quizList.add(quiz);
        }
        return quizList;
    }

    /**
     * Displays an alert with the specified alert type, title, and message.
     *
     * @param  alertType  the type of alert to display
     * @param  title      the title of the alert
     * @param  message    the message to display in the alert
     */
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setContentText(contentText);
        alert.show();
    }
    public static boolean confirmMessage(String title,String message) {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> option = alert.showAndWait();
        return option.get().equals(ButtonType.OK);

    }

    public static MenuItem createMenuItem(String title, EventHandler<ActionEvent> event ) {
        MenuItem menuItem = new MenuItem(title);
        menuItem.setOnAction(event);
        return menuItem;
    }
    public static List<MenuItem> configureMenuItems(int roleId)  {
        return ROLE_TASKS.get(roleId);
    }


}
