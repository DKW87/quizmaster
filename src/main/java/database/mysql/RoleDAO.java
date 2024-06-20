/**
 * @author Mack Bakkum
 * @project Quizmaster
 * @created 12/06/2024 - 13:30
 */

package database.mysql;

import model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends AbstractDAO implements GenericDAO<Role> {

    public RoleDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    // Method so users retrieved from DB can get their role assigned in the creation of the user. See UserDAO.getUserPerId
    @Override
    public Role getById(int roleId) {
        String sqlGetRole = "SELECT * FROM Role WHERE roleId = ?";
        try {
            setupPreparedStatementWithKey(sqlGetRole);
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                return createRoleFromResultSet(resultSet);
            }
        } catch (SQLException SqlError) {
            handleSQLException("roleId", SqlError);
        }
        return null;
    }

    // Method so information from the Role can be retrieved from just the name.
    @Override
    public Role getByName(String roleName) {
        String sqlGetRoleString = "SELECT * FROM Role WHERE name = ?";
        try {
            setupPreparedStatementWithKey(sqlGetRoleString);
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                return createRoleFromResultSet(resultSet);
            }
        } catch (SQLException SqlError) {
            handleSQLException("roleName", SqlError);
        }
        return null;
    }

    // Method to get an overview of all Roles.
    @Override
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        String sqlRoleList = "SELECT * FROM Role;";
        try {
            setupPreparedStatement(sqlRoleList);
            ResultSet resultSet = executeSelectStatement();
            while (resultSet.next()) {
                roles.add(createRoleFromResultSet(resultSet));
            }
        } catch (SQLException SqlError) {
            handleSQLException("getAll", SqlError);
        }
        return roles;
    }

    // Method to insert a new role into the DB.
    @Override
    public void storeOne(Role type) {
        String sqlRoleImport = "INSERT INTO role (roleId, name) VALUES (?, ?)";
        try {
            setupPreparedStatementWithKey(sqlRoleImport);
            preparedStatement.setInt(1, type.getRoleId());
            preparedStatement.setString(2, type.getName());
        } catch (SQLException SqlError) {
            handleSQLException("storeOne", SqlError);
        }
    }

    // Method to update an existing role implemented. For now unused.
    @Override
    public void updateOne(Role role) {
        String sqlUpdateUser = "UPDATE Role SET name = ? WHERE roleId = ?";
        try {
            setupPreparedStatement(sqlUpdateUser);
            preparedStatement.setInt(1, role.getRoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException SqlError) {
            handleSQLException("updateOne", SqlError);
        }
    }

    // Method to delete a role implemented, for now unused.
    @Override
    public void deleteOneById(int id) {
        String sqlDeleteId = "DELETE FROM Role WHERE roleId = ?";
        try {
            setupPreparedStatement(sqlDeleteId);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException SqlError) {
            handleSQLException("deleteOneById", SqlError);
        }
    }

    // Helper method to handle all SQL errors.
    private void handleSQLException(String methodName, SQLException e) {
        System.err.println("Error in RoleDAO/" + methodName + ": " + e.getMessage());
    }

    // Helper method to create Role(s) from ResultSets.
    private Role createRoleFromResultSet(ResultSet resultSet) throws SQLException {
        String roleName = resultSet.getString("name");
        int roleId = resultSet.getInt("roleId");
        Role role = new Role(roleName);
        role.setRoleId(roleId);
        return role;
    }
}
