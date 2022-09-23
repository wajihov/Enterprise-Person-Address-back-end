package com.example.societepersonnel.domain.enterprise;

import com.example.societepersonnel.core.utils.CollectionUtils;
import com.example.societepersonnel.domain.personal.PersonalMapper;
import com.example.societepersonnel.dto.EnterpriseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnterpriseMapper {

    private final PersonalMapper personalMapper;

    public EnterpriseMapper(PersonalMapper personalMapper) {
        this.personalMapper = personalMapper;
    }

    public Enterprise toEntity(EnterpriseDto enterpriseDto) {
        if (enterpriseDto == null) {
            return null;
        }
        return Enterprise.builder()
                .id(enterpriseDto.getId())
                .name(enterpriseDto.getName())
                .taxNumber(enterpriseDto.getTaxNumber())
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
                .addressId(enterprise.getAddress().getId())
                .personals(personalMapper.toDtos(enterprise.getPersonals()))
                .build();
    }

    public List<EnterpriseDto> toDtos(List<Enterprise> enterprises) {
        if (CollectionUtils.isNullOrEmpty(enterprises)) {
            return null;
        }
        return enterprises.stream().map(this::toDto).collect(Collectors.toList());
    }

}
