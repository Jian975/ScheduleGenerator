package com.RIT.ScheduleGenerator.Controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

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

    @PostConstruct
    public void autoInitializeProfessorsWithRatings() {
        List<Professor> existing = professorRepository.findAll();

        for (Professor prof : existing) {
            try {
                Professor scraped = rateMyProfessorScraper.scrapeProfessorByName(prof.getName());
                prof.setRating(scraped.getRating());
                professorRepository.save(prof);
                System.out.println("✅ Updated " + prof.getName() + " with rating: " + prof.getRating());
            } catch (Exception e) {
                System.err.println("⚠️ Failed to scrape " + prof.getName() + ": " + e.getMessage());
            }
        }

        System.out.println("🎉 All professors initialized with RMP ratings.");
    }

    @PostMapping(value = "/addProfessor", consumes = "application/json")
    public @ResponseBody MessageDTO addProfessor(@RequestBody ProfessorDTO professorDTO) {
        try {
            String professorName = professorDTO.name();

            Professor professor = rateMyProfessorScraper.scrapeProfessorByName(professorName);
            professor.setID(professorDTO.id());
            professor.setRating((double) professorDTO.rating());

            addNoRepeat(professor);
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

    @GetMapping(value = "/getAllProfessors")
    public @ResponseBody List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }
}