package com.example.societepersonnel.domain.personal;

import com.example.societepersonnel.core.utils.CollectionUtils;
import com.example.societepersonnel.dto.PersonalDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalMapper {

    public PersonalDto toDto(Personal personal) {
        if (personal == null) {
            return null;
        }
        return PersonalDto.builder()
                .id(personal.getId())
                .name(personal.getName())
                .lastName(personal.getLastName())
                .addressId(personal.getAddress().getId())
                .post(PersonalDto.PostEnum.valueOf(personal.getPost().toString()))
                .enterpriseId(personal.getEnterprise().getId())
                .build();
    }

    public Personal toEntity(PersonalDto personnelDto) {
        if (personnelDto == null) {
            return null;
        }
        Personal personal = Personal.builder()
                .id(personnelDto.getId())
                .name(personnelDto.getName())
                .lastName(personnelDto.getLastName())
                .post(Post.valueOf(personnelDto.getPost().getValue()))
                .build();
        return personal;
    }

    public List<PersonalDto> toDtos(List<Personal> personals) {
        if (CollectionUtils.isNullOrEmpty(personals)) {
            return null;
        }
        return personals.stream().map(this::toDto).collect(Collectors.toList());
    }


}
