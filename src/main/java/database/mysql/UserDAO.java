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

public class UserDAO extends AbstractDAO {
    private final RoleDAO roleDAO;

    public UserDAO(DBAccess dbAccess, RoleDAO roleDAO) {
        super(dbAccess);
        this.roleDAO = new RoleDAO(dbAccess);
    }

    // Method to save a user to the DB with the given attributes.
    public void saveUser (User user) {
        String sqlUserImport = "INSERT INTO User(userId, username, password, firstName, infix, lastName, roleId) VALUES (?,?,?,?,?,?,?);";
        try {
            setupPreparedStatement(sqlUserImport);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getInfix());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setInt(7, user.getRole());
        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/saveUser: " + sqlRuntimeError.getMessage());
        }
    }

    // Method to retrieve a user from the DB with only their ID.
    public User getUserPerId(int userId) {
        String sqlGetUserID = "SELECT * FROM User WHERE userId = ?";
        User user = null;
        try {
            setupPreparedStatement(sqlGetUserID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstname");
                String infix = resultSet.getString("infix");
                String lastName = resultSet.getString("lastName");
                int roleId = resultSet.getInt("roleId");

                Role role = this.roleDAO.getUserRoleById(roleId);  // Creates role with getUserRoleById method so a new user can be created.
                user = new User(userId, userName, password, firstName, infix, lastName, role);
            }
        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/getUserPerID: " + sqlRuntimeError.getMessage());
        }
        return user;
    }


    public List<User> usersFromCSV(String csvFilePath) throws IOException {
        List<User> users = new ArrayList<>();
        int nextUserId = 0; // First user ID. Will pass next number to each next user created.
        final int IMPORT_WITH_INFIX = 6;
        final int IMPORT_NO_INFIX = 5;

        //TO DO @Mack: Figure out how to store the number so with a 2nd CSV import the userId doesn't start as 1 again!!

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Can be used if CSV has a header. It'll skip the first line.

            while ((line = br.readLine()) != null) {
                String[] userValues = line.split(",");

                if (userValues.length == IMPORT_WITH_INFIX) { // For users with infix
                    Role userRole = this.roleDAO.getUserRoleByName(userValues[5]);
                    User user = new User(nextUserId, userValues[0], userValues[1], userValues[2], userValues[3], userValues[4], userRole);
                    users.add(user);
                }
                if (userValues.length == IMPORT_NO_INFIX) { // For users without infix
                    Role userRole = this.roleDAO.getUserRoleByName(userValues[4]);
                    User user = new User(nextUserId, userValues[0], userValues[1], userValues[2], userValues[3], userRole);
                    users.add(user);
                } else {
                    System.out.println("Error in CSV import");
                }
            }
        }
        return users;
    }
}
