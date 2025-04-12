package com.RIT.ScheduleGenerator.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import com.RIT.ScheduleGenerator.Entity.Course;

import java.util.ArrayList;
import java.util.List;

public class FileConverter {
    public static void main(String[] args) {
        try {
            String json = CourseScrapper.scraperCourses();
            JSONArray jsonArray = new JSONArray(json);
            List<Course> courses = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Course course = new Course();
                course.setID(obj.getInt("id"));
                course.setName(obj.getString("title"));
                courses.add(course);
            }
            // Print results
            for (Course course : courses) {
                System.out.println("Name: " + course.getName() + ", ID: " + course.getID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}