package model;

public class QuizIO {
    private String quizName;
    private String quizDifficulty;
    private int quizPoints;
    private String quizCourse;
    private int QuizId;

    // all args constructor
    public QuizIO(String quizName, String quizDifficulty, int quizPoints, String quizCourse) {
        this.quizName = quizName;
        this.quizDifficulty = quizDifficulty;
        this.quizPoints = quizPoints;
        this.quizCourse = quizCourse;

    }

    // getters en setters
    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizDifficulty() {
        return quizDifficulty;
            }

    public void setQuizDifficulty(String quizDifficulty) {
        this.quizDifficulty = quizDifficulty;
    }

    public int getQuizPoints() {
        return quizPoints;
    }

    public void setQuizPoints(int quizPoints) {
        this.quizPoints = quizPoints;
    }

    public String getQuizCourse() {
        return quizCourse;

    }

    public void setQuizCourse(String quizCourse) {
        this.quizCourse = quizCourse;
    }

    public int getQuizId() {
        return QuizId;
    }
    public void setQuizId(int QuizId) {
        this.QuizId = QuizId;

    }

    // toString
    @Override
    public String toString() {
        StringBuilder toStringQuizIO = new StringBuilder();
        toStringQuizIO.append(String.format("QuizName: %s QuizDifficulty: %s QuizPoints: %d Part of Course: %s "
        , quizName, quizDifficulty, quizPoints, quizCourse));
    return toStringQuizIO.toString();
    }
}
