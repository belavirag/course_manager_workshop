package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    @Autowired
    private CourseDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    //Write your tests here

    @BeforeEach
    void setup() {
        testObject.createCourse("Test Course", LocalDate.now(), 12);
    }

    @Test
    void createCourseTest() {
        assertEquals(testObject.findAll().size(), 1);
    }

    @Test
    void findByIdTest() {
        assertNotNull(testObject.findById(1));
    }

    @Test
    void findByNameContainsTest() {
        assertNotNull(testObject.findByNameContains("Test"));
    }

    @Test
    void findByDateBeforeTest() {
        assertEquals(testObject.findByDateBefore(LocalDate.now().plusDays(1)).size(), 1);
    }

    @Test
    void findByDateAfterTest() {
        assertEquals(testObject.findByDateAfter(LocalDate.now().minusDays(1)).size(), 1);
    }

    @Test
    void findAllTest() {
        assertEquals(testObject.findAll().size(), 1);
    }

    @Test
    void findByStudentIdTest() {
        assertNotNull(testObject.findByStudentId(1));
    }

    @Test
    void removeCourseTest() {
        Course course = testObject.findById(1);
        assertNotNull(course);
        testObject.removeCourse(course);
        assertTrue(testObject.findAll().isEmpty());
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
