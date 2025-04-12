package com.RIT.ScheduleGenerator.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RITProfessorScraper {

    public List<String> getAllRITProfessorIds() throws IOException {
        List<String> professorIds = new ArrayList<>();
        
        String url = "https://www.ratemyprofessors.com/search/professors?q=Rochester+Institute+of+Technology";
        Document doc = Jsoup.connect(url).get();

        // Find the links that contain the professor ID (you may need to adjust the selector depending on the structure)
        Elements links = doc.select("a.professor-name-link");

        for (Element link : links) {
            String professorUrl = link.attr("href");
            // The professor ID is in the URL, e.g., "https://www.ratemyprofessors.com/professor/306975"
            String professorId = professorUrl.split("/")[2];
            professorIds.add(professorId);
        }

        return professorIds;
    }
}