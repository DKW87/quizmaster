package controller.launcher;

import database.couchdb.CouchDBaccess;
import database.couchdb.CourseCouchDBDAO;
import database.couchdb.QuizResultCouchDBDAO;
import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import database.mysql.UserDAO;
import model.QuizResult;
import view.Main;

import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 25 June Tuesday 2024 - 13:13
 */
public class CouchDBQuizREsultTestLauncher {

    private static final CouchDBaccess couchDBaccess= Main.getCouchDBaccess();
    private static final DBAccess dbAccess= Main.getdBaccess();

    private static final QuizResultCouchDBDAO quizResultCouchDBDAO = new QuizResultCouchDBDAO(couchDBaccess);
    private static final UserDAO userDAO = new UserDAO(dbAccess);
    private static final QuizDAO quizDAO = new QuizDAO(dbAccess);

    public static void main(String[] args) {
        // * create quiz result with quiz and user fixme @Ekrem : resulId auto increment
        QuizResult quizResult = new QuizResult(1,userDAO.getById(1),quizDAO.getById(1),12 );
        QuizResult quizResult2 = new QuizResult(1,userDAO.getById(3),quizDAO.getById(1),12 );
        quizResultCouchDBDAO.saveQuizResult(quizResult);
        quizResultCouchDBDAO.saveQuizResult(quizResult2);

        List<QuizResult> quizResults = quizResultCouchDBDAO.getAllQuizResults(1,1);
        System.out.println(quizResults.size());
    }

}
