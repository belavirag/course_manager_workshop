package se.lexicon.course_manager_assignment.data.service.student;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentManager.class, CourseCollectionRepository.class, StudentCollectionRepository.class, ModelToDto.class})
public class StudentManagerTest {

    @Autowired
    private StudentService testObject;
    @Autowired
    private StudentDao studentDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(studentDao);
    }

    //Write your tests here

    CreateStudentForm getExampleForm() {
        return new CreateStudentForm(1, "Test Name", "tester@example.org", "Somewhere");
    }

    @BeforeEach
    void setup() {
        testObject.create(getExampleForm());
    }

    @Test
    void createTest() {
        CreateStudentForm form = getExampleForm();
        assertEquals(testObject.findAll().size(), 1);

        StudentView view = testObject.findAll().get(0);
        assertEquals(form.getId(), view.getId());
        assertEquals(form.getName(), view.getName());
        assertEquals(form.getEmail(), view.getEmail());
        assertEquals(form.getAddress(), view.getAddress());
    }

    @Test
    void updateTest() {
        testObject.update(new UpdateStudentForm(1, "New Name", "new@email.com", "Somewhere else"));

        StudentView view = testObject.findById(1);
        assertNotNull(view);

        assertEquals(view.getId(), 1);
        assertEquals(view.getName(), "New Name");
        assertEquals(view.getEmail(), "new@email.com");
        assertEquals(view.getAddress(), "Somewhere else");
    }

    @Test
    void findByIdTest() {
        assertNotNull(testObject.findById(1));
    }

    @Test
    void searchByEmailTest() {
        assertNotNull(testObject.searchByEmail("tester@example.org"));
    }

    @Test
    void searchByNameTest() {
        assertNotNull(testObject.searchByName("Test Name"));
    }

    @Test
    void findAllTest() {
        assertEquals(testObject.findAll().size(), 1);
    }

    @Test
    void deleteStudentTest () {
        testObject.deleteStudent(1);
        assertNull(testObject.findById(1));
    }

    @AfterEach
    void tearDown() {
        StudentSequencer.setStudentSequencer(0);
        studentDao.clear();
    }
}
