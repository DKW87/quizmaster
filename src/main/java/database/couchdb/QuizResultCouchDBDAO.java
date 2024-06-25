package database.couchdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import helpers.LocalDateTimeAdapter;
import model.QuizResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 16:25
 */
public class QuizResultCouchDBDAO extends AbstractCouchDBDAO {
    private Gson gson;
    public QuizResultCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new GsonBuilder()
                // register custom JsonSerializer for LocalDate
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    public void saveQuizResult(QuizResult quizResult) {
        String jsonString = gson.toJson(quizResult);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        saveDocument(jsonObject);
    }

    public QuizResult getQuizResultDocId(String doc_Id) {
        return gson.fromJson(getDocumentById(doc_Id), QuizResult.class);
    }
    /**
     * Retrieves all quiz results from the database.
     *
     * @return  a list of QuizResult objects representing all quiz results in the database
     */

    public List<QuizResult> getAllQuizResults() {
        List<QuizResult> results = new ArrayList<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            results.add(gson.fromJson(jsonObject, QuizResult.class));
        }
        return results  ;
    }
    /**
     * A description of the entire Java function.
     *
     * @param  quizId   description of parameter
     * @return          description of return value
     */
    public List<QuizResult> getAllQuizResults(int quizId) {
        List<QuizResult> results = new ArrayList<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            int dbQuizId = jsonObject.get("quiz").getAsJsonObject().get("quizId").getAsInt();
            if (dbQuizId == quizId) {
                results.add(gson.fromJson(jsonObject, QuizResult.class));
            }
        }
        return results  ;
    }
    /**
     * Retrieves all quiz results for a specific quiz and user.
     *
     * @param  quizId   the ID of the quiz
     * @param  userId   the ID of the user
     * @return          a list of QuizResult objects
     */
    public List<QuizResult> getAllQuizResults(int quizId,int userId) {
        List<QuizResult> results = new ArrayList<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            int dbQuizId = jsonObject.get("quiz").getAsJsonObject().get("quizId").getAsInt();
            int dbUserId = jsonObject.get("student").getAsJsonObject().get("userId").getAsInt();
            if (dbQuizId == quizId
                    && dbUserId == userId) {
                results.add(gson.fromJson(jsonObject, QuizResult.class));
            }
        }
        return results  ;
    }
}
