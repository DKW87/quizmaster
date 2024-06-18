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
            storeUserString(user);
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
            setupPreparedStatementWithKey(sqlGetUserName);
            preparedStatement.setString(1, name);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {  user = getUser(resultSet);}
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
            if (resultSet.next()) {  user = getUser(resultSet);}

        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/getUserPerID: " + sqlRuntimeError.getMessage());
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sqlUserList = "SELECT * FROM User;";
        try {
            setupPreparedStatementWithKey(sqlUserList);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                users.add(user);
            }
        } catch (SQLException SqlException) {
            System.out.println("Error in UserDAO/getAll: " + SqlException.getMessage());
        }
        return users;
    }

    // Used in getByName, getByid & getAll. Avoids duplicate code.
    private User getUser(ResultSet resultSet) throws SQLException {
            String userName = resultSet.getString("username");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("firstname");
            String infix = resultSet.getString("infix");
            String lastName = resultSet.getString("lastName");
            int roleId = resultSet.getInt("roleId");
            int userId = resultSet.getInt("userId");
            Role role = this.roleDAO.getById(roleId);  // Creates role with getUserRoleById method so a new user can be created.
            return new User(userId,userName, password, firstName, infix, lastName, role);
    }

    // Method to return all users from the DB by the given roleId.
    public List<User> getByRoleID(int roleId) {
        List <User> users = new ArrayList<>();
        String sqlGetUserID = "SELECT * FROM User WHERE roleId = ?";
        try {
            setupPreparedStatementWithKey(sqlGetUserID);
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                users.add(user);
            }
        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/getUserPerID: " + sqlRuntimeError.getMessage());
        }
        return users;
    }

    @Override
    public void updateOne(User user) {
        String sqlUpdateUser = "UPDATE User SET username = ?, password = ?, firstName = ?, infix = ?, lastName = ?, roleId = ? WHERE userId = ?";
        try {
            setupPreparedStatement(sqlUpdateUser);
            storeUserString(user);
            preparedStatement.setInt(7, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/updateOne: " + sqlRuntimeError.getMessage());
        }
    }

    @Override
    public void deleteOneById(int id) {
        String sqlDeleteUserId = "DELETE FROM User WHERE userId = ?";
        try {
            setupPreparedStatement(sqlDeleteUserId);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlRuntimeError) {
            System.out.println("Error in UserDAO/deleteOneById: " + sqlRuntimeError.getMessage());
        }
    }

    // Used in storeOne & updateOne. Avoids duplicate code in methods.
    private void storeUserString(User user) throws SQLException {
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getInfix());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setInt(6, user.getRole());
    }
}
