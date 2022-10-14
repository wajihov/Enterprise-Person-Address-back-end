package com.example.societepersonnel.domain.address;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.CollectionUtils;
import com.example.societepersonnel.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressMapper {

    public Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            throw new EnterprisePersonException(Codes.ERR_ADDRESS_NOT_FOUND);
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
            throw new EnterprisePersonException(Codes.ERR_ADDRESS_NOT_FOUND);
        }
        return AddressDto.builder()
                .id(address.getId())
                .address(address.getAddress())
                .city(address.getCity())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }

    public List<AddressDto> toDtos(List<Address> addresses) {
        if (CollectionUtils.isNullOrEmpty(addresses)) {
            throw new EnterprisePersonException(Codes.ERR_ADDRESSES_NOT_FOUND);
        }
        return addresses.stream().map(this::toDto).collect(Collectors.toList());
    }

}
