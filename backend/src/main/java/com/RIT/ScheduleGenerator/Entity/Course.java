package com.RIT.ScheduleGenerator.Entity;

import java.util.List;

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
    private List<Course> prerequisites;

    public Long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> prerequisites() {
        return prerequisites;
    }
}