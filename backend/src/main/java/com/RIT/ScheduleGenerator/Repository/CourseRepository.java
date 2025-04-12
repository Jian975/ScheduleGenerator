package com.RIT.ScheduleGenerator.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RIT.ScheduleGenerator.Entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
}
