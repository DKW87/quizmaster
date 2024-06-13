package database.mysql;

import model.StudentCourse;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Juni 2024 - 18:15
 */
public class StudentCourseDAO extends AbstractDAO {

    // FIXME: implementeer DAO's
//    private final CourseDAO CourseDao = new CourseDAO(dbAccess);
//    private final UserDAO  userDao = new UserDAO(dbAccess);

    public StudentCourseDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    /**
     * Retrieves a list of student courses for a given student ID, including the course name, difficulty,
     * coordinator, enrollment date, and dropout date.
     *
     * @param  studentId  the ID of the student
     * @return            a list of StudentCourse objects representing the student's courses
     */
    public List<StudentCourse> getCoursesByStudent(int studentId) {
        String sql = "select * from StudentCourse where studentId = ?";

        List<StudentCourse> studentCourses = new ArrayList<>();
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, studentId);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                LocalDate enrollDate = resultSet.getDate("enrollDate").toLocalDate();
                LocalDate dropoutDate = resultSet.getDate("dropoutDate").toLocalDate();
                int courseId = resultSet.getInt("courseId");
                // TODO: add Course and Difficulty to StudentCourse class
//                var user = userDao.getOneById(studentId);
//                var course = CourseDao.getOneById(courseId);
//                StudentCourse studentCourse = new StudentCourse(user, course);
                studentCourses.add(null);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentCourses;
    }


    public void storeOne(StudentCourse studentCourse) {

        String sql = "INSERT INTO StudentCourse(studentId, courseId, enrollDate) VALUES(?, ?, ?);";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, studentCourse.getStudent().getUserId());
            this.preparedStatement.setInt(2, studentCourse.getCourse().getCourseId());
            this.preparedStatement.setDate(3, Date.valueOf(studentCourse.getEnrollDate()));
            this.executeManipulateStatement();
        } catch (SQLException sqlFout) {
            System.out.println(sqlFout.getMessage());
        }

    }
}
