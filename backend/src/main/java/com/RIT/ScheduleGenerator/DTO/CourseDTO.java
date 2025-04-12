package com.RIT.ScheduleGenerator.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CourseDTO(String name,
        String listing,
        Long id,
        List<CourseDTO> prereqs) {

    public boolean meetsPrerequisites(List<CourseDTO> coursesTaken) {
        return coursesTaken.containsAll(prereqs);
    }
}