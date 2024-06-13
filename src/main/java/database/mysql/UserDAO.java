/**
 * @author Mack Bakkum
 * @project Quizmaster
 * @created 12/06/2024 - 11:30
 */

package database.mysql;

import model.User;
import model.Role;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO implements GenericDAO<User> {
    private final RoleDAO roleDAO = new RoleDAO(dbAccess);

    public UserDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    // Method to save a user to the DB with the given attributes.
    @Override
    public void storeOne(User user) {
        String sqlUserImport = "INSERT INTO User(username, password, firstName, infix, lastName, roleId) VALUES (?,?,?,?,?,?);";
        int primaryKey;
        try {
            setupPreparedStatementWithKey(sqlUserImport);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getInfix());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setInt(6, user.getRole());
            primaryKey = this.executeInsertStatementWithKey();
            user.setUserId(primaryKey);
        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/saveUser: " + sqlRuntimeError.getMessage());
        }
    }

    // Method to retrieve a user from the DB with only their username.
    @Override
    public User getByName(String name) {
        String sqlGetUserName = "SELECT * FROM User WHERE Username = ?";
        User user = null;
        try {
// TO DO @mack: Dit is dubbele code ook in de methode getById, kan ik dit nog anders oplossen?
            setupPreparedStatementWithKey(sqlGetUserName);
            preparedStatement.setString(1, name);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstname");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                int roleId = resultSet.getInt("roleId");
                int userId = resultSet.getInt("userId");
                Role role = this.roleDAO.getById(roleId);  // Creates role with getUserRoleById method so a new user can be created.
                user = new User(userId,userName, password, firstName, infix, lastName, role);
            }
        } catch (SQLException SqlException) {
            System.out.println("Error in UserDAO/saveUser: " + SqlException.getMessage());
        }
        return user;
    }

    // Method to retrieve a user from the DB with only their ID.
    @Override
    public User getById(int id) {
        String sqlGetUserID = "SELECT * FROM User WHERE userId = ?";
        User user = null;
        try {
            setupPreparedStatementWithKey(sqlGetUserID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstname");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                int roleId = resultSet.getInt("roleId");
                int userId = resultSet.getInt("userId");
                Role role = this.roleDAO.getById(roleId);  // Creates role with getUserRoleById method so a new user can be created.
                user = new User(userId,userName, password, firstName, infix, lastName, role);
            }
        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/getUserPerID: " + sqlRuntimeError.getMessage());
        }
        return user;
    }


    public List<User> usersFromCSV(String csvFilePath) throws IOException {
        List<User> users = new ArrayList<>();
        final int IMPORT_WITH_INFIX = 6;
        final int IMPORT_NO_INFIX = 5;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Can be used if CSV has a header. It'll skip the first line.
            while ((line = br.readLine()) != null) {
                String[] userValues = line.split(",");
                if (userValues.length == IMPORT_WITH_INFIX) { // For users with infix
                    Role userRole = this.roleDAO.getByName(userValues[5]);
                    User user = new User(userValues[0], userValues[1], userValues[2], userValues[3], userValues[4], userRole);
                    users.add(user);
                    System.out.println("Added user with infix");
                } if (userValues.length == IMPORT_NO_INFIX) { // For users without infix
                    Role userRole = this.roleDAO.getByName(userValues[4]);
                    User user = new User(userValues[0], userValues[1], userValues[2], userValues[3], userRole);
                    users.add(user);
                    System.out.println("Added user with no infix");
                }
            }
        }
        return users;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sqlUserList = "SELECT * FROM User;";

        try {
            setupPreparedStatementWithKey(sqlUserList);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstname");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                int roleId = resultSet.getInt("roleId");
                Role role = this.roleDAO.getById(roleId);
                users.add(new User(userName, password, firstName, infix, lastName, role));
            }
        } catch (SQLException SqlException) {
            System.out.println("Error in UserDAO/getAll: " + SqlException.getMessage());
        }
        return users;
    }
}
