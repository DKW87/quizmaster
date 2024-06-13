package database.mysql;

import model.Difficulty;
import model.Question;
import model.Quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 12/06/2024 - 16:04
 */
public class DifficultyDAO extends AbstractDAO implements GenericDAO<Difficulty> {

    public DifficultyDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Difficulty> getAll() {
        List<Difficulty> difficulties = new ArrayList<Difficulty>();
        String sql = "SELECT * FROM difficulty;";
        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int difficultyId = resultSet.getInt("difficultyId");
                String name = resultSet.getString("name");
                Difficulty difficulty = new Difficulty(name);
                difficulty.setDifficultyId(difficultyId);
                difficulties.add(difficulty);
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return difficulties;
    }

    @Override
    public Difficulty getById(int id) {
        Difficulty difficulty = null;
        String sql = "SELECT * FROM Difficulty WHERE difficultyId = ?;";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, id);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int difficultyId = resultSet.getInt("difficultyId");
                String difficultyName = resultSet.getString("name");
                difficulty = new Difficulty(difficultyName);
                difficulty.setDifficultyId(difficultyId);
                return difficulty;
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
        return difficulty;
    }

    @Override
    public Difficulty getByName(String name) {
        Difficulty difficulty = null;
        String sql = "SELECT * FROM Difficulty WHERE name = ?;";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setString(1, name);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int difficultyId = resultSet.getInt("difficultyId");
                String difficultyName = resultSet.getString("name");
                difficulty = new Difficulty(difficultyName);
                difficulty.setDifficultyId(difficultyId);
                return difficulty;
            }
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getMessage());
        }
        return difficulty;
    }

    @Override
    public void storeOne(Difficulty difficulty) {
        String sql = "INSERT INTO Difficulty(name) VALUES ?;";
        int pKey = 0;
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, difficulty.getName());
            pKey = this.executeInsertStatementWithKey();
            difficulty.setDifficultyId(pKey);
        } catch (SQLException error) {
            System.out.println("The following exception occurred: " + error.getErrorCode());
        }
    }

} // class
