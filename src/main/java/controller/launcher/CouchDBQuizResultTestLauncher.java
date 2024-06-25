package controller.launcher;

import database.couchdb.CouchDBaccess;
import database.couchdb.QuizResultCouchDBDAO;
import model.QuizResult;
import model.QuizResultDTO;
import view.Main;

import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 25 June Tuesday 2024 - 13:13
 */
public class CouchDBQuizResultTestLauncher {

    private static final CouchDBaccess couchDBaccess = Main.getCouchDBaccess();
    private static final QuizResultCouchDBDAO quizResultCouchDBDAO = new QuizResultCouchDBDAO(couchDBaccess);


    public static void main(String[] args) {
        // * create quiz result with quiz and user fixme @Ekrem : resulId auto increment
        QuizResultDTO quizResult = new QuizResultDTO(1, 1, 1, 12);
        QuizResultDTO quizResult2 = new QuizResultDTO(1, 3, 1, 12);
        quizResultCouchDBDAO.saveQuizResult(quizResult);
        quizResultCouchDBDAO.saveQuizResult(quizResult2);
        List<QuizResult> quizResults = quizResultCouchDBDAO.getAllQuizResults();
        for (QuizResult qr : quizResults) {
            System.out.println(qr.getDate());
        }
    }

}
