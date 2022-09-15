package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.dto.EntrepriseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrepriseMapper {

    public Enterprise toEntity(EntrepriseDto enterpriseDto, Adresse adresse) {
        if (enterpriseDto == null) {
            return null;
        }
        return Enterprise.builder()
                .id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .adresse(adresse)
                .numFiscale(enterpriseDto.getNumFiscale())
                .build();
    }

    public EntrepriseDto toDto(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }
        return EntrepriseDto.builder()
                .id(enterprise.getId())
                .name(enterprise.getName())
                .adresseId(enterprise.getAdresse().getId())
                .build();
    }

    public List<EntrepriseDto> toDtos(List<Enterprise> enterprises) {
        return enterprises.stream().map(this::toDto).collect(Collectors.toList());
    }

}
