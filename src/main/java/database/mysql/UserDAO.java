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
        } catch (SQLException SqlError) {
            handleSQLException("storeOne", SqlError);
        }
    }

    // Method to retrieve a user from the DB with only their username.
    @Override
    public User getByName(String name) {
        String sqlGetUserName = "SELECT * FROM User WHERE Username = ?";
        try {
            setupPreparedStatementWithKey(sqlGetUserName);
            preparedStatement.setString(1, name);
            return getUserFromResultSet(executeSelectStatement());
        } catch (SQLException SqlException) {
            System.out.println("Error in UserDAO/saveUser: " + SqlException.getMessage());
            return null;
        }
    }

    // Method to retrieve a user from the DB with only their ID.
    @Override
    public User getById(int id) {
        String sqlGetUserID = "SELECT * FROM User WHERE userId = ?";
        User user = null;
        try {
            setupPreparedStatementWithKey(sqlGetUserID);
            preparedStatement.setInt(1, id);
            return getUserFromResultSet(executeSelectStatement());
        } catch (SQLException SqlError) {
            handleSQLException("getById", SqlError);
            return null;
        }
    }

    // Method to return all users from the DB and put them in a List.
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sqlUserList = "SELECT * FROM User;";
        try {
            setupPreparedStatementWithKey(sqlUserList);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                users.add(createUserFromResultSet(resultSet));
            }
        } catch (SQLException SqlError) {
            handleSQLException("getAll", SqlError);
        }
        return users;
    }

    // Method to return all users from the DB by the given roleId.
    public List<User> getByRoleID(int roleId) {
        List <User> users = new ArrayList<>();
        String sqlGetUserID = "SELECT * FROM User WHERE roleId = ? order by lastName ASC;";
        try {
            setupPreparedStatementWithKey(sqlGetUserID);
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                users.add(createUserFromResultSet(resultSet));
            }
        } catch (SQLException SqlError) {
            handleSQLException("getByRoleID", SqlError);
        }
        return users;
    }

    // Method to update an already exsting user.
    @Override
    public void updateOne(User user) {
        String sqlUpdateUser = "UPDATE User SET username = ?, password = ?, firstName = ?, infix = ?, lastName = ?, roleId = ? WHERE userId = ?";
        try {
            setupPreparedStatement(sqlUpdateUser);
            storeUserString(user);
            preparedStatement.setInt(7, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException SqlError) {
            handleSQLException("updateOne", SqlError);
        }
    }

    // Method to delete a user by given userId.
    @Override
    public void deleteOneById(int id) {
        String sqlDeleteUserId = "DELETE FROM User WHERE userId = ?";
        try {
            setupPreparedStatement(sqlDeleteUserId);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException SqlError) {
            handleSQLException("deleteOneById", SqlError);
        }
    }

    // Helper method to avoid duplicate code Used in storeOne & updateOne.
    private void storeUserString(User user) throws SQLException {
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getFirstName());
        preparedStatement.setString(4, user.getInfix());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setInt(6, user.getRole());
    }

    // Helper method to create User from ResultSet
    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("userId");
        String userName = resultSet.getString("username");
        String password = resultSet.getString("password");
        String firstName = resultSet.getString("firstname");
        String infix = resultSet.getString("infix");
        String lastName = resultSet.getString("lastName");
        int roleId = resultSet.getInt("roleId");
        Role role = this.roleDAO.getById(roleId);
        return new User(userId, userName, password, firstName, infix, lastName, role);
    }

    // Helper method to get a user from ResultSet.
    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return createUserFromResultSet(resultSet);
        } else {
            return null;
        }
    }

    // Helper method to handle all SQL errors.
    private void handleSQLException(String methodName, SQLException e) {
        System.err.println("Error in UserDAO/" + methodName + ": " + e.getMessage());
    }
}

