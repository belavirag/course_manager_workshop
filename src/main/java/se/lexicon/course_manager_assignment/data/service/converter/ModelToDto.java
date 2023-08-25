package se.lexicon.course_manager_assignment.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelToDto implements Converters {
    @Override
    public StudentView studentToStudentView(Student student) {
        if (student == null) {
            return null;
        }

        return student.intoView();
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        if (course == null) {
            return null;
        }

        return course.intoView();
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {
        return courses.stream().map(Course::intoView).collect(Collectors.toList());
    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        return students.stream().map(Student::intoView).collect(Collectors.toList());
    }
}
