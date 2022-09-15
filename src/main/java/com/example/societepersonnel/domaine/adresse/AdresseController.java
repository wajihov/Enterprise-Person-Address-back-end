package com.example.societepersonnel.domaine.adresse;

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
    public ResponseEntity<Boolean> deleteAdress(String id) {
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
    public ResponseEntity<AdresseDto> updateAdress(String id, AdresseDto adresseDto) {
        return null;
    }
}
