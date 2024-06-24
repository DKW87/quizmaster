package database.couchdb;

import com.google.gson.JsonObject;
import org.lightcouch.Response;

import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 14:39
 */
public class AbstractCouchDBDAO {

    private CouchDBaccess couchDBaccess;

    public AbstractCouchDBDAO(CouchDBaccess couchDBaccess) {
        this.couchDBaccess = couchDBaccess;
    }

    public String saveDocument(JsonObject document) {
        Response response = couchDBaccess.getClient().save(document);
        return response.getId();
    }

    public JsonObject getDocumentById(String idDocument) {
        return couchDBaccess.getClient().find(JsonObject.class, idDocument);
    }

    public List<JsonObject> getAllDocuments() {
        return couchDBaccess.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
    }

    public String updateDocument(JsonObject document) {
        Response response = couchDBaccess.getClient().update(document);
        return response.getId();
    }

    public void deleteDocument(String idDocument, String revDocument) {
        couchDBaccess.getClient().remove(idDocument, revDocument);
    }

}
