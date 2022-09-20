package com.example.societepersonnel.domain.personnel;

import com.example.societepersonnel.PersonsApiDelegate;
import com.example.societepersonnel.dto.PersonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonnelController implements PersonsApiDelegate {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @Override
    public ResponseEntity<PersonDto> createPerson(PersonDto personDto) {
        PersonDto dto = personnelService.createPerson(personDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePerson(Long id) {
        personnelService.deleteEntreprise(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PersonDto>> findAllPersons() {
        List<PersonDto> dtos = personnelService.findPersons();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PersonDto> findPersonById(Long id) {
        PersonDto personDto = personnelService.findPersonById(id);
        return ResponseEntity.ok(personDto);
    }

    @Override
    public ResponseEntity<PersonDto> updatePerson(Long id, PersonDto personDto) {
        PersonDto dto = personnelService.updatePerson(id, personDto);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
}
