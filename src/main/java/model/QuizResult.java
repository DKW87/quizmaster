package model;

import java.time.LocalDate;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 11 juni 2024 - 22:15
 */
public class QuizResult implements Comparable<QuizResult> {


    private int resultId;
    private LocalDate date;
    private User student;
    private Quiz quiz;
    private int score;

    // ? All args constructor
    public QuizResult(int resultId, User student, Quiz quiz, int score) {
        this.resultId = resultId;
        this.student = student;
        this.quiz = quiz;
        this.score = score;
        this.date = LocalDate.now();
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Compares this QuizResult object with the specified QuizResult object for order.
     *
     * @param  obj   the QuizResult object to be compared.
     * @return      a negative integer, zero, or a positive integer as this QuizResult
     *              is less than, equal to, or greater than the specified QuizResult.
     */
    @Override
    public int compareTo(QuizResult obj) {
        return this.date.compareTo(obj.date);
    }
}
