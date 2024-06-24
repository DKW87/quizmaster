package database.couchdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 22 June Saturday 2024 - 15:18
 */
public class CourseCouchDBDAO extends AbstractCouchDBDAO {
    private Gson gson;
    public CourseCouchDBDAO(CouchDBaccess couchDBaccess) {
        super(couchDBaccess);
        gson = new Gson();
    }

    public String saveCourse(Course course) {
        String jsonString = gson.toJson(course);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        return saveDocument(jsonObject);
    }

    public Course getCourseByDocId(String doc_Id) {
        return gson.fromJson(getDocumentById(doc_Id), Course.class);
    }
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        for (JsonObject jsonObject : getAllDocuments()) {
            courseList.add(gson.fromJson(jsonObject, Course.class));
        }
        return courseList  ;
    }

    public Course getCourseByName(String courseName) {
        for (JsonObject jsonObject : getAllDocuments()) {
            if (jsonObject.get("name").getAsString().equals(courseName)) {
                return gson.fromJson(jsonObject, Course.class);
            }
        }
        return null;
    }
    public Course getCourseById(Integer courseId) {
        for (JsonObject jsonObject : getAllDocuments()) {
            if (jsonObject.get("courseId").getAsInt() == courseId) {
                return gson.fromJson(jsonObject, Course.class);
            }
        }
        return null;
    }

    public void deleteCourse(Course course) {
        String[] idAndRev = getIdAndRevFromCourse(course);
        deleteDocument(idAndRev[0], idAndRev[1]);
    }

    public String[] getIdAndRevFromCourse(Course course) {
        String[] idAndRev = new String[2];
        for (JsonObject jsonObject : getAllDocuments()) {
            if (jsonObject.has("courseId") &&
                    jsonObject.get("courseId").getAsInt() ==course.getCourseId()) {
                idAndRev[0] = jsonObject.get("_id").getAsString();
                idAndRev[1] = jsonObject.get("_rev").getAsString();
            }
        }
        return idAndRev;
    }

    public String updateCourse(Course course) {
        String[] idAndRev = getIdAndRevFromCourse(course);
        String jsonString = gson.toJson(course);
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        jsonObject.addProperty("_id", idAndRev[0]);
        jsonObject.addProperty("_rev", idAndRev[1]);
        return updateDocument(jsonObject);
    }


}
