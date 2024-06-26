package database.couchdb;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Course;
import model.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 26 june 2024 : 10:30
 */

public class QuizCouchDBDAO extends AbstractCouchDBDAO {
    private Gson gson;

    public QuizCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new Gson();
    }
    // Quiz object om zetten naar JsonObject en daarna opslaan in CouchDB
    public String saveSingleQuiz(Quiz quiz) {
        String jsonStringQuiz = gson.toJson(quiz);
        JsonObject jsonObject = new JsonParser().parse(jsonStringQuiz).getAsJsonObject();
        return saveDocument(jsonObject);
    }

    // ophalen van een Quiz op basis van het DocID in de CouchDB
    public Quiz getSingleQuizByDocId(String docId) {
        return gson.fromJson(getDocumentById(docId), Quiz.class);

    }

}
