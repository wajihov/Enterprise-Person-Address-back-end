package com.example.societepersonnel.domain.address;

import com.example.societepersonnel.core.utils.CollectionUtils;
import com.example.societepersonnel.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressMapper {

    public Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        return Address.builder()
                .id(addressDto.getId())
                .address(addressDto.getAddress())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .postalCode(addressDto.getPostalCode())
                .build();
    }

    public AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        }
        return AddressDto.builder()
                .id(address.getId())
                .address(address.getAddress())
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }

    public List<AddressDto> toDtoList(List<Address> addresses) {
        if (CollectionUtils.isNullOrEmpty(addresses)) {
            return null;
        }
        return addresses.stream().map(this::toDto).collect(Collectors.toList());
    }

}
