package database.mysql;

import model.Quiz;
import model.QuizIO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizDAO extends AbstractDAO {
    public QuizDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

public void saveQuizInDB(QuizIO quiz) {
        String sql = "INSERT INTO Quiz VALUES (?, ?, ? ,?)";
        try {
            setupPreparedStatementWithKey(sql);
            preparedStatement.setString(1,quiz.getQuizName());
            preparedStatement.setString(2,quiz.getQuizDifficulty());
            preparedStatement.setInt(3,quiz.getQuizPoints());
            preparedStatement.setString(3, quiz.getQuizCourse());
            int primaryKey = executeInsertStatementWithKey();
            quiz.setQuizId(primaryKey);
        } catch (SQLException sqlError) {
            System.out.println(sqlError.getMessage());
        }
}

public QuizIO getQuizPerQuizID(int quizId) {
        String sql = "SELECT * FROM Quiz WHERE QuizId = ?";
        QuizIO quiz = null;
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1,quizId);
            ResultSet resultSet = executeSelectStatement();
            // below will not work as quizPoints is not yet a column in DB TODO add quizPoints to DB
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String difficulty = resultSet.getString("difficulty");
                int quizPoints = resultSet.getInt("quizPoints");
                String courseID = resultSet.getString("courseID");
                quiz = new QuizIO(name,difficulty,quizPoints,courseID);
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Fout" + sqlError.getMessage());
        }
        return quiz;
}

}
