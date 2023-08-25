package se.lexicon.course_manager_assignment.data.service.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ModelToDto.class})
public class ModelToDtoTest {

    @Autowired
    private Converters testObject;
    private Random random = new Random();

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
    }

    //write your tests here

    private String randomString(int length) {
        final String values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(values.charAt(random.nextInt(values.length())));
        }
        return builder.toString();
    }

    public static void isSame(Student student, StudentView studentView) {
        assertEquals(student.getId(), studentView.getId());
        assertEquals(student.getEmail(), studentView.getEmail());
        assertEquals(student.getName(), studentView.getName());
        assertEquals(student.getAddress(), studentView.getAddress());
    }

    public static void isSame(Course course, CourseView courseView) {
        assertEquals(course.getId(), courseView.getId());
        assertEquals(course.getCourseName(), courseView.getCourseName());
        assertEquals(course.getStartDate(), courseView.getStartDate());
        assertEquals(course.getWeekDuration(), courseView.getWeekDuration());
    }

    @Test
    void testMultipleStudentConversion() {

        List<Student> randomStudents = IntStream.rangeClosed(1, 32)
                .mapToObj(id -> new Student(id, randomString(5) + " " + randomString(5), randomString(4) + "@example.org", randomString(10)))
                .collect(Collectors.toList());
        List<StudentView> studentViews = testObject.studentsToStudentViews(randomStudents);

        for (int i = 0; i < randomStudents.size(); i++) {
            Student student = randomStudents.get(i);
            StudentView studentView = studentViews.get(i);

            isSame(student, studentView);
        }
    }

    @Test
    void testMultipleCourseConversion() {
        List<Course> randomCourses = IntStream.rangeClosed(1, 32)
                .mapToObj(id -> new Course(id, randomString(10), LocalDate.now().plusDays(random.nextInt(30)), random.nextInt(12), new ArrayList<>()))
                .collect(Collectors.toList());
        List<CourseView> courseViews = testObject.coursesToCourseViews(randomCourses);

        for (int i = 0; i < randomCourses.size(); i++) {
            Course course = randomCourses.get(i);
            CourseView courseView = courseViews.get(i);

            isSame(course, courseView);
        }
    }
}
