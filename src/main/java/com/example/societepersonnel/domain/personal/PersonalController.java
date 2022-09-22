package com.example.societepersonnel.domain.personal;

import com.example.societepersonnel.PersonalsApiDelegate;
import com.example.societepersonnel.dto.PersonalDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonalController implements PersonalsApiDelegate {

    private final PersonalService personalService;

    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }

    @Override
    public ResponseEntity<PersonalDto> createPersonal(PersonalDto personDto) {
        PersonalDto dto = personalService.createPersonal(personDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePersonal(Long id) {
        personalService.deleteEnterprise(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PersonalDto>> findPersonals() {
        List<PersonalDto> personalDtos = personalService.findPersonals();
        return ResponseEntity.ok(personalDtos);
    }

    @Override
    public ResponseEntity<PersonalDto> findPersonalById(Long id) {
        PersonalDto dto = personalService.findPersonById(id);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<PersonalDto> updatePersonal(Long id, PersonalDto personalDto) {
        PersonalDto dto = personalService.updatePersonal(id, personalDto);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
}
