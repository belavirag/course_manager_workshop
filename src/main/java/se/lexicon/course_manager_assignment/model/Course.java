package se.lexicon.course_manager_assignment.model;

import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.dto.views.CourseView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class Course implements Serializable {
    private int id;
    private String courseName;
    private LocalDate startDate;
    private int weekDuration;
    private Collection<Student> students;

    public Course(int id, String courseName, LocalDate startDate, int weekDuration, Collection<Student> students) {
        this.id = id;
        this.courseName = courseName;
        this.startDate = startDate;
        this.weekDuration = weekDuration;
        this.students = students;
    }

    public Course(String courseName, LocalDate startDate, int weekDuration, Collection<Student> students) {
        this(CourseSequencer.nextCourseId(), courseName, startDate, weekDuration, students);
    }

    public Course() {

    }

    public boolean enrollStudent(Student student) {
        return students.add(student);
    }

    public boolean unenrollStudent(Student student) {
        return students.remove(student);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getWeekDuration() {
        return weekDuration;
    }

    public void setWeekDuration(int weekDuration) {
        this.weekDuration = weekDuration;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public CourseView intoView() {
        return new CourseView(id, courseName, startDate, weekDuration, students.stream().map(Student::intoView).collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, startDate, weekDuration, students);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (weekDuration != course.weekDuration) return false;
        if (!Objects.equals(courseName, course.courseName)) return false;
        if (!Objects.equals(startDate, course.startDate)) return false;
        return Objects.equals(students, course.students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate +
                ", weekDuration=" + weekDuration +
                ", students=" + students +
                '}';
    }
}
