package com.RIT.ScheduleGenerator.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.RIT.ScheduleGenerator.DTO.MessageDTO;
// import com.RIT.ScheduleGenerator.DTO.PersonDTO;
import com.RIT.ScheduleGenerator.Entity.Person;
import com.RIT.ScheduleGenerator.Repository.PersonRepository;

@Controller
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    // @GetMapping(value = "/getPersons")
    // public @ResponseBody List<PersonDTO> getPersons() {
    //         return personRepository.findAll().stream().map(person -> PersonDTO.builder().
    //             withAge(person.getAge())
    //             .withId(person.getId())
    //             .withName(person.getName())
    //             .build()).toList();
    //     }

    // @PostMapping(value = "/createPerson", consumes = "application/json")
    // public @ResponseBody MessageDTO createPerson(@RequestBody PersonDTO personDTO) {
    //     Person person = new Person();
    //     person.setAge(personDTO.age());
    //     person.setName(personDTO.name());
    //     personRepository.save(person);
    //     return MessageDTO.builder().withSuccess("Success").build();
    // }
}
