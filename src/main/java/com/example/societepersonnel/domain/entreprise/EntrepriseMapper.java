package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.core.utils.CollectionUtils;
import com.example.societepersonnel.domain.personnel.PersonnelMapper;
import com.example.societepersonnel.dto.EntrepriseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrepriseMapper {

    private final PersonnelMapper personnelMapper;

    public EntrepriseMapper(PersonnelMapper personnelMapper) {
        this.personnelMapper = personnelMapper;
    }

    public Entreprise toEntity(EntrepriseDto enterpriseDto) {
        if (enterpriseDto == null) {
            return null;
        }
        return Entreprise.builder()
                .id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .numFiscale(enterpriseDto.getNumFiscale())
                //.adresse(enterpriseDto.getAdresseId())
                //.personnels(enterpriseDto.getPersonnels())
                .build();
    }

    public EntrepriseDto toDto(Entreprise entreprise) {
        if (entreprise == null) {
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .name(entreprise.getName())
                .numFiscale(entreprise.getNumFiscale())
                .adresseId(entreprise.getAdresse().getId())
                .personnels(personnelMapper.toDtos(entreprise.getPersonnels()))
                .build();
    }

    public List<EntrepriseDto> toDtos(List<Entreprise> enterprises) {
        if (CollectionUtils.isNullOrEmpty(enterprises)) {
            return null;
        }
        return enterprises.stream().map(this::toDto).collect(Collectors.toList());
    }

}
