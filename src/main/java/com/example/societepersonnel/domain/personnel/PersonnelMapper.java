package com.example.societepersonnel.domain.personnel;

import com.example.societepersonnel.core.utils.CollectionUtils;
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
                .post(PersonDto.PostEnum.valueOf(personnel.getPost().toString()))
                //.enterpriseId(personnel.getEntreprise().getId())
                .build();
    }

    public Personnel toEntity(PersonDto personnelDto) {
        if (personnelDto == null) {
            return null;
        }
        Personnel personnel = Personnel.builder()
                .id(personnelDto.getId())
                .name(personnelDto.getName())
                .lastName(personnelDto.getLastName())
                .post(Post.valueOf(personnelDto.getPost().getValue()))
                .build();
        return personnel;
    }

    public List<PersonDto> toDtos(List<Personnel> personnels) {
        if (CollectionUtils.isNullOrEmpty(personnels)) {
            return null;
        }
        return personnels.stream().map(this::toDto).collect(Collectors.toList());
    }


}
