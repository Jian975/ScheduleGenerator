package com.RIT.ScheduleGenerator.Controller;

import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;
import com.RIT.ScheduleGenerator.Service.RITProfessorScraper;
import com.RIT.ScheduleGenerator.Service.RateMyProfessorScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfessorController {

    @Autowired
    private RateMyProfessorScraper rateMyProfessorScraper; // For scraping individual professors
    @Autowired
    private ProfessorRepository professorRepository;       // For saving professors to the database
    @Autowired
    private RITProfessorScraper ritProfessorScraper;       // For scraping all professor IDs for RIT

    @GetMapping("/scrapeAllProfessors")
    @ResponseBody
    public List<Professor> scrapeAndSaveAllRITProfessors() {
        List<String> professorIds = new ArrayList<>();
        List<Professor> savedProfessors = new ArrayList<>();
        
        try {
            // Get the list of professor IDs
            professorIds = ritProfessorScraper.getAllRITProfessorIds();
            
            // Loop through each professor ID and scrape/save their data
            for (String professorId : professorIds) {
                try {
                    String url = "https://www.ratemyprofessors.com/professor/" + professorId;
                    Professor professor = rateMyProfessorScraper.scrapeProfessor(url);

                    // Save the scraped professor to the database
                    savedProfessors.add(professorRepository.save(professor));
                } catch (Exception e) {
                    System.out.println("Failed to scrape or save professor with ID: " + professorId);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedProfessors;
    }
}