/**
 * @author Mack Bakkum
 * @project QuizMaster
 * @created 12/06/24 - 07:25
 */

package model;

import database.mysql.GenericDAO;

import java.util.List;

public class Role implements GenericDAO {

    private int roleId;
    private String name;

    public Role(String name) {
        this.name = name;
        this.roleId = 0; //Default value set to 0. Use setRoleId to change value per role.
    }

    public String toString(){
        return "Role ID + Name: " + roleId + " " + name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List getAll() {
        return List.of();
    }

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public Object getByName(String name) {
        return null;
    }

    @Override
    public void storeOne(Object type) {

    }
}
