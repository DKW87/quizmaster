/**
 * @author Mack Bakkum
 * @project Quizmaster
 * @created 12/06/2024 - 11:30
 */

package database.mysql;

import model.User;
import model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

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

}
