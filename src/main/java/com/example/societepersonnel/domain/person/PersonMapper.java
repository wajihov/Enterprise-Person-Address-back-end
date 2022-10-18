package com.example.societepersonnel.domain.person;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.CollectionUtils;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.PersonDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonMapper {

    private final AddressMapper addressMapper;

    public PersonMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public PersonDto toDto(Person person) {
        if (person == null) {
            throw new EnterprisePersonException(Codes.ERR_PERSON_NOT_FOUND);
        }
        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .lastname(person.getLastName())
                .post(PersonDto.PostEnum.valueOf(person.getPost().toString()))
                .localAddress(addressMapper.toDto(person.getAddress()))
                .enterpriseId(person.getEnterprise().getId())
                .build();
    }

    public Person toEntity(PersonDto personnelDto, AddressDto addressDto) {
        if (personnelDto == null) {
            throw new EnterprisePersonException(Codes.ERR_PERSON_NOT_FOUND);
        }
        Person person = Person.builder()
                .id(personnelDto.getId())
                .name(personnelDto.getName())
                .lastName(personnelDto.getLastname())
                .post(Post.valueOf(personnelDto.getPost().getValue()))
                .address(addressMapper.toEntity(addressDto))
                .build();
        return person;
    }

    public List<PersonDto> toDtoList(List<Person> persons) {
        if (CollectionUtils.isNullOrEmpty(persons)) {
            throw new EnterprisePersonException(Codes.ERR_PERSONS_NOT_FOUND);
        }
        return persons.stream().map(this::toDto).collect(Collectors.toList());
    }
}
