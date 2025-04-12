package com.RIT.ScheduleGenerator.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RIT.ScheduleGenerator.Entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
