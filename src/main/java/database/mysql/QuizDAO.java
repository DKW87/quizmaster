package database.mysql;

import model.Difficulty;
import model.Quiz;
import model.QuizIO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO extends AbstractDAO implements GenericDAO<Quiz> {
    public QuizDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public void storeOne(Quiz quiz) {
        String sql = "INSERT INTO Quiz(name, difficultyId,quizPoints,courseId) VALUES (?, ?, ? ,?)";
        try {
            setupPreparedStatementWithKey(sql);
            preparedStatement.setString(1, quiz.getQuizName());
            preparedStatement.setInt (2, quiz.getQuizDifficulty().getDifficultyId());
            preparedStatement.setInt(3, quiz.getQuizPoints());
            preparedStatement.setInt(4, quiz.getCourse().getCourseId());
            int primaryKey = executeInsertStatementWithKey();
            quiz.setQuizId(primaryKey);
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        }
    }


    @Override
    public List<Quiz> getAll() {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM quiz";

        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int quizId = resultSet.getInt("quizId");
                String name = resultSet.getString("name");
                int passmark = resultSet.getInt("passmark");
                // onderstaande kolomn moet nog worden toegevoegd aan de DB
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyId");
                int courseId = resultSet.getInt("courseId");
                    var difficulty = DifficultyDAO.getById(difficultyId);
                    var course = CourseDAO.getById(courseId);
                    Quiz quiz = new Quiz(quizId, name, passmark, quizPoints, course, difficulty);
                    quizzes.add(quiz);
            }
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        } return quizzes;

    }


    @Override
    public Quiz getById(int quizId) {
        Quiz quiz = null;
        String sql = "SELECT * FROM Quiz WHERE quizId = ?";
        try {
            this.setupPreparedStatement(sql);
            preparedStatement.setInt(1, quizId);
            ResultSet resultSet = executeSelectStatement();
            // below will not work as quizPoints is not yet a column in DB TODO add quizPoints to DB
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int passMark = resultSet.getInt("passMark");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyID");
                int courseId = resultSet.getInt("courseID");
                // ophalen van de juiste IDs uit DB:
                // onderstaand nog aanpassen
                var difficulty = DifficultyDAO.getById(difficultyId);
                var course = CourseDAO.getById(courseId);
                quiz = new Quiz(quizId, name, passMark, quizPoints, course, difficulty);
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Fout" + sqlError.getMessage());
        }
        return quiz;
    }

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
                int passMark = resultSet.getInt("passMark");
                int quizPoints = resultSet.getInt("quizPoints");
                int difficultyId = resultSet.getInt("difficultyID");
                int courseId = resultSet.getInt("courseID");
                // ophalen van de juiste IDs uit DB:
                // onderstaand nog aanpassen
                var difficulty = DifficultyDAO.getById(difficultyId);
                var course = CourseDAO.getById(courseId);
                quiz = new Quiz(quizId, name, passMark, quizPoints, course, difficulty);
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Fout" + sqlError.getMessage());
        } return quiz;
    }


}
