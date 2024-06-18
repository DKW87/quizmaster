package database.mysql;

import model.Course;
import model.Difficulty;
import model.Question;
import model.Quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 12/06/2024 - 16:04
 */
public class QuestionDAO extends AbstractDAO implements GenericDAO<Question> {

    public QuestionDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = new ArrayList<Question>();
        String sql = "SELECT * FROM Question;";
        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String questionDescription = resultSet.getString("questionDescription");
                String answerA = resultSet.getString("answerA");
                String answerB = resultSet.getString("answerB");
                String answerC = resultSet.getString("answerC");
                String answerD = resultSet.getString("answerD");
                Quiz quiz = null; // TODO implement quiz.getByName.getById
                Question question = new Question(questionDescription, answerA, answerB, answerC, answerD, quiz);
                question.setQuestionId(questionId);
                questions.add(question);
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return questions;
    }

    @Override
    public Question getById(int id) {
        Question question = null;
        String sql = "SELECT * FROM Question WHERE questionId = ?;";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, id);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String questionDescription = resultSet.getString("questionDescription");
                String answerA = resultSet.getString("answerA");
                String answerB = resultSet.getString("answerB");
                String answerC = resultSet.getString("answerC");
                String answerD = resultSet.getString("answerD");
                Quiz quiz = null; // TODO implement quiz.getByName.getById
                question = new Question(questionDescription, answerA, answerB, answerC, answerD, quiz);
                question.setQuestionId(questionId);
                return question;
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return question;
    }

    @Override
    public Question getByName(String name) {
        Question question = null;
        String sql = "SELECT * FROM Question WHERE questionDescription = ?;";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setString(1, name);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int questionId = resultSet.getInt("questionId");
                String questionDescription = resultSet.getString("questionDescription");
                String answerA = resultSet.getString("answerA");
                String answerB = resultSet.getString("answerB");
                String answerC = resultSet.getString("answerC");
                String answerD = resultSet.getString("answerD");
                Quiz quiz = null; // TODO implement quiz.getByName.getById
                question = new Question(questionDescription, answerA, answerB, answerC, answerD, quiz);
                question.setQuestionId(questionId);
                return question;
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return question;
    }

    @Override
    public void storeOne(Question question) {
        String sql = "INSERT INTO Question(questionDescription, answerA, answerB, answerC, answerD, quizId) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, question.getQuestionDescription());
            this.preparedStatement.setString(2, question.getAnswerA());
            this.preparedStatement.setString(3, question.getAnswerB());
            this.preparedStatement.setString(4, question.getAnswerC());
            this.preparedStatement.setString(5, question.getAnswerD());
            this.preparedStatement.setInt(6, question.getQuiz().getQuizId());
            int pKey = this.executeInsertStatementWithKey();
            question.setQuestionId(pKey);
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
    }

    public void storeList(List<Question> questions) {
        for (Question question : questions) {
            storeOne(question);
        }
    }

    @Override
    public void updateOne(Question question) {
        String sql = "UPDATE Question SET questionDescription = ?, answerA = ?, answerB = ?," +
                " answerC = ?, answerD = ?, quizId = ? WHERE questionId = ?";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setString(1, question.getQuestionDescription());
            this.preparedStatement.setString(2, question.getAnswerA());
            this.preparedStatement.setString(3, question.getAnswerB());
            this.preparedStatement.setString(4, question.getAnswerC());
            this.preparedStatement.setString(5, question.getAnswerD());
            this.preparedStatement.setInt(6, question.getQuiz().getQuizId());
            this.preparedStatement.setInt(7, question.getQuestionId());
            this.executeManipulateStatement();
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
    }

    @Override
    public void deleteOneById(int questionId) {
        String sql = "DELETE FROM Question WHERE questionId = ?";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, questionId);
            this.preparedStatement.executeUpdate();
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
    }

} // class
