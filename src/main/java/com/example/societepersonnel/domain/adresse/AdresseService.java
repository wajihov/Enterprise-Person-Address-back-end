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

    private Boolean validateAdresse(AdresseDto adresseDto) throws EntreprisePersonnelException {
        if (StringUtils.isNullOrEmpty(adresseDto.getAdresse())) {
            throw new EntreprisePersonnelException(Codes.ERR_ADRESS_NOT_VAlID);
        } else return true;
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
        log.info(" Les adresses sont les suivants");
        List<Adresse> adresses = adresseRepository.findAll();
        return adresseMapper.toDtos(adresses);
    }

    public boolean deleteAdresse(Long id) {
        log.info("La supprission de l'adresse numero {}", id);
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
                new RuntimeException("L'id " + id + " n'existe pas dans la BAse de donnée"));
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
