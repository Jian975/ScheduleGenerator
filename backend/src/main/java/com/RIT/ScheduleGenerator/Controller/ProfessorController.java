package com.RIT.ScheduleGenerator.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.RIT.ScheduleGenerator.DTO.CourseDTO;
import com.RIT.ScheduleGenerator.DTO.MessageDTO;
import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    private List<Professor> professorsTaken;

    private void initializeProfessors() {
        if (professorsTaken == null) {
            professorsTaken = new ArrayList<>();
        }
    }

    private void addNoRepeat(Professor professor) {
        initializeProfessors();
        if (!professorsTaken.contains(professor)) {
            professorsTaken.add(professor);
        }
    }

    @PostMapping(value = "/addProfessor", consumes = "application/json")
    public @ResponseBody MessageDTO addProfessor(@RequestBody CourseDTO courseDTO) {
        professorRepository.findById(courseDTO.id()).ifPresent(this::addNoRepeat);
        return MessageDTO.builder().withSuccess("Professor added to taken list").build();
    }

    @GetMapping(value = "/getAllProfessors")
    public @ResponseBody List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }
}