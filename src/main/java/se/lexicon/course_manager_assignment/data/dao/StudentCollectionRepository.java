package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        Student student = new Student(name, email, address);
        this.students.add(student);
        return student;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        return this.findAll()
                .stream()
                .filter(a -> a.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        return this.findAll()
                .stream()
                .filter(a -> a.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Student findById(int id) {
        return this.findAll()
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Student> findAll() {
        return this.students;
    }

    @Override
    public boolean removeStudent(Student student) {
        return this.students.remove(student);
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }
}
