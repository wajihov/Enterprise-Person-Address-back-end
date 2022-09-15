package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.domain.adresse.AdresseRepository;
import com.example.societepersonnel.dto.EntrepriseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EntrepriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final EntrepriseMapper entrepriseMapper;
    private final AdresseRepository adresseRepository;

    public EntrepriseService(EnterpriseRepository enterpriseRepository, EntrepriseMapper entrepriseMapper, AdresseRepository adresseRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.entrepriseMapper = entrepriseMapper;
        this.adresseRepository = adresseRepository;
    }

    public EntrepriseDto create(EntrepriseDto enterpriseDto) {
        log.info("adding entreprise {}", enterpriseDto.getName());
        Adresse adresse = adresseRepository.findById(enterpriseDto.getId()).get();//TODO
        Enterprise enterprise = entrepriseMapper.toEntity(enterpriseDto, adresse);
        return entrepriseMapper.toDto(enterprise);
    }
}
