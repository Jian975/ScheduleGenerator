package com.RIT.ScheduleGenerator.Controller;

import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;
import com.RIT.ScheduleGenerator.Service.RateMyProfessorScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfessorController {

    @Autowired
    private RateMyProfessorScraper rateMyProfessorScraper;

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/scrapeProfessor")
    @ResponseBody
    public Professor scrapeAndSaveProfessor() {
        try {
            String url = "https://www.ratemyprofessors.com/professor/306975";
            Professor professor = rateMyProfessorScraper.scrapeProfessor(url);

            // Save the scraped professor to the database
            return professorRepository.save(professor);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}