package com.example.societepersonnel.domaine.adresse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdresseMapper {


    public Adresse toEntity(AdresseDto adresseDto) {
        if (adresseDto == null) {
            return null;
        }
        return Adresse.builder()
                .address(adresseDto.getAdresse())
                .codePostal(adresseDto.getCodePostal())
                .pays(adresseDto.getPays())
                .codePostal(adresseDto.getCodePostal())
                .build();
    }

    public AdresseDto toDto(Adresse adresse) {
        if (adresse == null) {
            return null;
        }
        return AdresseDto.builder()
                .adresse(adresse.getAddress())
                .codePostal(adresse.getCodePostal())
                .pays(adresse.getPays())
                .codePostal(adresse.getCodePostal())
                .build();
    }


    public List<AdresseDto> toDtos(List<Adresse> adresses) {
        return adresses.stream().map(this::toDto).collect(Collectors.toList());
    }

}
