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
//    private final DifficultyDAO difficultyDao = new DifficultyDAO(dbAccess);
//    private final UserDAO  userDao = new UserDAO(dbAccess);

    public CourseDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";

        try {
            this.setupPreparedStatement(sql);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                String name = resultSet.getString("name");
                int coordinatorId = resultSet.getInt("coordinatorId");
                int difficultyId = resultSet.getInt("difficultyId");
                // TODO: @MacK and @Danny --> Ik heb userDao en difficultyDao nodig
                //                 var coordinator = userDao.getOneById(coordinatorId);
//                 var difficulty = difficultyDao.getOneById(difficultyId);

                //Course course = new Course(courseId, name, coordinator, difficulty);
                // FIXME : courses.add(course);
                courses.add(null);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Retrieves a list of courses associated with a given coordinator ID.
     *
     * @param  coordinatorId  the ID of the coordinator
     * @return                a list of Course objects representing the courses associated with the coordinator
     */
    public List<Course> getCoursesByCoordinator(int coordinatorId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE coordinatorId = ?";

        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setInt(1, coordinatorId);
            ResultSet resultSet = this.executeSelectStatement();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("courseId");
                String name = resultSet.getString("name");

                int difficultyId = resultSet.getInt("difficultyId");
                // TODO: @MacK and @Danny --> Ik heb userDao en difficultyDao nodig

//                 var difficulty = difficultyDao.getOneById(difficultyId);
//                    var coordinator = userDao.getOneById(coordinatorId);

                //Course course = new Course(courseId, name, coordinator, difficulty);
                // FIXME : courses.add(course);
                courses.add(null);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public Course getById(int id) {
        Course course = null;

        String sql = "SELECT * FROM course WHERE courseId = ?";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setInt(1, id);
            ResultSet resultSet = this.executeSelectStatement();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int difficultyId = resultSet.getInt("difficultyId");
                int coordinatorId = resultSet.getInt("coordinatorId");
                // FIXME: difficultyId, coordinatorId
//                var coordinator = userDao.getById(coordinatorId);
//                var difficulty = difficultyDAO.getById(difficultyId);
//                course = new Course(id, name, difficultyId, coordinatorId);
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        String sql = "SELECT * FROM course WHERE name = ?";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, courseName);
            ResultSet resultSet = this.executeSelectStatement();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int difficultyId = resultSet.getInt("difficultyId");
                int coordinatorId = resultSet.getInt("coordinatorId");
                // FIXME: difficultyId, coordinatorId
//                var coordinator = userDao.getById(coordinatorId);
//                var difficulty = difficultyDAO.getById(difficultyId);
//                course = new Course(id, name, coordinator, difficulty);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    @Override
    public void storeOne(Course cursus) {
        String sql = "INSERT INTO Course(name, difficultyId, coordinatorId) VALUES (?, ?, ?);";
        int primaryKey;
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, cursus.getName());
            // FIXME: difficultyId, coordinatorId
//            preparedStatement.setInt(2, cursus.getDifficulty().getId());
//            preparedStatement.setInt(3, cursus.getCoordinator().getId());
            primaryKey = this.executeInsertStatementWithKey();
            cursus.setCourseId(primaryKey);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a course from the database with the given course ID.
     *
     * @param  courseId  the ID of the course to be deleted
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
     * @throws SQLException if there is an error executing the SQL statement
     */
    public void updateOne(Course course) {
        String sql = "UPDATE Course SET name = ?, difficultyId = ?, coordinatorId = ? WHERE courseId = ?;";
        try {
            this.setupPreparedStatementWithKey(sql);
            this.preparedStatement.setString(1, course.getName());
            this.preparedStatement.setInt(2, course.getDifficulty().getDifficultyId());
            this.preparedStatement.setInt(3, course.getCoordinator().getUserId());
            this.preparedStatement.setInt(4, course.getCourseId());
            this.executeManipulateStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}


