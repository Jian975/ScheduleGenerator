package com.RIT.ScheduleGenerator.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.util.Pair;

import com.RIT.ScheduleGenerator.Entity.Course;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class CourseScrapper {
    private static String scrapeCoursesJSON() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://schedule.csh.rit.edu/search/find"))
                .POST(BodyPublishers.ofString(
                        "term=20251&college=any&department=any&level=any&credits=&professor=&online=true&honors=true&offCampus=true&title=&description="))
                .setHeader("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:137.0) Gecko/20100101 Firefox/137.0")
                .setHeader("Accept", "application/json, text/plain, */*")
                .setHeader("Accept-Language", "en-US,en;q=0.5")
                .setHeader("Accept-Encoding", "gzip, deflate, br, zstd")
                .setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .setHeader("Origin", "https://schedule.csh.rit.edu")
                .setHeader("DNT", "1")
                .setHeader("Sec-GPC", "1")
                .setHeader("Referer", "https://schedule.csh.rit.edu/search")
                .setHeader("Cookie", "85ea552b460ac987479c356aae9d482f=695210ce2210e7820969370e84b04d8a")
                .setHeader("Sec-Fetch-Dest", "empty")
                .setHeader("Sec-Fetch-Mode", "cors")
                .setHeader("Sec-Fetch-Site", "same-origin")
                .setHeader("Priority", "u=0")
                .build();

        HttpResponse<InputStream> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String output = "";

        // Decompress GZIP response
        try (GZIPInputStream gzipStream = new GZIPInputStream(response.body());
                BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output += line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return output;
    }

    public static Pair<List<Course>, Set<String>> scrapeCourses() {
        List<Course> courses = new ArrayList<>();
        Set<String> professors = new HashSet<>();
        try {
            String json = scrapeCoursesJSON();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Course course = new Course();
                course.setID(obj.getInt("id"));
                course.setName(obj.getString("title"));
                professors.add(obj.getString("instructor"));
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Pair.of(courses, professors);
    }

    public static void main(String[] args) {
        System.out.println(scrapeCoursesJSON());
    }
}
