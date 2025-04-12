package com.RIT.ScheduleGenerator.Repository;

import com.RIT.ScheduleGenerator.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
