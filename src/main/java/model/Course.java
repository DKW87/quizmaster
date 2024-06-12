package model;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 11 Haziran Salı 2024 - 21:56
 */
public class Course {

    private int courseId;
    private String name;
    private User coordinator;
    //private Difficulty difficulty;

    // TODO: @ekrem Add difficulty to course class

    public Course(int courseId, String name, User coordinator) {
        this.courseId = courseId;
        this.name = name;
        this.coordinator = coordinator;

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", coordinator=" + coordinator +
                '}';
    }
}
