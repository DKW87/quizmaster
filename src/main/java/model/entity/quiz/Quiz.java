package model.entity.quiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

  private List<Question> questions;

  public Quiz() {
    super();
    questions = new ArrayList<>();
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }
}
