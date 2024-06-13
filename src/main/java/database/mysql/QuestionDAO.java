package database.mysql;

import model.Course;
import model.Difficulty;
import model.Question;

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
        String sql = "SELECT * FROM question;";
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

                Course course = null; // how do I get course?

                Question question = new Question(questionDescription, answerA, answerB, answerC, answerD, course);
                question.setQuestionId(questionId);
                questions.add(question);
            }
        } catch (SQLException error) {
            System.out.println("De volgende uitzondering is opgetreden: " + error.getErrorCode());
        }
        return questions;
    }

    @Override
    public Question getOneById(int id) {
        // TODO what do we do here? I'm not following
        Question question = null;
        return question;
    }

    @Override
    public void storeOne(Question question) {
        String sql = "INSERT INTO question(questionDescription, answerA, answerB, answerC, answerD) VALUES (?, ?, ?, ?, ?);";
        int pKey = 0;
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, question.getQuestionDescription());
            this.preparedStatement.setString(2, question.getAnswerA());
            this.preparedStatement.setString(3, question.getAnswerB());
            this.preparedStatement.setString(4, question.getAnswerC());
            this.preparedStatement.setString(5, question.getAnswerD());
            pKey = this.executeInsertStatementWithKey();
            question.setQuestionId(pKey);
        } catch (SQLException error) {
            System.out.println("De volgende uitzondering is opgetreden: " + error.getErrorCode());
        }    }


} // class
