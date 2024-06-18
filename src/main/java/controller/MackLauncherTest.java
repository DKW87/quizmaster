/**
 * @author Mack Bakkum
 * @project QuizMaster
 * @created 13/06/2024 - 13:00
 */

package controller;

import database.mysql.DBAccess;
import model.User;
import database.mysql.UserDAO;
import database.mysql.RoleDAO;

import java.io.IOException;
import java.util.List;

import static utils.Util.convertListToUsers;

public class MackLauncherTest {

    public static void main(String[] args) throws IOException {

        DBAccess dbAccess = new DBAccess("zbakkumm", "bakkumm", "1J.cINqCPBBcHJ");
        dbAccess.openConnection();

        RoleDAO roleDAO = new RoleDAO(dbAccess);
        UserDAO userDAO = new UserDAO(dbAccess);

        List<User> users = userDAO.getAll();
        List<User> users2 = userDAO.getByRoleID(2);
        User user = userDAO.getByName("talmama");

        System.out.println(user);




//        // Small CSV to check import into Java.
//        String csvFilePath = "resources/gebruikersV6.csv";
//        List<User> importUsersTest = convertListToUsers(csvFilePath);

        // test print from import above.
//        for (User user : importUsersTest) {
//            System.out.println(user.getUserName());
//            System.out.println(user.getPassword());
//            System.out.println(user.getFirstName());
//            System.out.println(user.getInfix());
//            System.out.println(user.getLastName());
//            System.out.println(user.getRole());
//            System.out.println();
//            System.out.println("----");
//            System.out.println();
//        }
//
//        for (User user : importUsersTest) {
//            userDAO.storeOne(user);
//            System.out.printf("User %s added to DB\n", user.getUserName());
//        }


    }

}

    /*           Role Student = new Role("Student");
        Student.setRoleId(1);
        Role Coordinator = new Role("Coordinator");
        Coordinator.setRoleId(2);
        Role Docent = new Role("Docent");
        Docent.setRoleId(3);
        Role Administrator = new Role("Administrator");
        Administrator.setRoleId(4);
        Role functioneelBeheerder = new Role("Functioneel Beheerder");
        functioneelBeheerder.setRoleId(5);

        String csvFilePath = "resources/GebruikersTest.csv";
        List<User> userListTest = new ArrayList<>();

        User talmama = new User("talmama", "passwordTest123", "Mariella", "Talman", Student);
        User knobbse = new User("knobbse", "pw123", "Selvi", "Knobben", Coordinator);
        User engelya = new User("engelya", "jBY4mjzG07", "Yaniek", "Van", "Engelenburg", Student);
        User goddina = new User("goddina", "T84$br", "Nathaly", "Goddijn", Administrator);
        userListTest.add(talmama);
        userListTest.add(knobbse);
        userListTest.add(engelya);
        userListTest.add(goddina);

        for (User user : userListTest) {
            System.out.println(user.getUserName());
            System.out.println(user.getFirstName());
            System.out.println(user.getInfix());
            System.out.println(user.getLastName());
            System.out.println(user.getRole());
        }
    }
    */
