package com.example.societepersonnel.domain.person;

import com.example.societepersonnel.PersonsApiDelegate;
import com.example.societepersonnel.dto.PersonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController implements PersonsApiDelegate {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public ResponseEntity<PersonDto> createPerson(PersonDto personDto) {
        PersonDto dto = personService.createPerson(personDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePerson(Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PersonDto>> findAllPersons() {
        List<PersonDto> personDtoList = personService.findPersons();
        return ResponseEntity.ok(personDtoList);
    }

    @Override
    public ResponseEntity<PersonDto> findPersonById(Long id) {
        PersonDto dto = personService.findPersonById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<PersonDto> updatePerson(Long id, PersonDto personDto) {
        PersonDto dto = personService.updatePerson
                (id, personDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
