package database.mysql;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 14 juni 2024 - 10:00
 */

public class QuizDAO extends AbstractDAO implements GenericDAO<Quiz> {
    public QuizDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    private final DifficultyDAO difficultyDao = new DifficultyDAO(dbAccess);
    private final CourseDAO courseDao = new CourseDAO(dbAccess);


    // methode om 1 quiz op te slaan in de DB
    @Override
    public void storeOne(Quiz quiz) {
        String sql = "INSERT INTO Quiz(name, difficultyId, quizPoints,courseId) VALUES (?, ?, ? ,?)";
        try {
            setupPreparedStatementWithKey(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setInt(2, quiz.getQuizDifficulty().getDifficultyId());
            preparedStatement.setInt(3, quiz.getQuizPoints());
            preparedStatement.setInt(4, quiz.getCourse().getCourseId());
            int primaryKey = executeInsertStatementWithKey();
            quiz.setQuizId(primaryKey);
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        }
    }

    // methode om alle Quizzen uit de DB op te halen
    @Override
    public List<Quiz> getAll() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quiz";

        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String name = resultSet.getString("name");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyId");
                int courseId = resultSet.getInt("courseId");
                var difficulty = difficultyDao.getById(difficultyId);
                var course = courseDao.getById(courseId);
                var questionInQuizCount = getQuestionsInQuizCount(quizId);
                Quiz quiz = new Quiz(quizId, name, quizPoints, course, difficulty, questionInQuizCount);
                quizzes.add(quiz);
            }
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        }
        return quizzes;

    }

    // methode om een Quiz uit de DB te halen op basis van een quizID
    @Override
    public Quiz getById(int quizId) {
        Quiz quiz = null;
        String sql = "SELECT * FROM Quiz WHERE quizId = ?";
        try {
            this.setupPreparedStatement(sql);
            preparedStatement.setInt(1, quizId);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyID");
                int courseId = resultSet.getInt("courseID");
                // ophalen van de juiste IDs uit DB:
                var difficulty = difficultyDao.getById(difficultyId);
                var course = courseDao.getById(courseId);
                var questionInQuizCount = getQuestionsInQuizCount(quizId);
                quiz = new Quiz(quizId, name, quizPoints, course, difficulty, questionInQuizCount);
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Fout" + sqlError.getMessage());
        }
        return quiz;
    }

    // methode om een Quiz uit de DB te halen op basis van de Quiznaam
    @Override
    public Quiz getByName(String name) {
        Quiz quiz = null;
        String sql = "SELECT * FROM Quiz WHERE name = ?";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, name);
            ResultSet resultSet = this.executeSelectStatement();
            if (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyID");
                int courseId = resultSet.getInt("courseID");
                // ophalen van de juiste IDs uit DB:
                var difficulty = difficultyDao.getById(difficultyId);
                var course = courseDao.getById(courseId);
                var questionInQuizCount = getQuestionsInQuizCount(quizId);
                quiz = new Quiz(quizId, name, quizPoints, course, difficulty, questionInQuizCount);
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Fout" + sqlError.getMessage());
        }
        return quiz;
    }

    // methode om een bestaande Quiz te updaten in de DB
    @Override
    public void updateOne(Quiz quiz) {
        String sql = "UPDATE Quiz SET name = ?, difficultyId = ?," +
                " quizPoints = ?, courseId = ? WHERE quizId = ?";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setString(1, quiz.getQuizName());
            this.preparedStatement.setInt(2, quiz.getQuizDifficulty().getDifficultyId());
            this.preparedStatement.setInt(3, quiz.getQuizPoints());
            this.preparedStatement.setInt(4, quiz.getCourse().getCourseId());
            this.preparedStatement.setInt(5, quiz.getQuizId());
            this.executeManipulateStatement();
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
    }

    ;

    // methode om een Quiz in de DB te verwijderen op basis van een QuizID
    @Override
    public void deleteOneById(int quizId) {
        String sql = "DELETE FROM Quiz WHERE quizId = ?";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, quizId);
            this.preparedStatement.executeUpdate();
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
    }

    // methode telt het aantal vragen dat bij een Quiz hoort
    public int getQuestionsInQuizCount(int quizId) {
        String sql = "select count(*) as questionCount from Question WHERE quizId = ?";
        int questionCount = 0;
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, quizId);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                questionCount = resultSet.getInt("questionCount");
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return questionCount;
    }

    // methode om alle Quizzen in de DB op te halen op basis van coordinatorID
    public List<Quiz> getAllQuizzesByCoordinator(int coordinatorId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quiz WHERE courseId IN (SELECT courseId FROM Course WHERE coordinatorId = ?)";
        try {
            this.setupPreparedStatement(sql);
            preparedStatement.setInt(1, coordinatorId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String name = resultSet.getString("name");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyId");
                int courseId = resultSet.getInt("courseId");
                var difficulty = difficultyDao.getById(difficultyId);
                var course = courseDao.getById(courseId);
                var questionInQuizCount = getQuestionsInQuizCount(quizId);
                Quiz quiz = new Quiz(quizId, name, quizPoints, course, difficulty, questionInQuizCount);
                quizzes.add(quiz);
            }
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        }
        return quizzes;
    }

    // methode om alle Quizzen op te halen voor een student (op basis van studentID) voor cursussen waard de student een actieve inschrijving heeft.
    public List<Quiz> getAllQuizzesByStudentId(int studentId) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quiz WHERE courseId IN (SELECT courseId FROM StudentCourse WHERE studentId=? AND dropoutDate IS NULL)";
        try {
            this.setupPreparedStatement(sql);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String name = resultSet.getString("name");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyId");
                int courseId = resultSet.getInt("courseId");
                var difficulty = difficultyDao.getById(difficultyId);
                var course = courseDao.getById(courseId);
                var questionInQuizCount = getQuestionsInQuizCount(quizId);
                Quiz quiz = new Quiz(quizId, name, quizPoints, course, difficulty, questionInQuizCount);
                quizzes.add(quiz);
            }
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        }
        return quizzes;
    }

    // methode telt het aantal keer een Quiz is gemaakt op basis van UserID en QuizID als input
    public int getNumberMadeQuizzes(int UserID, int QuizID) {
        String sql = "SELECT COUNT(QuizID) AS AantalGemaakt FROM QUIZRESULTS WHERE UserID =? AND QuizID =?";
        int madeQuizCount = 0;
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, UserID);
            this.preparedStatement.setInt(2, QuizID);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                madeQuizCount = resultSet.getInt("AantalGemaakt");
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return madeQuizCount;
    }

    // methode telt het aantal keer een Quiz is gehaald op basis van UserID en QuizID als input
    public int getNumberSuccesQuizzes(int UserID, int QuizID) {
        String sql = "SELECT SUM(Gehaald) AS AantalGehaald FROM QUIZRESULTS WHERE UserID =? AND QuizID =?";
        int succesQuizCount = 0;
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, UserID);
            this.preparedStatement.setInt(2, QuizID);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                succesQuizCount = resultSet.getInt("AantalGehaald");
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return succesQuizCount;
    }

    // methode haalt de highscore op van een gemaakte quiz op basis van UserID en QuizID als input
    public int getQuizHighscore(int UserID, int QuizID) {
        String sql = "SELECT MAX(Score) AS HighScore FROM QUIZRESULTS WHERE UserID =? AND QuizID =?";
        int quizHighscore = 0;
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, UserID);
            this.preparedStatement.setInt(2, QuizID);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                quizHighscore = resultSet.getInt("HighScore");
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return quizHighscore;
    }

    // Created by Mack: Method to gather all Quizzes belonging to a specific CourseID for the CordinatorDashboard.
    public List<Quiz> getAllQuizzesByCourseId(int courseIdSearch) throws SQLException {
        List<Quiz> quizzesById = new ArrayList<>();
        String sqlImport = "SELECT * FROM Quiz WHERE courseId = ?";
        try {
            this.setupPreparedStatement(sqlImport);
            preparedStatement.setInt(1, courseIdSearch);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String name = resultSet.getString("name");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyId");
                int courseId = resultSet.getInt("courseId");
                var difficulty = difficultyDao.getById(difficultyId);
                var course = courseDao.getById(courseId);
                var questionInQuizCount = getQuestionsInQuizCount(quizId);
                Quiz quiz = new Quiz(quizId, name, quizPoints, course, difficulty, questionInQuizCount);
                quizzesById.add(quiz);
            }
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        }
        return quizzesById;
    }


}