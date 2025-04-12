package com.RIT.ScheduleGenerator.Service;

import com.RIT.ScheduleGenerator.Entity.Professor;
import com.RIT.ScheduleGenerator.Repository.ProfessorRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.IOException;

@Service
public class RateMyProfessorScraper {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor scrapeProfessor(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Element scriptTag = doc.selectFirst("script[type=application/ld+json]");
        if (scriptTag != null) {
            String json = scriptTag.html();
            JSONObject obj = new JSONObject(json);

            String name = obj.getString("name");

            double rating = 0.0;
            if (obj.has("aggregateRating")) {
                JSONObject ratingObj = obj.getJSONObject("aggregateRating");
                rating = ratingObj.getDouble("ratingValue");
            }

            Professor professor = new Professor();
            professor.setName(name);
            professor.setRating(rating);

            return professorRepository.save(professor);
        } else {
            throw new IOException("No JSON-LD data found on page");
        }
    }
}