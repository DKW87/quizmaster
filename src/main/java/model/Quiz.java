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
    private static int passMark;
    private int quizPoints;
    private Course course;

    // all args constructor:
    public Quiz(int quizId, String name, int passMark, int quizPoints, Course course) {
        this.quizId = quizId;
        this.name = name;
        this.passMark = passMark;
        this.quizPoints = quizPoints;
        this.course = course;
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

    /* not necessary?
    public List<Question> getQuestions() {
        return questions;
     }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
     */


    public int getPassMark() {
        return passMark;
    }

    public void setPassMark(int passMark) {
        passMark = passMark;
    }

    public int getQuizPoints() {
        return quizPoints;
    }

    public void setQuizPoints(int quizPoints) {
        this.quizPoints = quizPoints;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    // method to add questions to quiz
    // !NB: assumed the content of a Quiz will not be stored in the DB - only the result of the Quiz - otherwise add method to DAO
    // Selection for right questions for the right quiz not yet added to method (TODO Rob)
    // method input in UML change from Quiz quiz to Question question?
    public void addQuestion(Question question) {
        questions.add(question);
    }


    // toString
    @Override
    public String toString() {
        int QCounter = 0;
        StringBuilder toStringQuiz = new StringBuilder();
        toStringQuiz.append("Quiz ID: " + this.quizId + " ");
        toStringQuiz.append("Name: " + this.name + "\n");
        toStringQuiz.append("Quiz is part of course: " + this.course + "\n");
        toStringQuiz.append("Total Quizpoints: " + this.quizPoints + "\n");
        toStringQuiz.append("Questions: ");
        for (Question question : this.questions) {
            QCounter++;
            toStringQuiz.append("Question: " + QCounter + "\n");
            toStringQuiz.append(question.toString() + "\n");
        }
        return toStringQuiz.toString();
    }

}
