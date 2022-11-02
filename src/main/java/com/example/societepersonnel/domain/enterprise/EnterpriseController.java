package com.example.societepersonnel.domain.enterprise;


import com.example.societepersonnel.EnterprisesApiDelegate;
import com.example.societepersonnel.dto.EnterpriseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class EnterpriseController implements EnterprisesApiDelegate {

    private final EnterpriseService enterpriseService;


    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @Override
    public ResponseEntity<EnterpriseDto> createEnterprise(EnterpriseDto enterpriseDto) {
        EnterpriseDto dto = enterpriseService.createEnterprise(enterpriseDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteEnterprise(Long id) {
        enterpriseService.deleteEnterprise(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EnterpriseDto> findEnterpriseById(Long id) {
        EnterpriseDto dto = enterpriseService.findEnterpriseById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EnterpriseDto>> findEnterprises() {
        List<EnterpriseDto> dtoList = enterpriseService.findEnterprises();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EnterpriseDto> updateEnterprise(Long id, EnterpriseDto enterpriseDto) {
        EnterpriseDto dto = enterpriseService.updateEnterprise(id, enterpriseDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
