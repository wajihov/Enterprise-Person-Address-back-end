package com.example.societepersonnel.domain.adresse;

import com.example.societepersonnel.AdressesApiDelegate;
import com.example.societepersonnel.dto.AdresseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdresseController implements AdressesApiDelegate {

    @Override
    public ResponseEntity<AdresseDto> createAdresse(AdresseDto adresseDto) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteAdresse(String id) {
        return null;
    }

    @Override
    public ResponseEntity<AdresseDto> findAdresseById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<List<AdresseDto>> findAdresses() {
        return null;
    }

    @Override
    public ResponseEntity<AdresseDto> updateAdresse(String id, AdresseDto adresseDto) {
        return null;
    }
}
