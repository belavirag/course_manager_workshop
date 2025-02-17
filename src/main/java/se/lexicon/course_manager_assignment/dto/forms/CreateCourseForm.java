package se.lexicon.course_manager_assignment.dto.forms;



import org.springframework.format.annotation.DateTimeFormat;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.messages.ValidationMessages;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreateCourseForm implements Serializable {

    @Null
    private Integer id;

    @NotBlank(message = ValidationMessages.IS_REQUIRED)
    @Size(min = 2, max = 100, message = ValidationMessages.BETWEEN_5_AND_100_LETTERS)
    private String courseName;

    @NotNull(message = ValidationMessages.IS_REQUIRED)
    @FutureOrPresent(message = ValidationMessages.IN_THE_PRESENT_OR_FUTURE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = ValidationMessages.IS_REQUIRED)
    @Min(value = 1, message = ValidationMessages.AT_LEAST_ONE_WEEK)
    private Integer weekDuration;

    public CreateCourseForm(Integer id, String courseName, LocalDate startDate, Integer weekDuration) {
        this.id = id;
        this.courseName = courseName;
        this.startDate = startDate;
        this.weekDuration = weekDuration;
    }

    public CreateCourseForm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getWeekDuration() {
        return weekDuration;
    }

    public void setWeekDuration(Integer weekDuration) {
        this.weekDuration = weekDuration;
    }

    public CourseView intoView() {
        // int id, String courseName, LocalDate startDate, int weekDuration, List<StudentView> students) {
        return new CourseView(id, courseName, startDate, weekDuration, new ArrayList<>());
    }
}
