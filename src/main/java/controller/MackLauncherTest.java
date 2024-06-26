/**
 * @author Mack Bakkum
 * @project QuizMaster
 * @created 13/06/2024 - 13:00
 */

package controller;

import com.google.gson.Gson;
import database.couchdb.CouchDBaccess;
import database.couchdb.UsersCouchDBDAO;
import database.mysql.DBAccess;
import model.*;
import database.mysql.UserDAO;
import view.Main;

import java.io.IOException;
import java.util.List;

public class MackLauncherTest {

    private final static DBAccess dbAccess = Main.getdBaccess();
    private static CouchDBaccess couchDBAccess;
    private static UsersCouchDBDAO usersCouchDBDAO;

    public static void main(String[] args) throws IOException {

        // Access to local NoSQL DB
        couchDBAccess = new CouchDBaccess("users", "testadmin", "test");
        usersCouchDBDAO = new UsersCouchDBDAO(couchDBAccess);

        // Retrieving all Users from SQL DB in list allUsersTest
        UserDAO userDAO = new UserDAO(dbAccess);
        List<User> allUsersTest;
        allUsersTest = userDAO.getAll();
        System.out.println("-- All users have been retrieved from SQL DB. --");

        // Converting list allUsersTest to JSON
        Gson gson = new Gson();
        String jsonUsersTest = gson.toJson(allUsersTest);
        System.out.println("-- List has been converted to JSON. --");

        // JSON to User objects
        User[] userJsonToUsers = gson.fromJson(jsonUsersTest, User[].class);

        // Saving all users in list in NoSQL DB
        for (User user : userJsonToUsers) {
            usersCouchDBDAO.saveUser(user);
        }

        // Retrieving a user with Doc ID
        System.out.println("-- User retrieval from ID: --");
        User getUserFromId = usersCouchDBDAO.getUserByDocId("fca8dba169924b708abbfd3917bde91c");
        System.out.println(getUserFromId + "\n");

        // Retrieving user from a userName
        System.out.println("-- User retrieval from userName: --");
        User getUserFromUserName = usersCouchDBDAO.getUserByUserName("bongasa");
        System.out.println(getUserFromUserName + "\n");

        // Retrieving all users in the NoSQL DB.
        System.out.println("-- All users retrieval: --");
        List<User> allUsersRetrieved = usersCouchDBDAO.getAllUsers();
            System.out.println(allUsersRetrieved+ "\n");
    }
}
