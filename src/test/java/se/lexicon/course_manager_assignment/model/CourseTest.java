package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    @DisplayName("Test context")
    void testContext() {
        assertNotNull(converters, "Test context not setup correctly!");
    }

    Course getExample() {
        return new Course(1, "Test Course", LocalDate.now(), 12, new ArrayList<>());
    }

    @Test
    void courseViewTest() {
        Course course = getExample();
        CourseView courseView = converters.courseToCourseView(course);

        ModelToDtoTest.isSame(course, courseView);
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
}
