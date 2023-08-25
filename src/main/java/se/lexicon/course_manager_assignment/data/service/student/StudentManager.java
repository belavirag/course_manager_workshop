package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentManager implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final Converters converters;

    @Autowired
    public StudentManager(StudentDao studentDao, CourseDao courseDao, Converters converters) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.converters = converters;
    }

    @Override
    public StudentView create(CreateStudentForm form) {
        return converters.studentToStudentView(studentDao.createStudent(form.getName(), form.getEmail(), form.getAddress()));
    }

    @Override
    public StudentView update(UpdateStudentForm form) {
        Student student = studentDao.findById(form.getId());
        if (student == null) {
            return null;
        }

        student.setName(form.getName());
        student.setEmail(form.getEmail());
        student.setAddress(form.getAddress());

        return student.intoView();
    }

    @Override
    public StudentView findById(int id) {
        return converters.studentToStudentView(this.studentDao.findById(id));
    }

    @Override
    public StudentView searchByEmail(String email) {
        return converters.studentToStudentView(studentDao.findByEmailIgnoreCase(email));
    }

    @Override
    public List<StudentView> searchByName(String name) {
        return this.studentDao.findByNameContains(name).stream().map(converters::studentToStudentView).collect(Collectors.toList());
    }

    @Override
    public List<StudentView> findAll() {
        return this.studentDao.findAll().stream().map(converters::studentToStudentView).collect(Collectors.toList());
    }

    @Override
    public boolean deleteStudent(int id) {
        Student student = this.studentDao.findById(id);
        if (student == null) {
            return false;
        }

        return this.studentDao.removeStudent(student);
    }
}
