package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.mysql.CourseDAO;
import database.mysql.DifficultyDAO;
import database.mysql.QuizDAO;
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
    private int quizPoints;
    private Course course;
    private int questionsInQuizCount;

    // all args constructor:
    public Quiz(int quizId, String name, int quizPoints, Course course, Difficulty difficulty, int questionsInQuizCount) {
        this.quizId = quizId;
        this.name = name;
        this.quizPoints = quizPoints;
        this.course = course;
        this.difficulty = difficulty;
        this.questionsInQuizCount = questionsInQuizCount;
    }

    public Quiz(int quizId, String name, int quizPoints, Course course, Difficulty difficulty) {
        this.quizId = quizId;
        this.name = name;
        this.quizPoints = quizPoints;
        this.course = course;
        this.difficulty = difficulty;
        this.questionsInQuizCount = 0;
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
    public String toString() {return this.name;}


}
