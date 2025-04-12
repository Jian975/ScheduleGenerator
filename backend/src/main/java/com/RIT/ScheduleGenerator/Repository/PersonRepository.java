package com.RIT.ScheduleGenerator.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.RIT.ScheduleGenerator.Entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    
}
