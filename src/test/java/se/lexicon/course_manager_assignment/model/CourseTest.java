package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDtoTest;
import se.lexicon.course_manager_assignment.dto.views.CourseView;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = { Course.class, ModelToDto.class})
public class CourseTest {
    @Autowired
    private Converters converters;

    private Course testObject;

    @Test
    @DisplayName("Test context")
    void testContext() {
        assertNotNull(converters, "Test context not setup correctly!");
    }

    Course getExample() {
        return new Course(1, "Test Course", LocalDate.now(), 12, new ArrayList<>());
    }

    Student getExampleStudent() {
        return new Student(1, "Test Student", "email@example.org", "Somewhere Rd");
    }

    @BeforeEach
    void setup() {
        testObject = getExample();
    }

    @Test
    void courseViewTest() {
        Course course = getExample();
        CourseView courseView = converters.courseToCourseView(course);

        ModelToDtoTest.isSame(course, courseView);
    }

    @Test
    void enrollStudentTest() {
        Student student = getExampleStudent();

        assertTrue(testObject.enrollStudent(student));

        assertFalse(testObject.enrollStudent(student));
        assertFalse(testObject.enrollStudent(null));
    }

    @Test
    void unenrollStudent() {
        Student student = getExampleStudent();

        assertTrue(testObject.enrollStudent(student));
        assertTrue(testObject.unenrollStudent(student));

        assertFalse(testObject.unenrollStudent(null));
        assertFalse(testObject.unenrollStudent(new Student(2, "Other Student", "other.student@example.com", "Someplace St")));

    }

    @Test
    void testEqualsAndHashcode() {
        Course courseOne = getExample();
        Course sameCourse = new Course(1, "Test Course", LocalDate.now(), 12, new ArrayList<>());
        Course differentCourse = new Course(2, "Another Course", LocalDate.now().plusWeeks(1), 6, new ArrayList<>());

        assertEquals(courseOne, sameCourse);
        assertNotEquals(courseOne, differentCourse);
        assertEquals(courseOne.hashCode(), sameCourse.hashCode());
        assertNotEquals(courseOne.hashCode(), differentCourse.hashCode());
    }

    @AfterEach
    void tearDown() {
        CourseSequencer.setCourseSequencer(0);
    }
}
