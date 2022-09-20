package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.EntreprisesApiDelegate;
import com.example.societepersonnel.dto.EntrepriseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EntreprisesApiDelegate {

    private final EntrepriseService entrepriseService;
    private final EntrepriseMapper entrepriseMapper;

    public EntrepriseController(EntrepriseService entrepriseService, EntrepriseMapper entrepriseMapper) {
        this.entrepriseService = entrepriseService;
        this.entrepriseMapper = entrepriseMapper;
    }

    @Override
    public ResponseEntity<EntrepriseDto> createEnterprise(EntrepriseDto entrepriseDto) {
        EntrepriseDto dto = entrepriseService.createEntreprise(entrepriseDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteEnterprise(Long id) {
        entrepriseService.deleteEntreprise(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntrepriseDto> findEnterpriseById(Long id) {
        EntrepriseDto dto = entrepriseService.findEntrepriseById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EntrepriseDto>> findEnterprises() {
        List<EntrepriseDto> entreprises = entrepriseService.findAllEntreprise();
        return ResponseEntity.ok(entreprises);
    }

    @Override
    public ResponseEntity<EntrepriseDto> updateEnterprise(Long id, EntrepriseDto entrepriseDto) {
        EntrepriseDto dto = entrepriseService.updateEntreprise(id, entrepriseDto);
        return ResponseEntity.ok(dto);
    }
}
