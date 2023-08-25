package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        return converters.courseToCourseView(courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration()));
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        Course course = courseDao.findById(form.getId());
        if (course == null) {
            return null;
        }

        course.setCourseName(form.getCourseName());
        course.setStartDate(form.getStartDate());
        course.setWeekDuration(form.getWeekDuration());
        // no need to set students i think

        return course.intoView();
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        return courseDao.findByNameContains(courseName).stream().map(converters::courseToCourseView).collect(Collectors.toList());
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        return courseDao.findByDateBefore(end).stream().map(converters::courseToCourseView).collect(Collectors.toList());
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        return courseDao.findByDateAfter(start).stream().map(converters::courseToCourseView).collect(Collectors.toList());
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        return courseDao.findById(courseId).enrollStudent(studentDao.findById(studentId));
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        return courseDao.findById(courseId).unenrollStudent(studentDao.findById(studentId));
    }

    @Override
    public CourseView findById(int id) {
        return converters.courseToCourseView(courseDao.findById(id));
    }

    @Override
    public List<CourseView> findAll() {
        return courseDao.findAll().stream().map(converters::courseToCourseView).collect(Collectors.toList());
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        return courseDao.findByStudentId(studentId).stream().map(converters::courseToCourseView).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCourse(int id) {
        return courseDao.removeCourse(courseDao.findById(id));
    }
}
