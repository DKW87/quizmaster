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
    private List<Question> questions;
    private Difficulty difficulty;
    private int passMark;
    private int quizPoints;
    private Course course;

    // all args constructor:
    public Quiz(int quizId, String name, int passMark, int quizPoints, Course course, Difficulty difficulty) {
        this.quizId = quizId;
        this.name = name;
        this.passMark = passMark;
        this.quizPoints = quizPoints;
        this.course = course;
        this.questions = new ArrayList<>();
        this.difficulty = difficulty;
    }

    // getters en setters * now for all attributes, delete later once not used?
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return name;
    }

    public void setQuizName(String name) {
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

    public Difficulty getQuizDifficulty() {
        return difficulty;
    }
    public void setQuizDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // method to add questions to quiz
    // !NB: assumed the content of a Quiz will not be stored in the DB - only the result of the Quiz - otherwise add method to DAO
    // Selection for right questions for the right quiz not yet added to method (TODO Rob)
    // method input in UML change from Quiz quiz to Question question?
    public void addQuestion(Question question) {
        if (!this.questions.contains(question))
            this.questions.add(question);
    }


    // Methods:
    // ik heb deze hier ook geimplementeerd maar moeten we niet gewoon apart zetten omdat deze voor alle hetzelfde is?
    public List<String> convertCsvToList(String filePath) {
        List<String> questionList = new ArrayList<>();
        File file = new File(filePath);
        try{
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                questionList.add(input.nextLine());
            }
        }
        catch (FileNotFoundException error) {
            System.out.println("File not found.");
        }
        return questionList;
    }

    public List<Quiz> convertListToObjects(List<String> list) {
        List<Quiz> quizList = new ArrayList<>();
        for (String string : list) {
            String[] splitLine = string.split(";");
            String quizName = splitLine[0];
            String difficultyString = splitLine[1];
            String quizPointsString = splitLine[2];
            String courseString = splitLine[3];
            int quizId = 0;
            int passMark = 0;
            int quizPoints = Integer.parseInt(quizPointsString);
            var difficulty = DifficultyDAO.getByName(difficultyString);
            var course = CourseDAO.getByName(courseString);
            // hopelijk werkt het zo, ik kan het niet testen alleen....en getByName kan ik niet aanroepen terwijl die al wel bij CourseDAO bestaat (nog niet bij DifficultyDAO)
            Quiz quiz = new Quiz(quizId,quizName,passMark,quizPoints,course,difficulty);
            quizList.add(quiz);
        }
        return quizList;
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
