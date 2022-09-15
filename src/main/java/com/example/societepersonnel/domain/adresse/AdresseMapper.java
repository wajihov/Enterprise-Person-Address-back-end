package com.example.societepersonnel.domain.adresse;

import com.example.societepersonnel.dto.AdresseDto;
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
                .id(adresseDto.getId())
                .adresse(adresseDto.getAdresse())
                .ville(adresseDto.getVille())
                .pays(adresseDto.getPays())
                .codePostal(adresseDto.getCodePostal())
                .build();
    }

    public AdresseDto toDto(Adresse adresse) {
        if (adresse == null) {
            return null;
        }
        return AdresseDto.builder()
                .id(adresse.getId())
                .adresse(adresse.getAdresse())
                .ville(adresse.getVille())
                .pays(adresse.getPays())
                .codePostal(adresse.getCodePostal())
                .build();
    }


    public List<AdresseDto> toDtos(List<Adresse> adresses) {
        return adresses.stream().map(this::toDto).collect(Collectors.toList());
    }

}
