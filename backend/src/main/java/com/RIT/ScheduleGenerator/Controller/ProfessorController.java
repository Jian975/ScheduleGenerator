package com.RIT.ScheduleGenerator.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.RIT.ScheduleGenerator.DTO.ProfessorDTO;
import com.RIT.ScheduleGenerator.DTO.MessageDTO;
import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;
import com.RIT.ScheduleGenerator.Service.RateMyProfessorScraper;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private RateMyProfessorScraper rateMyProfessorScraper;

    private List<Professor> professors;

    private void initializeProfessors() {
        if (professors == null) {
            professors = new ArrayList<>();
        }
    }

    private void addNoRepeat(Professor professor) {
        initializeProfessors();
        if (!professors.contains(professor)) {
            professors.add(professor);
        }
    }

    // Add Professor with RMP rating based on ProfessorDTO
    @PostMapping(value = "/addProfessor", consumes = "application/json")
    public @ResponseBody MessageDTO addProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            String professorName = professorDTO.name();

            // Scrape professor information from RateMyProfessors based on the name
            Professor professor = rateMyProfessorScraper.scrapeProfessorByName(professorName);
            professor.setID(professorDTO.id()); // Set the ID from the incoming ProfessorDTO

            // Convert the rating from float to double (if needed)
            professor.setRating((double) professorDTO.rating()); // Convert float to double

            addNoRepeat(professor);  // Prevent duplicates before saving

            // Save the professor to the repository
            professorRepository.save(professor);

            return MessageDTO.builder()
                .withSuccess("Professor " + professorName + " added with RMP rating: " + professor.getRating())
                .build();

        } catch (Exception e) {
            return MessageDTO.builder()
                .withFailure("Error adding professor: " + e.getMessage())
                .build();
        }
    }

    // Fetch all professors from the repository
    @GetMapping(value = "/getAllProfessors")
    public @ResponseBody List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }
}