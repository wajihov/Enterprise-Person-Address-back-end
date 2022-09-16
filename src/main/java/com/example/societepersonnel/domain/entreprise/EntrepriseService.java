package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.dto.EntrepriseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EntrepriseService {

    private final EntrepriseMapper entrepriseMapper;
    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService(EntrepriseMapper entrepriseMapper, EntrepriseRepository entrepriseRepository) {
        this.entrepriseMapper = entrepriseMapper;
        this.entrepriseRepository = entrepriseRepository;
    }

    public EntrepriseDto createEntreprise(EntrepriseDto entrepriseDto, Adresse adresse) {
        log.info("l'ajout de l'entreprise {}", entrepriseDto.getName());

        return null;
    }
}
