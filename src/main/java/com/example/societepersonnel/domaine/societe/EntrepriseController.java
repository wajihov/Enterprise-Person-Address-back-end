package com.example.societepersonnel.domaine.societe;

import com.example.societepersonnel.EnterprisesApiDelegate;
import com.example.societepersonnel.dto.EnterpriseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EnterprisesApiDelegate {

    @Override
    public ResponseEntity<EnterpriseDto> createEnterprise(EnterpriseDto enterpriseDto) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deleteEnterprise(String id) {
        return null;
    }

    @Override
    public ResponseEntity<EnterpriseDto> findEnterpriseById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<List<EnterpriseDto>> findEnterprises() {
        return null;
    }

    @Override
    public ResponseEntity<EnterpriseDto> updateEnterprise(String id, EnterpriseDto enterpriseDto) {
        return null;
    }
}
