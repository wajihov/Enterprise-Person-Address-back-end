package com.example.societepersonnel.domain.enterprise;

import com.example.societepersonnel.core.utils.CollectionUtils;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.domain.person.PersonMapper;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnterpriseMapper {

    private final PersonMapper personMapper;
    private final AddressMapper addressMapper;

    public EnterpriseMapper(PersonMapper personMapper, AddressMapper addressMapper) {
        this.personMapper = personMapper;
        this.addressMapper = addressMapper;
    }

    public Enterprise toEntity(EnterpriseDto enterpriseDto, AddressDto addressDto) {
        if (enterpriseDto == null) {
            return null;
        }
        return Enterprise.builder()
                .id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .taxNumber(enterpriseDto.getTaxNumber())
                .address(addressMapper.toEntity(addressDto))
                .build();
    }

    public Enterprise toEntity(EnterpriseDto enterpriseDto) {
        if (enterpriseDto == null) {
            return null;
        }
        return Enterprise.builder()
                .id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .taxNumber(enterpriseDto.getTaxNumber())
                .address(addressMapper.toEntity(enterpriseDto.getLocalAddress()))
                .build();
    }

    public EnterpriseDto toDto(Enterprise enterprise) {
        if (enterprise == null) {
            return null;
        }
        return EnterpriseDto.builder()
                .id(enterprise.getId())
                .name(enterprise.getName())
                .taxNumber(enterprise.getTaxNumber())
                .localAddress(addressMapper.toDto(enterprise.getAddress()))
                .persons(personMapper.toDtos(enterprise.getPersons()))
                .build();
    }

    public List<EnterpriseDto> toDtos(List<Enterprise> enterprises) {
        if (CollectionUtils.isNullOrEmpty(enterprises)) {
            return null;
        }
        return enterprises.stream().map(this::toDto).collect(Collectors.toList());
    }

}
