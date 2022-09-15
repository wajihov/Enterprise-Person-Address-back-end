package com.example.societepersonnel.domain.adresse;

import com.example.societepersonnel.dto.AdresseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdresseService {

    private final AdresseRepository adresseRepository;
    private final AdresseMapper adresseMapper;

    public AdresseService(AdresseRepository adresseRepository, AdresseMapper adresseMapper) {
        this.adresseRepository = adresseRepository;
        this.adresseMapper = adresseMapper;
    }

    public AdresseDto create(AdresseDto adresseDto) {
        log.info("adding adresse {}", adresseDto.getAdresse());
        Adresse adresse = adresseMapper.toEntity(adresseDto);
        adresse = adresseRepository.save(adresse);
        return adresseMapper.toDto(adresse);
    }


}
