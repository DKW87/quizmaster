package database.couchdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Question;
import model.Quiz;

import java.util.List;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 26/06/2024 - 14:13
 */
public class QuestionCouchDBDAO extends AbstractCouchDBDAO {

    private Gson gson;

    public QuestionCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new Gson();
    }

    public String saveOneQuestion(Question question) {
        String json = gson.toJson(question);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        return saveDocument(jsonObject);
    }

    public void saveAllQuestions(List<Question> questions) {
        for (Question question : questions) {
            saveOneQuestion(question);
        }
    }

    public Question getAllQuestions(String questionDescription, String answerA, int quizId) {
        Question question;
        for (JsonObject jsonObject : getAllDocuments()) {
            question = gson.fromJson(jsonObject, Question.class);
            if (question.getQuestionDescription().equals(questionDescription) && question.getAnswerA().equals(answerA)
                    && question.getQuiz().getQuizId() == quizId) {
                return question;
            }
        }
        return null;
    }

}
