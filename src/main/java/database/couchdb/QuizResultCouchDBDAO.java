package database.couchdb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.mysql.QuizResultDAO;
import helpers.LocalDateTimeAdapter;
import model.QuizResult;
import model.QuizResultDTO;
import view.Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 16:25
 */
public class QuizResultCouchDBDAO extends AbstractCouchDBDAO {

    private final QuizResultDAO quizResultDAO = new QuizResultDAO(Main.getdBaccess());
    private Gson gson;
    public QuizResultCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new GsonBuilder()
                // register custom JsonSerializer for LocalDate
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    public void saveQuizResult(QuizResultDTO quizResult) {
        String jsonString = gson.toJson(quizResult);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        saveDocument(jsonObject);
    }

    public QuizResult getQuizResultDocId(String doc_Id) {
        QuizResultDTO quizResult =    gson.fromJson(getDocumentById(doc_Id), QuizResultDTO.class);
        return quizResultDAO.convertQuizResultDTOToQuizResult(quizResult);
    }
    /**
     * Retrieves all quiz results from the database.
     *
     * @return  a list of QuizResult objects representing all quiz results in the database
     */

    public List<QuizResult> getAllQuizResults() {
        List<QuizResult> results = new ArrayList<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            results.add(quizResultDAO.convertQuizResultDTOToQuizResult(gson.fromJson(jsonObject, QuizResultDTO.class)));
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
            int dbQuizId = jsonObject.get("quizId").getAsInt();
            if (dbQuizId == quizId) {
                results.add(quizResultDAO.convertQuizResultDTOToQuizResult(gson.fromJson(jsonObject, QuizResultDTO.class)));
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
            int dbQuizId = jsonObject.get("quizId").getAsInt();
            int dbUserId = jsonObject.get("studentId").getAsInt();
            if (dbQuizId == quizId && dbUserId == userId) {
                results.add(quizResultDAO.convertQuizResultDTOToQuizResult(gson.fromJson(jsonObject, QuizResultDTO.class)));
            }
        }
        return results  ;
    }


}
