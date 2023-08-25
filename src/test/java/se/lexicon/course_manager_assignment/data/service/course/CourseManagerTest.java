package se.lexicon.course_manager_assignment.data.service.course;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseManager.class, CourseCollectionRepository.class, ModelToDto.class, StudentCollectionRepository.class})
public class CourseManagerTest {

    @Autowired
    private CourseService testObject;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(studentDao);
    }

    //Write your tests here

    CreateCourseForm getExampleCourseForm() {
        return new CreateCourseForm(1, "Test Course", LocalDate.now(), 12);
    }

    @BeforeEach
    void setup() {
        testObject.create(getExampleCourseForm());
    }

    @Test
    void createTest() {
        CreateCourseForm form = getExampleCourseForm();
        assertEquals(testObject.findAll().size(), 1);

        CourseView view = testObject.findAll().get(0);
        assertNotNull(view);

        assertEquals(view.getId(), form.getId());
        assertEquals(view.getCourseName(), form.getCourseName());
        assertEquals(view.getStartDate(), form.getStartDate());
        assertEquals(view.getWeekDuration(), form.getWeekDuration());
    }

    @Test
    void updateTest() {
        LocalDate testDate = LocalDate.now().plusDays(1);
        UpdateCourseForm form = new UpdateCourseForm(1, "New Course Name", testDate, 4);

        testObject.update(form);
        CourseView view = testObject.findById(1);
        assertNotNull(view);

        assertEquals(view.getId(), form.getId());
        assertEquals(view.getCourseName(), form.getCourseName());
        assertEquals(view.getStartDate(), form.getStartDate());
        assertEquals(view.getWeekDuration(), form.getWeekDuration());
    }

    @Test
    void searchByCourseNameTest() {
        assertEquals(testObject.searchByCourseName("Test Course").size(), 1);
    }

    @Test
    void searchByDateAfterTest() {
        assertEquals(testObject.searchByDateAfter(LocalDate.now().minusDays(1)).size(), 1);
    }

    @Test
    void searchByDateBeforeTest() {
        assertEquals(testObject.searchByDateBefore(LocalDate.now().plusDays(1)).size(), 1);
    }

    @Test
    void addStudentToCourseTest() {
        studentDao.createStudent("Test Student", "hi@example.org", "Test Road");
        Student student = studentDao.findByNameContains("Test").stream().findFirst().orElse(null);
        assertNotNull(student);

        testObject.addStudentToCourse(1, student.getId());
        List<CourseView> courses = testObject.findByStudentId(student.getId());
        assertEquals(courses.size(), 1);
    }

    @Test
    void removeStudentFromCourseTest() {

        studentDao.createStudent("Test Student", "hi@example.org", "Test Road");
        testObject.addStudentToCourse(1, 1);
        assertEquals(testObject.findById(1).getStudents().size(), 1);
        testObject.removeStudentFromCourse(1, 1);
        assertEquals(testObject.findById(1).getStudents().size(), 0);
    }

    @Test
    void findAllTest() {
        assertEquals(testObject.findAll().size(), 1);
    }

    @Test
    void findByStudentIdTest() {
        studentDao.createStudent("Test Student", "hi@example.org", "Test Road");
        testObject.addStudentToCourse(1, 1);

        assertEquals(testObject.findByStudentId(1).size(), 1);
    }

    @Test
    void deleteCourseTest() {
        testObject.deleteCourse(1);
        assertEquals(testObject.findAll().size(), 0);
    }

    @AfterEach
    void tearDown() {
        studentDao.clear();
        courseDao.clear();
        StudentSequencer.setStudentSequencer(0);
        CourseSequencer.setCourseSequencer(0);
    }
}
