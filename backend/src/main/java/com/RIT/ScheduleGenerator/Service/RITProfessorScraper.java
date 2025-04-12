package com.RIT.ScheduleGenerator.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RITProfessorScraper {

    public static List<String> getAllRITProfessorIds() throws IOException {
        List<String> professorIds = new ArrayList<>();

        // Load local HTML file
        File input = new File("path/to/your/page source.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        // Updated selector to match hrefs containing "/professor/"
        Elements links = doc.select("a[href^=\"/professor/\"]");

        for (Element link : links) {
            String professorUrl = link.attr("href");
            String[] parts = professorUrl.split("/");
            if (parts.length >= 3) {
                professorIds.add(parts[2]); // Extract ID from /professor/{id}
            }
        }

        System.out.println(professorIds); // Print the list of professor IDs
        return professorIds;
    }

    public static void main(String[] args) throws IOException {
        getAllRITProfessorIds();
    }
}