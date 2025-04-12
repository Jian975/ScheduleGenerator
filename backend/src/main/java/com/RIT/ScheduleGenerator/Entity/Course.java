package com.RIT.ScheduleGenerator.Entity;

import java.util.ArrayList;
import java.util.List;

import com.RIT.ScheduleGenerator.DTO.CourseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private ArrayList<String> days = new ArrayList<String>();
    private List<Course> prerequisites;

    public Long getID() {
        return id;
    }

    public void setID(long id)
    {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays()
    {
        String output = "";

        for (int i = 0; i < days.size(); i++)
        {
            output += days.get(i);

            if (i < days.size() - 1)
                output += ", ";
        }

        return output;
    }

    public void setDays(ArrayList<String> days)
    {
        this.days = days;
    }

    public List<Course> prerequisites() {
        return prerequisites;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course other) {
            return id.equals(other.id);
        }
        return false;
    }

    public boolean meetsPrerequisites(List<Course> coursesTaken) {
        return coursesTaken.containsAll(prerequisites);
    }
}