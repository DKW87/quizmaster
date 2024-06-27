package model;

/**
 * @author Danny KWANT
 * @project Quizmaster
 * @created 12/06/2024 - 14:28
 */
public class Question {

    // attributes

    private int questionId;
    private String questionDescription;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private Quiz quiz;

    // constructors

    public Question(String questionDescription, String answerA, String answerB, String answerC, String answerD, Quiz quiz) {
        this.questionDescription = questionDescription;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.quiz = quiz;
    }

    // methods

    @Override
    public String toString() {
        return "Vraag [" + questionId + "]: " + questionDescription +".\n";
    }

    // getters and setters

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

} // class
