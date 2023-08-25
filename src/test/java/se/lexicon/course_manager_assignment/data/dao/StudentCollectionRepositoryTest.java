package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    //Write your tests here

    @BeforeEach
    void setup() {
        testObject.createStudent("Test Student", "test.student.vxo.se", "Testgatan 12");
    }

    @Test
    void createStudentTest() {
        // setup adds student, check if it exists
        assertEquals(testObject.findAll().size(), 1);
    }

    @Test
    void findByEmailIgnoreCaseTest() {
        assertNotNull(testObject.findByEmailIgnoreCase("Test.Student.VXO.se"));
    }

    @Test
    void findByNameContainsTest() {
        assertNotNull(testObject.findByNameContains("Test"));
    }

    @Test
    void findByIdTest() {
        assertNotNull(testObject.findById(1));
    }

    @Test
    void findAllTest() {
        assertEquals(testObject.findAll().size(), 1);
    }

    @Test
    void removeStudentTest() {
        testObject.removeStudent(testObject.findById(1));
        assertTrue(testObject.findAll().isEmpty());
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
