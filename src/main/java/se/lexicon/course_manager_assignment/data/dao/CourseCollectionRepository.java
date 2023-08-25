package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.model.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
        Course course = new Course(courseName, startDate, weekDuration, new ArrayList<>());
        this.courses.add(course);
        return course;
    }

    @Override
    public Course findById(int id) {
        return this.findAll()
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        return this.findAll().stream().filter(a -> a.getCourseName().contains(name)).collect(Collectors.toList());
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        return this.findAll().stream().filter(a -> a.getStartDate().isBefore(end)).collect(Collectors.toList());
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        return this.findAll().stream().filter(a -> a.getStartDate().isAfter(start)).collect(Collectors.toList());
    }

    @Override
    public Collection<Course> findAll() {
        return this.courses;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        return this.findAll()
                .stream()
                .filter(a -> a.getStudents().stream().anyMatch(b -> b.getId() == studentId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeCourse(Course course) {
        return this.courses.remove(course);
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
