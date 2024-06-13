package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public List<Question> convertListToObjects(List<String> list) {
        List<Question> questionList = new ArrayList<>();
            for (String string : list) {
                String[] splitLine = string.split(";");
                String questionDescription = splitLine[0];
                String answerA = splitLine[1];
                String answerB = splitLine[2];
                String answerC = splitLine[3];
                String answerD = splitLine[4];
                Quiz quiz = null; // TODO implement get quiz by name
                Question question = new Question(questionDescription, answerA, answerB, answerC, answerD, quiz);
                questionList.add(question);
            }
        return questionList;
    }

    @Override
    public String toString() {
        return "Question [" + questionId + "]: " + questionDescription +".\n";
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

} // class
