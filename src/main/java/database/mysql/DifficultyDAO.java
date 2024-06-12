package database.mysql;

import model.Difficulty;

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
            System.out.println("De volgende uitzondering is opgetreden: " + error.getErrorCode());
        }
        return difficulties;
    }

    @Override
    public Difficulty getOneById(int id) {
        // TODO what do we do here? I'm not following..
        Difficulty difficulty = null;
        return difficulty;
    }

    @Override
    public void storeOne(Difficulty difficulty) {
        String sql = "INSERT INTO difficulty(name) VALUES ?;";
        int pKey = 0;
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, difficulty.getName());
            pKey = this.executeInsertStatementWithKey();
            difficulty.setDifficultyId(pKey);
        } catch (SQLException error) {
            System.out.println("De volgende uitzondering is opgetreden: " + error.getErrorCode());
        }
    }

} // class
