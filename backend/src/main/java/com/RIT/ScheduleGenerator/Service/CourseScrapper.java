package com.RIT.ScheduleGenerator.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.zip.GZIPInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class CourseScrapper {
    public static void scraperCourses() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://schedule.csh.rit.edu/search/find"))
                .POST(BodyPublishers.ofString("term=20251&college=any&department=any&level=any&credits=&professor=&online=true&honors=true&offCampus=true&title=&description="))
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Decompress GZIP response
        try (GZIPInputStream gzipStream = new GZIPInputStream(response.body());
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
}

    public static void main(String[] args) {
        scraperCourses();
    }
}
