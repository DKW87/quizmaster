package model;

import java.time.LocalDate;

/**
 * @author Zahir Ekrem SARITEKE
 * @project quizmaster
 * @created 12 Juni 2024 - 18:16
 */
public class StudentCourse {
    private int studentCourseId;
    private User student;
    private Course course;
    private LocalDate enrollDate;
    private LocalDate dropoutDate;

    public StudentCourse(User student, Course course, LocalDate enrollDate) {
        this.student = student;
        this.course = course;
        this.enrollDate = enrollDate;
        this.dropoutDate = null;
    }

    public StudentCourse(User student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollDate = LocalDate.now();
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public LocalDate getDropoutDate() {
        return dropoutDate;
    }

    public int getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(int studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    @Override
    public String toString() {
        return student.getFirstName() + " " + student.getLastName() + " - " + course.getName();
    }


}
