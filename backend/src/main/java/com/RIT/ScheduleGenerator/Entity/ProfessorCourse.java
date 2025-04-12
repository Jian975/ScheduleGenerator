package com.RIT.ScheduleGenerator.Entity;

import jakarta.persistence.Entity;

@Entity
public class ProfessorCourse {
    private Long courseID;
    private Long professorID;

    public long getCourseID() {
        return courseID;
    }

    public Long getProfessorID() {
       return professorID;
    }
}
