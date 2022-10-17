package com.example.societepersonnel.domain.address;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    private Address searchAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() ->
                new EnterprisePersonException(Codes.ERR_ADDRESS_NOT_FOUND));
    }

    public AddressDto createAddress(AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        log.info("address = {} ", address);
        address = addressRepository.save(address);
        log.info("the address is added successfully {}", addressDto.getAddress());
        return addressMapper.toDto(address);
    }

    public AddressDto findAddressById(Long id) {
        Address address = searchAddressById(id);
        AddressDto addressDto = addressMapper.toDto(address);
        log.info("the address searched is {}", addressDto.getAddress());
        return addressDto;
    }

    public List<AddressDto> listAddressDto() {
        List<Address> addresses = addressRepository.findAll();
        log.info("list {} addresses", addresses.size());
        return addressMapper.toDtos(addresses);
    }

    public void deleteAddress(Long id) {
        Address address = searchAddressById(id);
        addressRepository.delete(address);
        log.info("The deletion of the address name {} is successful", address.getAddress());
    }

    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        address.setId(id);
        address = addressRepository.save(address);
        log.info("the address with id {} has been successfully modified", address.getAddress());
        return addressMapper.toDto(address);
    }

}
