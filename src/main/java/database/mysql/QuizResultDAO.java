package database.mysql;

import model.QuizResult;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Haziran Çarşamba 2024 - 16:42
 */
public class QuizResultDAO extends AbstractDAO implements GenericDAO<QuizResult> {

    // FIXME: implementeer DAO's
//    private final QuizDAO quizDao = new QuizDAO(dbAccess);
//    private final UserDAO  userDao = new UserDAO(dbAccess);


    public QuizResultDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<QuizResult> getAll() {
        List<QuizResult> quizResultList = new ArrayList<>();
        String sql = "SELECT * FROM Result;";
        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int quizId = resultSet.getInt("quizId");
                int studentId = resultSet.getInt("userId");
                int score = resultSet.getInt("score");

                // FIXME: implementeer DAO's @Rob(QuizDAO) and @Mack(UserDAO) (see above)
//                var quiz = quizDao.getById(quizId);
//                var user = userDao.getById(studentId);
//                var quizResult = new QuizResult(date, quiz, user, score);
                quizResultList.add(null);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return quizResultList;

    }

    /**
     * Retrieves a list of QuizResults for a specific student based on the provided studentId.
     *
     * @param  studentId   the unique identifier of the student
     * @return             a list of QuizResult objects associated with the student
     */
    public List<QuizResult> getResultsByStudent(int studentId) {
        List<QuizResult> quizResultList = new ArrayList<>();
        String sql = "SELECT * FROM Result WHERE userId = ? order by date;";
        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int quizId = resultSet.getInt("quizId");
                int score = resultSet.getInt("score");
                // FIXME: implementeer DAO's @Rob(QuizDAO) and @Mack(UserDAO) (see above)
//                var quiz = quizDao.getById(quizId);
//                var user = userDao.getById(studentId);
//                var quizResult = new QuizResult(date, quiz, user, score);
                quizResultList.add(null);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return quizResultList;

    }

    @Override
    public QuizResult getById(int resultId) {
        String sql = "SELECT * FROM Result WHERE resultId = ?";

        QuizResult quizResult = null;
        try {
            setupPreparedStatement(sql);
            preparedStatement.setInt(1, resultId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int quizId = resultSet.getInt("quizId");
                int studentId = resultSet.getInt("userId");
                int score = resultSet.getInt("score");
                // FIXME: implementeer DAO's @Rob(QuizDAO) and @Mack(UserDAO) (see above)
//                var quiz = quizDao.getById(quizId);
//                var user = userDao.getById(studentId);
//                quizResult = new QuizResult(date, quiz, user , score);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizResult;

    }



    @Override
    public void storeOne(QuizResult quizResultaat) {
        String sql = "INSERT INTO  Result (date ,userId, quizId, score) VALUES (?, ?, ?, ?)";
        int primaryKey;
        try  {
            setupPreparedStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(quizResultaat.getDate()));
            preparedStatement.setInt(2, quizResultaat.getQuiz().getQuizId());
            preparedStatement.setInt(3, quizResultaat.getStudent().getUserId());
            preparedStatement.setInt(4, quizResultaat.getScore());
            primaryKey = this.executeInsertStatementWithKey();
            quizResultaat.setResultId(primaryKey);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    @Override
    public QuizResult getByName(String name) {
        return null;
    }
}
