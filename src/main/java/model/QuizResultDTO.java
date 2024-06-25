package model;

import java.time.LocalDateTime;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 24 June Monday 2024 - 16:28
 */
public class QuizResultDTO {

    private int resultId;
    private LocalDateTime date;
    private int studentId;
    private int quizId;
    private int score;

    public QuizResultDTO(int resultId, int studentId, int quizId, int score) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.quizId = quizId;
        this.score = score;
        this.date = LocalDateTime.now();
    }

    public int getResultId() {
        return resultId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getStudentId() {
        return studentId;
    }


    public int getQuizId() {
        return quizId;
    }


    public int getScore() {
        return score;
    }


}
