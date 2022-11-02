package com.example.societepersonnel.address;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.domain.address.AddressRepository;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.dto.AddressDto;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressServicesTest {

    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private AddressMapper addressMapper;
    @Autowired
    private AddressService addressService;
    @Captor
    private ArgumentCaptor<Long> addressIdArgumentCaptor;
    @Captor
    private ArgumentCaptor<Address> addressArgumentCaptor;

    @Test
    void Given_AddressDto_WHEN_CreateAddress_THEN_Should_save_database() {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Avenue Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        var address = new Address();
        address.setId(addressDto.getId());
        address.setAddress(addressDto.getAddress());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());
        Mockito.when(addressMapper.toEntity(addressDto)).thenReturn(address);
        Mockito.when(addressRepository.save(address)).thenReturn(address);
        Mockito.when(addressMapper.toDto(address)).thenReturn(addressDto);
        //WHEN
        addressDto = addressService.createAddress(addressDto);
        //THEN
        Mockito.verify(addressRepository).save(addressArgumentCaptor.capture());
        var addressSaved = addressArgumentCaptor.getValue();
        assertEquals(addressSaved.getAddress(), addressDto.getAddress());
        assertEquals(addressSaved.getCity(), addressDto.getCity());
        assertEquals(addressSaved.getCountry(), addressDto.getCountry());
        assertEquals(addressSaved.getPostalCode(), addressDto.getPostalCode());
    }

    @Test
    void GIVEN_addressDtoId_WHEN_deleteAddress_THEN_SHOULD_delete_from_database() {
        //GIVEN
        var address = new Address();
        address.setId(10l);
        address.setAddress("Avenue Habib burger");
        address.setCity("Manabu");
        address.setCountry("Tunisia");
        address.setPostalCode("2009");
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(address));
        //WHEN
        addressService.deleteAddress(10l);
        //THEN
        Mockito.verify(addressRepository).deleteById(addressIdArgumentCaptor.capture());
        var addressFomCaptor = addressIdArgumentCaptor.getValue();
        Assertions.assertEquals(address.getId(), addressFomCaptor);
    }

    @Test
    void GIVEN_addressDtoId_WHEN_findAddress_THEN_SHOULD_findAddress_from_database() {
        //GIVEN
        var addressId = 1L;
        var address = new Address();
        address.setId(1L);
        address.setAddress("Avenue Habib bourbon");
        address.setCity("Manitoba");
        address.setCountry("Tunisia");
        address.setPostalCode("2009");

        var addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setAddress(address.getAddress());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        addressDto.setPostalCode(address.getPostalCode());

        Mockito.when(addressMapper.toEntity(addressDto)).thenReturn(address);
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(address));
        Mockito.when(addressMapper.toDto(address)).thenReturn(addressDto);
        //WHEN
        addressDto = addressService.findAddressById(addressId);
        //THEN
        Assertions.assertEquals(address.getId(), addressDto.getId());
        Assertions.assertEquals(address.getAddress(), addressDto.getAddress());
        Assertions.assertEquals(address.getCity(), addressDto.getCity());
        Assertions.assertEquals(address.getCountry(), addressDto.getCountry());
        Assertions.assertEquals(address.getPostalCode(), addressDto.getPostalCode());
    }

    @Test
    void GIVEN_2_Address_saved_WHEN_getAllAddress_then_should_return_address_from_database() {
        //GIVEN
        var firstAddress = new Address();
        firstAddress.setId(1L);
        firstAddress.setAddress("Avenue Hedi Noura");
        firstAddress.setCity("Manual");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("2001");

        var firstAddressDto = new AddressDto();
        firstAddressDto.setId(firstAddress.getId());
        firstAddressDto.setAddress(firstAddress.getAddress());
        firstAddressDto.setCity(firstAddress.getCity());
        firstAddressDto.setCountry(firstAddress.getCountry());
        firstAddressDto.setPostalCode(firstAddress.getPostalCode());

        var secondAddress = new Address();
        secondAddress.setAddress("Avenue Med 5");
        secondAddress.setId(2L);
        secondAddress.setCity("Tunis");
        secondAddress.setCountry("Tunisia");
        secondAddress.setPostalCode("1001");
        List<Address> addresses = new ArrayList<>();
        addresses.add(firstAddress);
        addresses.add(secondAddress);

        var secondAddressDto = new AddressDto();
        secondAddressDto.setId(secondAddress.getId());
        secondAddressDto.setAddress(secondAddress.getAddress());
        secondAddressDto.setCity(secondAddress.getCity());
        secondAddressDto.setCountry(secondAddress.getCountry());
        secondAddressDto.setPostalCode(secondAddress.getPostalCode());
        List<AddressDto> addressDtoList = new ArrayList<AddressDto>() {
            {
                add(firstAddressDto);
                add(secondAddressDto);
            }
        };
        Mockito.when(addressRepository.findAll()).thenReturn(addresses);
        Mockito.when(addressMapper.toDtoList(addresses)).thenReturn(addressDtoList);
        //WHEN
        addressDtoList = addressService.listAddressDto();
        //THEN
        Assertions.assertEquals(addressDtoList.size(), addresses.size());
        Assertions.assertEquals(2, addressDtoList.size());
        for (int i = 0; i < addressDtoList.size(); i++) {
            assertEquals(addressDtoList.get(i).getId(), addresses.get(i).getId());
            assertEquals(addressDtoList.get(i).getAddress(), addresses.get(i).getAddress());
            assertEquals(addressDtoList.get(i).getCity(), addresses.get(i).getCity());
            assertEquals(addressDtoList.get(i).getCountry(), addresses.get(i).getCountry());
            assertEquals(addressDtoList.get(i).getPostalCode(), addresses.get(i).getPostalCode());
        }
    }

    @Test
    void GIVEN_listAddress_WHEN_getAllAddress_then_should_return_EnterprisePersonException() {
        //GIVEN
        List<Address> addressList = Collections.emptyList();
        Mockito.when(addressRepository.findAll()).thenReturn(addressList);
        //
        EnterprisePersonException e = Assertions.assertThrows(EnterprisePersonException.class, () ->
                addressService.listAddressDto());
        Assertions.assertEquals("ADDRESSES NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_Address_WHEN_updateAddress_THEN_SHOULD_UPDATE_ON_DATABASE() {
        //GIVEN
        var addressId = 1L;
        var address = new Address();
        address.setId(1L);
        address.setAddress("Avenue Tarek ibn zed");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("3008");

        var addressDto = new AddressDto();
        addressDto.setAddress("Avenue Habib Bourgain ");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("5000");
        Mockito.when(addressMapper.toEntity(addressDto)).thenReturn(address);
        Mockito.when(addressRepository.save(Mockito.any())).thenReturn(address);
        Mockito.when(addressMapper.toDto(address)).thenReturn(addressDto);
        //WHEN
        var addressDtoUpdate = addressService.updateAddress(addressId, addressDto);
        //THEN
        Assertions.assertEquals(addressDtoUpdate.getId(), addressDto.getId());
        Assertions.assertEquals(addressDtoUpdate.getAddress(), addressDto.getAddress());
        Assertions.assertEquals(addressDtoUpdate.getCity(), addressDto.getCity());
        Assertions.assertEquals(addressDtoUpdate.getCountry(), addressDto.getCountry());
        Assertions.assertEquals(addressDtoUpdate.getPostalCode(), addressDto.getPostalCode());
    }

    @Test
    void Given_address_WHEN_toEntity_THEN_SHOULD_return_RuntimeException() {
        //GIVEN & WHEN
        var address = new Address();
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(address));
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            addressService.findAddressById(null);
        });
        Assertions.assertEquals("ADDRESS NOT FOUND", e.getMessage());
    }
}
