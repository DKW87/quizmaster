package model;

import java.util.List;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 12 juni 2024 - 10:00
 */


public class Quiz {
    private int quizId;
    private String name;
    private List<Question> questions;
    //private Difficulty difficulty;
    // TODO: @rob Add difficulty to course quiz
    private static int PASS_MARK;

    // all args constructor:
    public Quiz(int quizId, String name, List<Question> questions, int passMark) {
        this.quizId = quizId;
        this.name = name;
        this.questions = questions;
        this.PASS_MARK = passMark;
    }

    // getters en setters * now for all attributes, delete later once not used?
    public int getQuizId() {
        return quizId;
    }
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public int getPassMark() {
        return PASS_MARK;
    }
    public void setPassMark(int passMark) {
        PASS_MARK = passMark;
    }




// toString
@Override
    public String toString() {
        int QCounter =0;
        StringBuilder toStringQuiz = new StringBuilder();
        toStringQuiz.append("Quiz ID: " + quizId + " ");
        toStringQuiz.append("Name: " + name + "\n");
        toStringQuiz.append("Questions: ");
        for (Question question : questions) {
            QCounter++;
            toStringQuiz.append("Question: " + QCounter + "\n");
            toStringQuiz.append(question.toString() + "\n");
        }
        return toStringQuiz.toString();
}

}
