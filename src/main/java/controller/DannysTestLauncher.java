package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 13/06/2024 - 10:46
 */
public class DannysTestLauncher {

    public static void main(String[] args) {

        String locationQuestionCSV = "Resources/Vragen.csv";
        DBAccess dbAccess = new DBAccess("zbakkumm","bakkumm", "1J.cINqCPBBcHJ");
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        Question questionMethodHandler = new Question(null, null, null, null, null, null);


        List<String> questionsInCsv = questionMethodHandler.convertCsvToList(locationQuestionCSV);

        // prints list of converted CSV
        for (String question : questionsInCsv) {
            System.out.println(question);
        }

        List<Question> listQuestionObjects = questionMethodHandler.convertListToObjects(questionsInCsv);

        // prints list of Question objects
        for (Question question : listQuestionObjects) {
            System.out.println(question);
        }

          // can't get the saving working because the connection is null :(
//        dbAccess.openConnection();
//        questionDAO.storeList(listQuestionObjects);
//        List<Question> remoteQuestions = questionDAO.getAll();
//        dbAccess.closeConnection();
//
//        for (Question question : remoteQuestions) {
//            System.out.println(question);
//        }


    } // main

} // class
