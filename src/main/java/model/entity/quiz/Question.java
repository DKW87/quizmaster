package model.entity.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {

  private String question;
  private List<Answer> answers;

  public Question(String question) {
    super();
    this.question = question;
    this.answers = new ArrayList<>();
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Answer> answers) {
    this.answers = answers;
  }
}
