package com.example.societepersonnel.domain.personnel;

import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.domain.entreprise.Enterprise;
import com.example.societepersonnel.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonnelMapper {


    public PersonDto toDto(Personnel personnel) {
        if (personnel == null) {
            return null;
        }
        return PersonDto.builder()
                .id(personnel.getId())
                .name(personnel.getName())
                .lastName(personnel.getLastName())
                .adresseId(personnel.getAdresse().getId())
                .post(personnel.getPost())
                .enterpriseId(personnel.getEnterprise().getId())
                .build();
    }

    public Personnel toEntity(Enterprise enterprise, PersonDto personnelDto, Adresse adresse) {
        if (personnelDto == null) {
            return null;
        }
        Personnel personnel = Personnel.builder()
                .id(personnelDto.getId())
                .name(personnelDto.getName())
                .lastName(personnelDto.getLastName())
                .adresse(adresse)
                .post(personnelDto.getPost())
                .enterprise(enterprise)
                .build();
        return personnel;
    }

    public List<PersonDto> toDtos(List<Personnel> personnelList) {
        return personnelList.stream().map(this::toDto).collect(Collectors.toList());
    }


}
