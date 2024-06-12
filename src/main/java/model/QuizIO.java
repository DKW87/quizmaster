package model;

public class QuizIO {
    private String column1;
    private String column2;
    private int column3;
    private String column4;

    // all args constructor
    public QuizIO(String column1, String column2, int column3, String column4) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.column4 = column4;
    }

    // getters en setters
    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public int getColumn3() {
        return column3;
    }

    public void setColumn3(int column3) {
        this.column3 = column3;
    }

    public String getColumn4() {
        return column4;
    }

    public void setColumn4(String column4) {
        this.column4 = column4;
    }


    // toString
    @Override
    public String toString() {
        StringBuilder toStringQuizIO = new StringBuilder();
        toStringQuizIO.append(String.format("QuizName: %s QuizDifficulty: %s QuizPoints: %d Part of Course: %s "
        , column1, column2, column3, column4));
    return toStringQuizIO.toString();
    }
}
