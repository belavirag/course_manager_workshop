package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDtoTest;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = { Student.class, ModelToDto.class})
public class StudentTest {
    @Autowired
    private Converters converters;

    @Test
    @DisplayName("Test context")
    void testContext() {
        assertNotNull(converters, "Test context not setup correctly!");
    }

    Student getExample() {
        return new Student(1, "A Name", "a.name@test.com", "Somewhere Rd");
    }

    @Test
    void studentViewTest() {
        Student student = getExample();
        StudentView studentView = converters.studentToStudentView(student);

        ModelToDtoTest.isSame(student, studentView);
    }

    @Test
    void testEqualsAndHashcode() {
        Student studentOne = getExample();
        Student sameStudent =  new Student(1, "A Name", "a.name@test.com", "Somewhere Rd");
        Student differentStudent = new Student(2, "Other Name", "other.name@example.org", "Somewhere St");

        assertEquals(studentOne, sameStudent);
        assertNotEquals(studentOne, differentStudent);
        assertEquals(studentOne.hashCode(), sameStudent.hashCode());
        assertNotEquals(studentOne.hashCode(), differentStudent.hashCode());

    }
}
