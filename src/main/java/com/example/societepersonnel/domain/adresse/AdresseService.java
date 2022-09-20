package com.example.societepersonnel.domain.adresse;

import com.example.societepersonnel.core.exception.EntreprisePersonnelException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.StringUtils;
import com.example.societepersonnel.dto.AdresseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AdresseService {

    private final AdresseRepository adresseRepository;
    private final AdresseMapper adresseMapper;

    public AdresseService(AdresseRepository adresseRepository, AdresseMapper adresseMapper) {
        this.adresseRepository = adresseRepository;
        this.adresseMapper = adresseMapper;
    }

    public AdresseDto createAdresse(AdresseDto adresseDto) {
        log.info("l'adresse est ajouté avec succès {}", adresseDto.getAdresse());
        Adresse adresse = adresseMapper.toEntity(adresseDto);
        adresse = adresseRepository.save(adresse);
        return adresseMapper.toDto(adresse);
    }

    public AdresseDto findAdresseById(Long id) {
        Adresse adresse = searchAdresseById(id);
        log.info("l'adresse est la suivante {}", adresse.getAdresse());
        AdresseDto adresseDto = adresseMapper.toDto(adresse);
        return adresseDto;
    }

    public List<AdresseDto> listAdresseDto() {
        List<Adresse> adresses = adresseRepository.findAll();
        log.info("Recherche {} adresses ", adresses.size());
        return adresseMapper.toDtos(adresses);
    }

    public void deleteAdresse(Long id) {
        if (searchAdresseById(id) != null) ;
        {
            log.info("La suppression de l'adresse numero {}", id);
            adresseRepository.deleteById(id);
        }
    }

    private Adresse searchAdresseById(Long id) {
        if (id == null) {
            return null;
        }
        return adresseRepository.findById(id).orElseThrow(() ->
                new EntreprisePersonnelException(Codes.ERR_ADRESS_NOT_FOUND));
    }

    public AdresseDto modifyAdresse(Long id, AdresseDto adresseDto) {
        log.info("la modification commence {}, {} ", adresseDto.getAdresse(), id);
        Adresse adresseUpdate = adresseMapper.toEntity(adresseDto);
        Adresse adresseCurrent = searchAdresseById(id);
        if (!StringUtils.isNullOrEmpty(adresseUpdate.getAdresse())) {
            adresseCurrent.setAdresse(adresseUpdate.getAdresse());
        }
        if (!StringUtils.isNullOrEmpty(adresseUpdate.getCodePostal())) {
            adresseCurrent.setCodePostal(adresseUpdate.getCodePostal());
        }
        if (!StringUtils.isNullOrEmpty(adresseUpdate.getPays())) {
            adresseCurrent.setPays(adresseUpdate.getPays());
        }
        if (!StringUtils.isNullOrEmpty(adresseUpdate.getPays())) {
            adresseCurrent.setVille(adresseUpdate.getVille());
        }
        if (adresseUpdate.getEntreprise() != null) {
            adresseCurrent.setEntreprise(adresseUpdate.getEntreprise());
        }
        if (adresseUpdate.getPersonnel() != null) {
            adresseCurrent.setPersonnel(adresseUpdate.getPersonnel());
        }
        adresseCurrent = adresseRepository.save(adresseCurrent);
        return adresseMapper.toDto(adresseCurrent);
    }

}
