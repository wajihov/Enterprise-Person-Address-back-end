package com.example.societepersonnel.domain.adresse;

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
        log.info("l'adresse est ajouter avec succussffly {}", adresseDto.getAdresse());
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
        return adresseMapper.toDtos(adresses);
    }

    public boolean deleteAdresse(Long id) {
        Adresse adresse = searchAdresseById(id);
        if (adresse != null) {
            adresseRepository.delete(adresse);
            return true;
        } else
            return false;
    }

    private Adresse searchAdresseById(Long id) {
        if (id == null) {
            log.error("l'id est null");
            return null;
        }
        return adresseRepository.findById(id).orElseThrow(() ->
                new RuntimeException("L'id " + id + " n'existe pas dans la base de donnee"));
    }

    public AdresseDto modifyAdresse(Long id, AdresseDto adresseDto) {
        Adresse adresseUpdate = adresseMapper.toEntity(adresseDto);
        Adresse adresseCurrent = searchAdresseById(id);
        if (adresseUpdate.getAdresse() != null) {
            adresseCurrent.setAdresse(adresseUpdate.getAdresse());
        }
        if (adresseUpdate.getCodePostal() != null) {
            adresseCurrent.setCodePostal(adresseUpdate.getCodePostal());
        }
        if (adresseUpdate.getPays() != null) {
            adresseCurrent.setPays(adresseUpdate.getPays());
        }
        if (adresseUpdate.getVille() != null) {
            adresseCurrent.setVille(adresseUpdate.getVille());
        }
        if (adresseUpdate.getEnterprise() != null) {
            adresseCurrent.setEnterprise(adresseUpdate.getEnterprise());
        }
        if (adresseUpdate.getPersonnel() != null) {
            adresseCurrent.setPersonnel(adresseUpdate.getPersonnel());
        }
        adresseCurrent = adresseRepository.save(adresseCurrent);
        return adresseMapper.toDto(adresseCurrent);
    }

}
