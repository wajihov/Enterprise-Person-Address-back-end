package com.example.societepersonnel.domain.personnel;

import com.example.societepersonnel.PersonsApiDelegate;
import com.example.societepersonnel.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonnelController implements PersonsApiDelegate {

    @Override
    public ResponseEntity<PersonDto> createPerson(PersonDto personDto) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deletePerson(String id) {
        return null;
    }

    @Override
    public ResponseEntity<List<PersonDto>> findAllPersons() {
        return null;
    }

    @Override
    public ResponseEntity<PersonDto> findPersonById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<PersonDto> updatePerson(String id, PersonDto personDto) {
        return null;
    }
}
