package database.mysql;

import model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Juni 2024 - 13:23
 */
public class CourseDAO extends AbstractDAO implements GenericDAO<Course> {

    // FIXME: implementeer DAO's
    private final DifficultyDAO difficultyDao = new DifficultyDAO(dbAccess);
    private final UserDAO userDao = new UserDAO(dbAccess);
    private final StudentCourseDAO studentCourseDAO = new StudentCourseDAO(dbAccess);

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
                Course course  = createCourseFromResultSet(resultSet);
                var studentCount = studentCourseDAO.getCourseByStudentCount(course.getCourseId());
                course.setStudentCount(studentCount);
                courses.add(course);

            }
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/getAll: " + e.getMessage());
        }
        return courses;
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
                var studentCount = studentCourseDAO.getCourseByStudentCount(course.getCourseId());
                course.setStudentCount(studentCount);
                courses.add(null);
            }
        } catch (SQLException e) {
            System.out.println("Error in CourseDAO/getCoursesByCoordinator: " + e.getMessage());
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
    public void storeOne(Course cursus) {
        String sql = "INSERT INTO Course(name, difficultyId, coordinatorId) VALUES (?, ?, ?);";
        int primaryKey;
        try {
            this.setupPreparedStatementWithKey(sql);
            setCourseToQuery(cursus);
            primaryKey = this.executeInsertStatementWithKey();
            cursus.setCourseId(primaryKey);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a course from the database with the given course ID.
     *
     * @param courseId the ID of the course to be deleted
     */
    public void deleteOneById(int courseId) {
        String sql = "DELETE FROM Course WHERE courseId = ?;";
        try {
            this.setupPreparedStatement(sql);
            this.preparedStatement.setInt(1, courseId);
            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates a course in the database with the provided course object.
     *
     * @param course the course object containing the updated values
     */
    public void updateOne(Course course)  {
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
     * @param  course  the Course object to set to the query
     */
    private void setCourseToQuery(Course course) throws SQLException {
        this.preparedStatement.setString(1, course.getName());
        this.preparedStatement.setInt(2, course.getDifficulty().getDifficultyId());
        this.preparedStatement.setInt(3, course.getCoordinator().getUserId());
    }


}


