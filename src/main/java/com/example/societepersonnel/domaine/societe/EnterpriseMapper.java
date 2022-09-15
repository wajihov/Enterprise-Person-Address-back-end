package com.example.societepersonnel.domaine.societe;

import com.example.societepersonnel.domaine.adresse.AdresseMapper;
import com.example.societepersonnel.domaine.personnel.PersonnelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnterpriseMapper {

    private final PersonnelMapper personnelMapper;
    private final AdresseMapper adresseMapper;

    public EnterpriseMapper(PersonnelMapper personnelMapper, AdresseMapper adresseMapper) {
        this.personnelMapper = personnelMapper;
        this.adresseMapper = adresseMapper;
    }

    public Enterprise toEntity(EnterpriseDto enterpriseDto) {
        if (enterpriseDto == null) {
            return null;
        }
        return Enterprise.builder()
                .id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .adresse(adresseMapper.toEntity(enterpriseDto.getAdresse()))
                .numFiscale(enterpriseDto.getNumFiscale())
                .build();
    }

    public EnterpriseDto toDto(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }
        return EnterpriseDto.builder()
                .id(enterprise.getId())
                .name(enterprise.getName())
                .adresse(adresseMapper.toDto(enterprise.getAdresse()))
                .personnels(personnelMapper.toDtos(enterprise.getPersonnels()))
                .build();
    }

    public List<EnterpriseDto> toDtos(List<Enterprise> enterprises) {
        return enterprises.stream().map(this::toDto).collect(Collectors.toList());
    }

}
