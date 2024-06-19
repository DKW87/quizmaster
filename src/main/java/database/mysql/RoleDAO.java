/**
 * @author Mack Bakkum
 * @project Quizmaster
 * @created 12/06/2024 - 13:30
 */

package database.mysql;

import model.Role;

import javax.xml.transform.Result;
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
                String roleName = resultSet.getString("name");
                Role role = new Role(roleName);
                role.setRoleId(roleId);
                return role;
            }
        } catch (SQLException e) {
            System.out.println("Error in RoleDAO/getUserRole: " + e.getMessage());
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
                String roleName2 = resultSet.getString("name");
                int roleIdDb = resultSet.getInt("roleId");
                Role role = new Role(roleName2);
                role.setRoleId(roleIdDb); // Sets the roleId from the DB to the created role.
                return role;
            }
        } catch (SQLException SQLException) {
            System.out.println("Error in RoleDAO/getUserRoleByName: " + SQLException.getMessage());
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
                int roleId = resultSet.getInt("roleId");
                String name = resultSet.getString("name");
                Role role = new Role(name);
                role.setRoleId(roleId);
                System.out.println("role-----"+role);
                roles.add(role);
            }
        } catch (SQLException SqlException) {
            System.out.println("Error in RoleDAO/getAll: " + SqlException.getMessage());
        }
        return roles;
    }

    // Method to insert a new role into the DB.
    @Override
    public void storeOne(Role type) {
        String sqlRoleImport = "INSERT INTO role (roleId, name) VALUES (?, ?)";
        int primaryKey;
        try {
            setupPreparedStatementWithKey(sqlRoleImport);
            preparedStatement.setInt(1, type.getRoleId());
            preparedStatement.setString(2, type.getName());

        } catch (SQLException SqlException) {
            System.out.println("Error in RoleDAO/storeOne: " + SqlException.getMessage());
        }
    }

    @Override
    public void updateOne(Role type) {

    }

    @Override
    public void deleteOneById(int id) {

    }
}
