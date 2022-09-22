package com.example.societepersonnel.domain.address;

import com.example.societepersonnel.core.exception.EntreprisePersonnelException;
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
                new EntreprisePersonnelException(Codes.ERR_ADRESS_NOT_FOUND));
    }

    public AddressDto createAddress(AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        address = addressRepository.save(address);
        log.info("the address is added successfully {}", addressDto.getAddress());
        return addressMapper.toDto(address);
    }

    public AddressDto findAddressById(Long id) {
        Address address = searchAddressById(id);
        AddressDto adresseDto = addressMapper.toDto(address);
        log.info("the address searched is {}", address.getAddress());
        return adresseDto;
    }

    public List<AddressDto> listAddressDto() {
        List<Address> addresses = addressRepository.findAll();
        log.info("list {} addresses", addresses.size());
        return addressMapper.toDtos(addresses);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
        log.info("The deletion of the address number {} is successful", id);
    }

    public AddressDto modifyAddress(Long id, AddressDto addressDto) {
        /*log.info("la modification commence {}, {} ", adresseDto.getAdresse(), id);
        Address addressUpdate = addressMapper.toEntity(adresseDto);
        Address addressCurrent = searchAdresseById(id);
        if (!StringUtils.isNullOrEmpty(addressUpdate.getAdresse())) {
            addressCurrent.setAdresse(addressUpdate.getAdresse());
        }
        if (!StringUtils.isNullOrEmpty(addressUpdate.getCodePostal())) {
            addressCurrent.setCodePostal(addressUpdate.getCodePostal());
        }
        if (!StringUtils.isNullOrEmpty(addressUpdate.getPays())) {
            addressCurrent.setPays(addressUpdate.getPays());
        }
        if (!StringUtils.isNullOrEmpty(addressUpdate.getVille())) {
            addressCurrent.setVille(addressUpdate.getVille());
        }
        if (addressUpdate.getEntreprise() != null) {
            addressCurrent.setEntreprise(addressUpdate.getEntreprise());
        }
        if (addressUpdate.getPersonnel() != null) {
            addressCurrent.setPersonnel(addressUpdate.getPersonnel());
        }
        addressCurrent = addressRepository.save(addressCurrent);
        return addressMapper.toDto(addressCurrent);*/
        return null;
    }

}
