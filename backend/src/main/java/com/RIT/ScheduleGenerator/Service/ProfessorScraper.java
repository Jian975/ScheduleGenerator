package com.RIT.ScheduleGenerator.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfessorScraper {

    public static Set<String> getProfessorNames() {
        Set<String> instructors = new HashSet<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://schedule.csh.rit.edu/search/find"))
                .POST(BodyPublishers.ofString("term=20251&college=any&department=any&level=any&credits=&professor=&online=true&honors=true&offCampus=true&title=&description="))
                .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:137.0) Gecko/20100101 Firefox/137.0")
                .setHeader("Accept", "application/json, text/plain, */*")
                .setHeader("Accept-Encoding", "gzip")
                .setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();

        HttpResponse<InputStream> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return instructors;
        }

        StringBuilder jsonString = new StringBuilder();

        try (GZIPInputStream gzipStream = new GZIPInputStream(response.body());
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray courses = new JSONArray(jsonString.toString());

            for (int i = 0; i < courses.length(); i++) {
                JSONObject course = courses.getJSONObject(i);
                if (course.has("instructor")) {
                    JSONArray profArray = course.getJSONArray("instructor");
                    for (int j = 0; j < profArray.length(); j++) {
                        String name = profArray.getString(j).trim();
                        if (!name.isEmpty()) {
                            instructors.add(name);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return instructors;
    }

    public static void main(String[] args) {
        Set<String> professorList = getProfessorNames();
        System.out.println("=== Professors Found ===");
        for (String name : professorList) {
            System.out.println(name);
        }
    }
}
