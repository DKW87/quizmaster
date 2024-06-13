package model;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 12/06/2024 - 14:31
 */
public class Difficulty {

    // attributes

    private int difficultyId;
    private String name;

    // constructors

    public Difficulty(String name) {
        this.name = name;
    }

    // methods

    @Override
    public String toString() {
        return name+"\n";
    }

    // getters and setters

    public int getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(int difficultyId) {
        this.difficultyId = difficultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

} // class
