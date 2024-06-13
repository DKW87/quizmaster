/**
 * @author Mack Bakkum
 * @project Quizmaster
 * @created 12/06/2024 - 13:30
 */

package database.mysql;

import model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO extends AbstractDAO {

    public RoleDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    // Method so users retrieved from DB can get their role assigned in the creation of the user. See UserDAO.getUserPerId
    public Role getUserRoleById(int roleId) {
        String sqlGetRole = "SELECT * FROM role WHERE roleId = ?";
        try {
            setupPreparedStatement(sqlGetRole);
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = executeSelectStatement();
            if (resultSet.next()) {
                String roleName = resultSet.getString("roleName");
                Role role = new Role(roleName);
                role.setRoleId(roleId);
                return role;
            }
        } catch (SQLException e) {
            System.out.println("Error in RoleDAO/getUserRole: " + e.getMessage());
        }
        return null;
    }

    public Role getUserRoleByName(String roleName) {
        String sqlGetRoleString = "SELECT * FROM role WHERE name = ?";
        try {
            setupPreparedStatement(sqlGetRoleString);
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
}
