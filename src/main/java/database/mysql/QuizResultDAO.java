package database.mysql;

import model.QuizResult;
import model.QuizResultDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Haziran Çarşamba 2024 - 16:42
 */
public class QuizResultDAO extends AbstractDAO {


    private final QuizDAO quizDao = new QuizDAO(dbAccess);
    private final UserDAO userDao = new UserDAO(dbAccess);


    public QuizResultDAO(DBAccess dbAccess) {
        super(dbAccess);
    }


    /**
     * Retrieves a list of QuizResults for a specific student based on the provided studentId.
     *
     * @param studentId the unique identifier of the student
     * @return a list of QuizResult objects associated with the student
     */
    public List<QuizResult> getResultsByStudent(int studentId) {
        List<QuizResult> quizResultList = new ArrayList<>();
        String sql = "SELECT * FROM Result WHERE userId = ? order by date;";
        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                var quizResult = createQuizResultFromResultSet(resultSet);
                quizResultList.add(quizResult);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return quizResultList;

    }

    /**
     * Retrieves a list of QuizResult objects based on the given quiz ID and student ID.
     *
     * @param  quizId   the ID of the quiz
     * @param  studentId the ID of the student
     * @return           a list of QuizResult objects
     */
    public List<QuizResult> getStudentResultsByQuizId(int quizId, int studentId) {
        List<QuizResult> quizResultList = new ArrayList<>();
        String sql = "SELECT * FROM Result WHERE quizId = ? AND userId = ? order by date;";
        try {
            this.setupPreparedStatement(sql);
            preparedStatement.setInt(1, quizId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                var quizResult = createQuizResultFromResultSet(resultSet);
                quizResultList.add(quizResult);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizResultList;
    }


    /**
     * Stores a single QuizResult object in the Result table of the database.
     *
     * @param  quizResult  the QuizResult object to be stored
     * @return                 the primary key of the inserted row, or 0 if an error occurred
     */
    public int storeOne(QuizResult quizResult) {
        String sql = "INSERT INTO  Result (date ,userId, quizId, score) VALUES (?, ?, ?, ?)";
        int primaryKey = 0;
        try {
            setupPreparedStatement(sql);
            setQuizResultToQuery(quizResult); //setQuizResultToQuery
            primaryKey = this.executeInsertStatementWithKey();
            quizResult.setResultId(primaryKey);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return primaryKey;

    }

    public QuizResult convertQuizResultDTOToQuizResult(QuizResultDTO quizResultDTO) {
        return new QuizResult(quizResultDTO.getResultId(),
                userDao.getById(quizResultDTO.getStudentId()),
                quizDao.getById(quizResultDTO.getQuizId()),
                quizResultDTO.getScore(),
                quizResultDTO.getDate()
        );
    }

    private QuizResult createQuizResultFromResultSet(ResultSet resultSet) throws SQLException {
        LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
        int userId = resultSet.getInt("userId");
        int resultId = resultSet.getInt("resultId");
        int quizId = resultSet.getInt("quizId");
        int score = resultSet.getInt("score");
        var user = userDao.getById(userId);
        var quiz = quizDao.getById(quizId);
        return new QuizResult(resultId, user, quiz, score, date);
    }
    private void setQuizResultToQuery(QuizResult quizResult) throws SQLException {
        preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(quizResult.getDate()));
        preparedStatement.setInt(2, quizResult.getQuiz().getQuizId());
        preparedStatement.setInt(3, quizResult.getStudent().getUserId());
        preparedStatement.setInt(4, quizResult.getScore());

    }

}
