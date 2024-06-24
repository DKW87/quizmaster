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

        QuizDAO quizDAO = new QuizDAO(dbAccess);

        List<Quiz> ingeladenQuizStudentidTest = new ArrayList<>();
        ingeladenQuizStudentidTest = quizDAO.getAllQuizzesByStudentId(6);
        for (Quiz quiz : ingeladenQuizStudentidTest) {
            System.out.println(quiz);
        }







    }
}
