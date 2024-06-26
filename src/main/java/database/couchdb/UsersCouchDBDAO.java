/**
 * @author Mack Bakkum
 * @project Quizmaster
 * @created 26/06/2024 - 12:00
 */

package database.couchdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersCouchDBDAO extends AbstractCouchDBDAO {

    private Gson gson;

    public UsersCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new Gson();
    }

    public String saveUser(User user) {
        String jsonStringUser = gson.toJson(user);
        JsonObject jsonObjectUser = JsonParser.parseString(jsonStringUser).getAsJsonObject();
        return saveDocument(jsonObjectUser);
    }

    public User getUserByDocId(String docId) {
        return gson.fromJson(getDocumentById(docId), User.class);
    }

    public User getUserByUserName(String userName) {
        for (JsonObject jsonObjectUser : getAllDocuments()) {
            if (jsonObjectUser.get("userName").getAsString().equals(userName)) {
                return gson.fromJson(jsonObjectUser, User.class);
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> fullUserList = new ArrayList<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            fullUserList.add(gson.fromJson(jsonObject, User.class));
        }
        return fullUserList;
    }

}
