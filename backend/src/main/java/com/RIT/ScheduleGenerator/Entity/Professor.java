package com.RIT.ScheduleGenerator.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double rating;

    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public Long getID() {
        return id;
    }
}