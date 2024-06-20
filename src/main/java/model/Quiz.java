package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.mysql.CourseDAO;
import database.mysql.DifficultyDAO;
import model.*;

/**
 * @author Rob Jansen
 * @project quizmaster
 * @created 12 juni 2024 - 10:00
 */


public class Quiz {
    private int quizId;
    private String name;
    private Difficulty difficulty;
    private int passMark;
    private int quizPoints;
    private Course course;
    private int questionsInQuizCount;

    // all args constructor:
    public Quiz(int quizId, String name, int passMark, int quizPoints, Course course, Difficulty difficulty, int questionsInQuizCount) {
        this.quizId = quizId;
        this.name = name;
        this.passMark = passMark;
        this.quizPoints = quizPoints;
        this.course = course;
        this.difficulty = difficulty;
        this.questionsInQuizCount = questionsInQuizCount;
    }

    // getters en setters
    public int getQuizId() {
        return quizId;
    }

    // nu nog 1 Setter - aanvullen waar nodig.
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return name;
    }

    public int getPassMark() {
        return passMark;
    }

    public int getQuizPoints() {
        return quizPoints;
    }

    public Course getCourse() {
        return course;
    }



    public Difficulty getQuizDifficulty() {
        return difficulty;
    }

    public int getQuestionsInQuizCount() {
        return questionsInQuizCount;
    }


    // toString
    @Override
    public String toString() {
        StringBuilder toStringQuiz = new StringBuilder();
        toStringQuiz.append("Quiz ID: " + this.quizId + " ");
        toStringQuiz.append("Name: " + this.name + " ");
        toStringQuiz.append("Course: " + this.course.getName() + " ");
        toStringQuiz.append("Quizpoints: " + this.quizPoints + " ");
        toStringQuiz.append("Difficulty: " + this.difficulty.getName() + " ");
        toStringQuiz.append("Passmark: " + this.passMark + " ");
        toStringQuiz.append("#Questions in Quiz: " + this.questionsInQuizCount + " ");
        return toStringQuiz.toString();
    }

}
