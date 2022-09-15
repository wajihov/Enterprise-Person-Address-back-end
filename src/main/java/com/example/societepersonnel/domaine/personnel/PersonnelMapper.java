package com.example.societepersonnel.domaine.personnel;

import com.example.societepersonnel.domaine.adresse.AdresseMapper;
import com.example.societepersonnel.domaine.societe.Enterprise;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonnelMapper {

    private AdresseMapper adresseMapper;

    public PersonnelMapper(AdresseMapper adresseMapper) {
        this.adresseMapper = adresseMapper;
    }

    public PersonnelDto toDto(Personnel personnel) {
        if (personnel == null) {
            return null;
        }
        return PersonnelDto.builder()
                .id(personnel.getId())
                .name(personnel.getName())
                .lastName(personnel.getLastName())
                .adresse(adresseMapper.toDto(personnel.getAdresse()))
                .post(personnel.getPost())
                .enterpriseId(personnel.getEnterprise().getId())
                .build();
    }

    public Personnel toEntity(Enterprise enterprise, PersonnelDto personnelDto) {
        if (personnelDto == null) {
            return null;
        }
        Personnel personnel = Personnel.builder()
                .id(personnelDto.getId())
                .name(personnelDto.getName())
                .lastName(personnelDto.getLastName())
                .adresse(adresseMapper.toEntity(personnelDto.getAdresse()))
                .post(personnelDto.getPost())
                .enterprise(enterprise)
                .build();
        return personnel;
    }

    public List<PersonnelDto> toDtos(List<Personnel> personnelList) {
        return personnelList.stream().map(this::toDto).collect(Collectors.toList());
    }


}
