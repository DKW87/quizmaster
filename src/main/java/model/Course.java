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
    private Difficulty difficulty;

    public Course(String name, User coordinator, Difficulty difficulty) {
        this(0, name, coordinator, difficulty);

    }
    public Course(int courseId, String name, User coordinator, Difficulty difficulty) {
        this.courseId = courseId;
        setName(name);
        this.coordinator = coordinator;
        this.difficulty = difficulty;

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
        // ? check if name is not blank
        if (name.isBlank() ) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
    }


    public User getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", coordinator=" + coordinator +
                ", difficulty=" + difficulty +
                '}';
    }
}
