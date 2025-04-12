package com.RIT.ScheduleGenerator.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.RIT.ScheduleGenerator.DTO.CourseDTO;
import com.RIT.ScheduleGenerator.DTO.MessageDTO;
import com.RIT.ScheduleGenerator.Entity.Course;
import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.CourseRepository;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;
import com.RIT.ScheduleGenerator.Service.CourseScrapper;
import com.RIT.ScheduleGenerator.Service.RateMyProfessorScraper;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private RateMyProfessorScraper rateMyProfessorScraper;

    private List<Course> coursesTaken;

    private void initializeCoursesTaken() {
        if (coursesTaken == null) {
            coursesTaken = new ArrayList<>();
        }
    }

    @PostConstruct
    public void autoInitializeCoursesAndProfessors() {
        Pair<List<Course>, Set<String>> pair = CourseScrapper.scrapeCourses();
        List<Course> courses = pair.getFirst();
        Set<String> professorNames = pair.getSecond();

        for (String professorName : professorNames) {
            Professor professor;
            try {
                professor = rateMyProfessorScraper.scrapeProfessorByName(professorName);
            } catch (Exception e) {
                professor = new Professor();
                professor.setName(professorName);
                professor.setRating(0.0); // Default if not found
            }
            professorRepository.save(professor);
        }

        courses.forEach(courseRepository::save);

        System.out.println("✅ Courses and professors initialized at startup.");
    }

    private void addNoRepeat(Course course) {
        initializeCoursesTaken();
        if (!coursesTaken.contains(course)) {
            coursesTaken.add(course);
        }
    }

    @PostMapping(value = "/takeCourse", consumes = "application/json")
    public @ResponseBody MessageDTO takeCourse(@RequestBody CourseDTO courseDTO) {
        initializeCoursesTaken();
        courseRepository.findById(courseDTO.id()).ifPresent(this::addNoRepeat);
        return MessageDTO.builder().withSuccess("Course added to taken list").build();
    }

    @GetMapping(value = "/getAllValidCourses")
    public @ResponseBody List<CourseDTO> getAllValidCourses() {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.meetsPrerequisites(coursesTaken))
                .map(CourseDTO::from)
                .toList();
    }
}