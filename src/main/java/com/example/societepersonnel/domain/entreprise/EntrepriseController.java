package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.EntreprisesApiDelegate;
import com.example.societepersonnel.dto.EntrepriseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EntreprisesApiDelegate {

    @Override
    public ResponseEntity<EntrepriseDto> createEnterprise(EntrepriseDto entrepriseDto) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteEnterprise(String id) {
        return null;
    }

    @Override
    public ResponseEntity<EntrepriseDto> findEnterpriseById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<List<EntrepriseDto>> findEnterprises() {
        return null;
    }

    @Override
    public ResponseEntity<EntrepriseDto> updateEnterprise(String id, EntrepriseDto entrepriseDto) {
        return null;
    }
}
