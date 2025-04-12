package com.RIT.ScheduleGenerator.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.RIT.ScheduleGenerator.DTO.CourseDTO;
import com.RIT.ScheduleGenerator.DTO.MessageDTO;
import com.RIT.ScheduleGenerator.Entity.Course;
import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.CourseRepository;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;
import com.RIT.ScheduleGenerator.Service.CourseScrapper;

@Controller
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    private List<Course> coursesTaken;
    

    private void initializeCoursesTaken() {
        if (coursesTaken == null) {
            coursesTaken = new ArrayList<>();
        }
    }

    @GetMapping(value = "/initializeCourses")
    public @ResponseBody MessageDTO initializeCourses() {
        Pair<List<Course>, Set<String>> pair = CourseScrapper.scrapeCourses();
        List<Course> courses = pair.getFirst();
        pair.getSecond().stream().forEach(professorName -> {
            Professor professor = new Professor();
            professor.setName(professorName);
            professorRepository.save(professor);
        });;
        courses.stream().forEach(courseRepository::save);
        return MessageDTO.builder().withSuccess("Success").build();
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
