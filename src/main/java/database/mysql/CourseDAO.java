package database.mysql;

import model.Course;
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
 * @created 12 Juni 2024 - 13:23
 */
public class CourseDAO extends AbstractDAO implements GenericDAO<Course> {

    private final DifficultyDAO difficultyDao = new DifficultyDAO(dbAccess);
    private final UserDAO userDao = new UserDAO(dbAccess);


    public CourseDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                Course course = createCourseFromResultSet(resultSet);
                var studentCount = this.getCourseByStudentCount(course.getCourseId());
                course.setStudentCount(studentCount);
                courses.add(course);

            }
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/getAll: " + e.getMessage());
        }
        return courses;
    }

    /**
     * Retrieves a list of unassigned courses for a given student ID.
     *
     * @param studentId the ID of the student
     * @return a list of Course objects representing the unassigned courses
     */
    public List<Course> getUnAssignedCoursesByStudent(int studentId) {
        List<Course> courses = new ArrayList<>();
        String sql = "select  *\n" +
                "from Course as c\n" +
                "where c.courseId\n" +
                "not in (select StudentCourse.courseId\n" +
                "        from StudentCourse\n" +
                "        where dropoutDate is null and  studentId = ?);";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, studentId);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                Course course = createCourseFromResultSet(resultSet);
                var studentCount = this.getCourseByStudentCount(course.getCourseId());
                course.setStudentCount(studentCount);
                courses.add(course);

            }
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/getUnAssignedCoursesByStudent: " + e.getMessage());
        }
        return courses;
    }


    /**
     * Retrieves a list of student courses for a given student ID, including the course name, difficulty,
     * coordinator, enrollment date, and dropout date.
     *
     * @param studentId the ID of the student
     * @return a list of StudentCourse objects representing the student's courses
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
                int courseId = resultSet.getInt("courseId");
                int studentCourseId = resultSet.getInt("studentCourseId");
                var user = userDao.getById(studentId);
                var course = this.getById(courseId);
                StudentCourse studentCourse = new StudentCourse(user, course, enrollDate);
                studentCourse.setStudentCourseId(studentCourseId);
                studentCourses.add(studentCourse);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentCourses;
    }

    /**
     * Retrieves a list of courses associated with a given coordinator ID.
     *
     * @param coordinatorId the ID of the coordinator
     * @return a list of Course objects representing the courses associated with the coordinator
     */
    public List<Course> getCoursesByCoordinator(int coordinatorId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course WHERE coordinatorId = ?";

        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setInt(1, coordinatorId);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                Course course = createCourseFromResultSet(resultSet);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }


    @Override
    public Course getById(int id) {
        Course course = null;
        String sql = "SELECT * FROM Course WHERE courseId = ?";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setInt(1, id);
            ResultSet resultSet = this.executeSelectStatement();
            if (resultSet.next()) {
                course = createCourseFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/getCourseById: " + e.getMessage());
        }
        return course;
    }


    /**
     * Finds a course by its name.
     *
     * @param courseName the name of the course to find
     * @return the course with the given name, or null if not found
     */
    @Override
    public Course getByName(String courseName) {
        Course course = null;

        String sql = "SELECT * FROM Course WHERE name = ?";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, courseName);
            ResultSet resultSet = this.executeSelectStatement();
            if (resultSet.next()) {
                course = createCourseFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/getCourseByName: " + e.getMessage());
        }

        return course;
    }

    @Override
    public void storeOne(Course course) {
        String sql = "INSERT INTO Course(name, difficultyId, coordinatorId) VALUES (?, ?, ?);";
        int primaryKey;
        try {
            this.setupPreparedStatementWithKey(sql);
            setCourseToQuery(course);
            primaryKey = this.executeInsertStatementWithKey();
            course.setCourseId(primaryKey);
        } catch (SQLException e) {
            throw new RuntimeException("Duplicate entry  " + course.getName() + " for key 'course.name_UNIQUE'", e);
        }
    }

    /**
     * Deletes a course from the database with the given course ID.
     *
     * @param courseId the ID of the course to be deleted
     */
    @Override
    public void deleteOneById(int courseId) {
        String sql = "DELETE FROM Course WHERE courseId = ?;";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, courseId);
            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
            throw new RuntimeException("Cannot delete or update a parent row: a foreign key constraint fails", e);
        }
    }


    /**
     * Updates a course in the database with the provided course object.
     *
     * @param course the course object containing the updated values
     */
    @Override
    public void updateOne(Course course) {
        String sql = "UPDATE Course SET name = ?, difficultyId = ?, coordinatorId = ? WHERE courseId = ?;";
        try {
            this.setupPreparedStatement(sql);
            setCourseToQuery(course);
            this.preparedStatement.setInt(4, course.getCourseId());
            this.executeManipulateStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a student to a course.
     *
     * @param userId   the ID of the student
     * @param courseId the ID of the course
     */
    public void addStudentToCourse(int userId, int courseId) {
        String sql = "INSERT INTO StudentCourse(studentId, courseId, enrollDate) VALUES (?, ?, ?);";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setInt(1, userId);
            this.preparedStatement.setInt(2, courseId);
            this.preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
            this.executeInsertStatementWithKey();
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/addStudentToCourse: " + e.getMessage());
        }

    }

    /**
     * Retrieves the number of students enrolled in a course.
     *
     * @param courseId the ID of the course
     * @return the number of students enrolled in the course
     */
    public int getCourseByStudentCount(int courseId) {
        String sql = "select count(*) as studentCount from StudentCourse where courseId = ?";

        int studentCount = 0;
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, courseId);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                studentCount = resultSet.getInt("studentCount");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentCount;
    }

    /**
     * Removes a student from a course by deleting the corresponding record in the StudentCourse table.
     *
     * @param studentCourseId the ID of the student-course record to be removed
     */
    public void removeStudentFromCourse(int studentCourseId) {
        String sql = "DELETE FROM StudentCourse WHERE  studentCourseId = ?;";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, studentCourseId);
            this.executeManipulateStatement();
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/removeStudentFromCourse: " + e.getMessage());
        }
    }

    /**
     * Deletes all records from the Course table in the database.
     */
    public void deleteAll() {
        String sql = "DELETE FROM Course;";
        try {
            this.setupPreparedStatement(sql);
            this.executeManipulateStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Bulk creates courses in the database by reading a CSV file and storing each course object.
     *
     * @throws SQLException if there is an error executing the SQL statement
     */
    public void bulkCreate(List<Course> courses) throws SQLException {
        for (Course course : courses) {
            this.storeOne(course);
        }
    }

    /**
     * Creates a Course object from the given ResultSet.
     *
     * @param resultSet the ResultSet containing the data for the Course
     * @return a Course object created from the data in the ResultSet
     * @throws SQLException if there is an error retrieving data from the ResultSet
     */
    private Course createCourseFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        int difficultyId = resultSet.getInt("difficultyId");
        int coordinatorId = resultSet.getInt("coordinatorId");
        int courseId = resultSet.getInt("courseId");
        var difficulty = difficultyDao.getById(difficultyId);
        var coordinator = userDao.getById(coordinatorId);
        return new Course(courseId, name, coordinator, difficulty);
    }


    /**
     * Sets the given Course object to the prepared statement for querying.
     *
     * @param course the Course object to set to the query
     */
    private void setCourseToQuery(Course course) throws SQLException {
        this.preparedStatement.setString(1, course.getName());
        this.preparedStatement.setInt(2, course.getDifficulty().getDifficultyId());
        this.preparedStatement.setInt(3, course.getCoordinator().getUserId());
    }


}


