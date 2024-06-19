package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import model.*;
import view.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 13/06/2024 - 10:46
 */
public class DannysTestLauncher {

    private final static DBAccess dbAccess = Main.getdBaccess();

    public static void main(String[] args) {

        Question question = new Question("adjasdjadhja", "a", "b",
                "c", "d", null);
        System.out.println(question);
//
//        String locationQuestionCSV = "Resources/Vragen.csv";
//        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
//        Question questionMethodHandler = new Question(null, null, null, null, null, null);
//
//
//        // converts CSV to a string list
//        List<String> questionsInCsv = questionMethodHandler.convertCsvToList(locationQuestionCSV);
//
//        for (String question : questionsInCsv) {
//            System.out.println(question);
//        }
//
//        // create list of Question objects based on converted CSV
//        List<Question> listQuestionObjects = questionMethodHandler.convertListToObjects(questionsInCsv);
//
//        for (Question question : listQuestionObjects) {
//            System.out.println(question);
//        }
//
//        // save list of Question objects to DB
////        questionDAO.storeList(listQuestionObjects); questions already stored in DB
//
//        // create list of all Question records in DB
//        List<Question> remoteQuestions = questionDAO.getAll();
//        dbAccess.closeConnection();
//
//        for (Question question : remoteQuestions) {
//            System.out.println(question);
//        }
//

    } // main

} // class
