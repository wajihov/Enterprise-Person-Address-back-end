package com.example.societepersonnel.domain.adresse;

import com.example.societepersonnel.AdressesApiDelegate;
import com.example.societepersonnel.dto.AdresseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdresseController implements AdressesApiDelegate {

    private final AdresseService adresseService;
    private final AdresseMapper adresseMapper;

    public AdresseController(AdresseService adresseService, AdresseMapper adresseMapper) {
        this.adresseService = adresseService;
        this.adresseMapper = adresseMapper;
    }

    @Override
    public ResponseEntity<AdresseDto> createAdresse(AdresseDto adresseDto) {
        AdresseDto dto = adresseService.createAdresse(adresseDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AdresseDto>> findAdresses() {
        List<AdresseDto> dtoList = adresseService.listAdresseDto();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AdresseDto> findAdresseById(Long id) {
        AdresseDto adresseDto = adresseService.findAdresseById(id);
        return new ResponseEntity<>(adresseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> deleteAdresse(Long id) {
        boolean dtoDelete = adresseService.deleteAdresse(id);
        return ResponseEntity.ok(dtoDelete);
    }

    @Override
    public ResponseEntity<AdresseDto> updateAdresse(Long id, AdresseDto adresseDto) {
        AdresseDto dto = adresseService.modifyAdresse(id, adresseDto);
        return ResponseEntity.ok(dto);
    }
}
