package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.EntreprisesApiDelegate;
import com.example.societepersonnel.dto.EntrepriseDto;
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
        return EntreprisesApiDelegate.super.createEnterprise(entrepriseDto);
    }

    @Override
    public ResponseEntity<Boolean> deleteEnterprise(Long id) {
        return EntreprisesApiDelegate.super.deleteEnterprise(id);
    }

    @Override
    public ResponseEntity<EntrepriseDto> findEnterpriseById(Long id) {
        return EntreprisesApiDelegate.super.findEnterpriseById(id);
    }

    @Override
    public ResponseEntity<List<EntrepriseDto>> findEnterprises() {
        return EntreprisesApiDelegate.super.findEnterprises();
    }

    @Override
    public ResponseEntity<EntrepriseDto> updateEnterprise(Long id, EntrepriseDto entrepriseDto) {
        return EntreprisesApiDelegate.super.updateEnterprise(id, entrepriseDto);
    }
}
