package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import model.QuizIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizProcesserIO {
    public static void main(String[] args) {

// read CSV file
        String csvFileQuiz = "Resources/Quizzen.csv";
        String line;
        String csvSplitBy = ",";
        List<QuizIO> quizList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileQuiz))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                QuizIO quizData = new QuizIO(data[0], data[1], Integer.parseInt(data[2]), data[3]);
                quizList.add(quizData);
            }
        } catch (IOException fout) {
            System.out.println("File not found");
        }
// print read CSV file to console (for checking)
        int quizNumber = 0;
        for (QuizIO quiz : quizList) {
            quizNumber++;
            System.out.printf("Quiznumber %d ", quizNumber);
            System.out.println(quiz);
        }
/*
//  open connection to Database
        DBAccess dbAccess = new DBAccess("jdbc:mysql://oege.ie.hva.nl:3306/zbakkum", "bakkumm", "1J.cINqCPBBcHJ");
        dbAccess.openConnection();

// store data retrieved from CSV file (added to quizList) into DATABASE:
        QuizDAO quizDAO = new QuizDAO(dbAccess);
        for (QuizIO quiz : quizList) {
            quizDAO.saveQuizInDB(quiz);
        }

// close connection to Database
        dbAccess.closeConnection();
*/
    }

}

