package com.RIT.ScheduleGenerator.Service;

import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RateMyProfessorScraper {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor scrapeProfessor(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        String name = doc.selectFirst("div.NameTitle__Name-dowf0z-0.cfjPUG").text(); // might need tweaking
        String ratingStr = doc.selectFirst("div.RatingValue__Numerator-qw8sqy-2.gxuTRq").text(); // might need tweaking

        double rating = Double.parseDouble(ratingStr);

        Professor professor = new Professor();
        professor.setName(name);
        professor.setRating(rating);

        return professorRepository.save(professor);
    }
}