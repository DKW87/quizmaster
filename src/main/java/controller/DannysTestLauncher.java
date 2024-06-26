package controller;

import database.couchdb.CouchDBaccess;
import database.couchdb.QuestionCouchDBDAO;
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

    private final static CouchDBaccess couchDBaccess = new CouchDBaccess("question", "admin", "admin");
    private final static QuestionCouchDBDAO questionCouchDBDAO = new QuestionCouchDBDAO(couchDBaccess);
    private final static QuestionDAO questionDAO = new QuestionDAO(Main.getdBaccess());


    public static void main(String[] args) {

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        *  This launcher and QuestionCouchDBDAO serves to demonstrate  *
        *  my ability to be able to store in and read from my local    *
        *  CoachDB using NoSQL. This was made for my portfolio.        *
        * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        // getAll from MySQL and saveAll in NoSQL
        List<Question> questionsList = new ArrayList<>(questionDAO.getAll());
        questionCouchDBDAO.saveAllQuestions(questionsList);

        // print result from MySQL
        System.out.println(questionsList.get(0));

        // Query CouchDB with PK based on 3 unique param: questionDescription, answerA and quizId
        Question questionFromJSON = questionCouchDBDAO.getAllQuestions(questionsList.get(0).getQuestionDescription(),
                questionsList.get(0).getAnswerA(), questionsList.get(0).getQuiz().getQuizId());

        // print result from NoSQL
        System.out.println(questionFromJSON);

    } // main

} // class
