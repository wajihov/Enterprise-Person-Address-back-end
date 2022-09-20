package com.example.societepersonnel.domain.personnel;

import com.example.societepersonnel.core.exception.EntreprisePersonnelException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.StringUtils;
import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.domain.adresse.AdresseMapper;
import com.example.societepersonnel.domain.adresse.AdresseService;
import com.example.societepersonnel.domain.entreprise.Entreprise;
import com.example.societepersonnel.domain.entreprise.EntrepriseMapper;
import com.example.societepersonnel.domain.entreprise.EntrepriseService;
import com.example.societepersonnel.dto.AdresseDto;
import com.example.societepersonnel.dto.EntrepriseDto;
import com.example.societepersonnel.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PersonnelService {

    private final PersonnelRepository personnelRepository;
    private final PersonnelMapper personnelMapper;
    private final AdresseMapper adresseMapper;
    private final AdresseService adresseService;
    private final EntrepriseMapper entrepriseMapper;
    private final EntrepriseService entrepriseService;

    public PersonnelService(PersonnelRepository personnelRepository, PersonnelMapper personnelMapper, AdresseMapper adresseMapper, AdresseService adresseService, EntrepriseMapper entrepriseMapper, EntrepriseService entrepriseService) {
        this.personnelRepository = personnelRepository;
        this.personnelMapper = personnelMapper;
        this.adresseMapper = adresseMapper;
        this.adresseService = adresseService;
        this.entrepriseMapper = entrepriseMapper;
        this.entrepriseService = entrepriseService;
    }

    private Adresse addAdresse(Long id) {
        AdresseDto adresseDto = adresseService.findAdresseById(id);
        Adresse adresse = adresseMapper.toEntity(adresseDto);
        return adresse;
    }

    private Entreprise addEntreprise(Long id) {
        EntrepriseDto entrepriseDto = entrepriseService.findEntrepriseById(id);
        Entreprise entreprise = entrepriseMapper.toEntity(entrepriseDto);
        return entreprise;
    }

    private Personnel searchPersonById(Long id) {
        return personnelRepository.findById(id).orElseThrow(()
                -> new EntreprisePersonnelException(Codes.ERR_PERSONNEL_NOT_FOUND));
    }

    public PersonDto createPerson(PersonDto personDto) {
        log.info("L'ajout d'une personne {}", personDto.getName());
        Personnel personnel = personnelMapper.toEntity(personDto);
        Long adresse_id = personDto.getAdresseId();
        Adresse adresse = addAdresse(adresse_id);
        personnel.setAdresse(adresse);
        Long entreprise_id = personDto.getEnterpriseId();
        Entreprise entreprise = addEntreprise(entreprise_id);
        personnel.setEntreprise(entreprise);
        personnel = personnelRepository.save(personnel);
        return personnelMapper.toDto(personnel);
    }

    public PersonDto findPersonById(Long id) {
        log.info("La rechercher de personne est {} ", id);
        Personnel personnel = searchPersonById(id);
        return personnelMapper.toDto(personnel);
    }

    public List<PersonDto> findPersons() {
        List<Personnel> personnels = personnelRepository.findAll();
        log.info("les personnels {} sont recherché", personnels.size());
        return personnelMapper.toDtos(personnels);
    }

    public PersonDto updatePerson(Long id, PersonDto personDto) {
        Personnel currentPerson = personnelMapper.toEntity(personDto);
        Personnel modifyPerson = searchPersonById(id);
        Long id_adresse = personDto.getAdresseId();
        Long id_entreprise = personDto.getEnterpriseId();
        if (!StringUtils.isNullOrEmpty(currentPerson.getName())) {
            modifyPerson.setName(currentPerson.getName());
        }
        if (!StringUtils.isNullOrEmpty(currentPerson.getLastName())) {
            modifyPerson.setLastName(currentPerson.getLastName());
        }
        if (currentPerson.getPost() != null) {
            modifyPerson.setPost(currentPerson.getPost());
        }
        if (id_adresse != null) {
            Adresse adresse = addAdresse(id_adresse);
            modifyPerson.setAdresse(adresse);
        }
        if (id_entreprise != null) {
            Entreprise entreprise = addEntreprise(id_entreprise);
            modifyPerson.setEntreprise(entreprise);
        }
        modifyPerson = personnelRepository.save(modifyPerson);
        return personnelMapper.toDto(modifyPerson);
    }

    public void deleteEntreprise(Long id) {
        if (searchPersonById(id) != null) {
            log.info("la supprission de la personnne {} est effectué avec succès ", id);
            personnelRepository.deleteById(id);
        }
    }

}
