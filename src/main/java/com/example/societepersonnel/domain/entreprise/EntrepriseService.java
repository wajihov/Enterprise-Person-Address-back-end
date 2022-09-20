package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.core.exception.EntreprisePersonnelException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.domain.adresse.AdresseMapper;
import com.example.societepersonnel.domain.adresse.AdresseService;
import com.example.societepersonnel.dto.AdresseDto;
import com.example.societepersonnel.dto.EntrepriseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EntrepriseService {

    private final EntrepriseMapper entrepriseMapper;
    private final EntrepriseRepository entrepriseRepository;
    private final AdresseMapper adresseMapper;
    private final AdresseService adresseService;

    public EntrepriseService(EntrepriseMapper entrepriseMapper, EntrepriseRepository entrepriseRepository, AdresseMapper adresseMapper, AdresseService adresseService) {
        this.entrepriseMapper = entrepriseMapper;
        this.entrepriseRepository = entrepriseRepository;
        this.adresseMapper = adresseMapper;
        this.adresseService = adresseService;
    }

    public EntrepriseDto createEntreprise(EntrepriseDto entrepriseDto) {
        log.info("l'ajout de l'entreprise {}", entrepriseDto.getName());
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDto);
        Long id_adresse=entrepriseDto.getAdresseId();
        AdresseDto adresseDto= adresseService.findAdresseById(id_adresse);
        Adresse adresse= adresseMapper.toEntity(adresseDto);
        entreprise.setAdresse(adresse);
        entreprise = entrepriseRepository.save(entreprise);
        return entrepriseMapper.toDto(entreprise);
    }

    public List<EntrepriseDto> findAllEntreprise() {
        List<Entreprise> enterprises = entrepriseRepository.findAll();
        log.info("recherche de {} entreprises ", enterprises.size());
        return entrepriseMapper.toDtos(enterprises);
    }

    private Entreprise searchEntrepriseById(Long id) {
        return entrepriseRepository.findById(id).orElseThrow(()
                -> new EntreprisePersonnelException(Codes.ERR_ENTREPRESE_NOT_FOUND));

    }

    public EntrepriseDto findEntrepriseById(Long id) {
        log.info("L'entreprise rechercher avec l'id : {}", id);
        return entrepriseMapper.toDto(searchEntrepriseById(id));
    }

    public EntrepriseDto updateEntreprise(Long id, EntrepriseDto entrepriseDto) {
        searchEntrepriseById(id);
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDto);
        entreprise.setId(id);
        entreprise = entrepriseRepository.save(entreprise);
        log.info("L'entreprise est modifié avec succès {}", entrepriseDto.getName());
        return entrepriseMapper.toDto(entreprise);
    }

    public void deleteEntreprise(Long id) {
        searchEntrepriseById(id);
        log.info("L'entreprise est supprimé avec succès avec l'id {}", id);
        entrepriseRepository.deleteById(id);
    }

}
