package com.example.societepersonnel.domain.address;

import com.example.societepersonnel.AddressesApiDelegate;
import com.example.societepersonnel.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController implements AddressesApiDelegate {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;

    }

    @Override
    public ResponseEntity<AddressDto> createAddress(AddressDto addressDto) {
        AddressDto dto = addressService.createAddress(addressDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<AddressDto>> findAddresses() {
        List<AddressDto> dtoList = addressService.listAddressDto();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AddressDto> findAddressById(Long id) {
        AddressDto addressDto = addressService.findAddressById(id);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAddress(Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<AddressDto> updateAddress(Long id, AddressDto adresseDto) {
        AddressDto dto = addressService.updateAddress(id, adresseDto);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
}
