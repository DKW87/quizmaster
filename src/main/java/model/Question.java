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
    private Course course;

    // constructors

    public Question(String questionDescription, String answerA, String answerB, String answerC, String answerD, Course course) {
        this.questionDescription = questionDescription;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.course = course;
    }

    // methods

    // TODO @Danny

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

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

} // class
