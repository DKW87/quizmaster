package controller;
import model.*;

public class LauncherTestRob {
    public static void main(String[] args) {


    Quiz quizTestRob2 = new Quiz(1,"Test Quiz 2",2,7,new Course(1,"testCourse",new User(1,"Test Naam","TestPW","Rob","", new Role("Administrator")),new Difficulty("Moeilijk")),new Difficulty("DifQuiz"));

        System.out.println(quizTestRob2);


    }
}
