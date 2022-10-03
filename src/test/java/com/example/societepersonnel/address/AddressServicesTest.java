package com.example.societepersonnel.address;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressRepository;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.dto.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressServicesTest {

    @MockBean
    private AddressRepository addressRepository;
    @Autowired
    private AddressService addressService;
    @Captor
    private ArgumentCaptor<Address> addressArgumentCaptor;

    @Test
    void Given_Address_WHEN_CreateAddress_THEN_Should_save_database() {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Avenue Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        //WHEN
        addressService.createAddress(addressDto);
        //THEN
        Mockito.verify(addressRepository).save(addressArgumentCaptor.capture());
        Address address = addressArgumentCaptor.getValue();
        assertEquals(address.getAddress(), addressDto.getAddress());
        assertEquals(address.getCity(), addressDto.getCity());
        assertEquals(address.getCountry(), addressDto.getCountry());
        assertEquals(address.getPostalCode(), addressDto.getPostalCode());
    }

    @Test
    void GIVEN_addressDtoId_WHEN_deleteAddress_THEN_SHOULD_delete_from_database() {
        //GIVEN
        Address address = new Address();
        address.setId(10l);
        address.setAddress("Avenue Habib burger");
        address.setCity("Manabu");
        address.setCountry("Tunisia");
        address.setPostalCode("2009");
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(address));
        //WHEN
        addressService.deleteAddress(10l);

        //THEN
        Mockito.verify(addressRepository).delete(addressArgumentCaptor.capture());
        Address addressFomCaptor = addressArgumentCaptor.getValue();
        Assertions.assertEquals(address.getId(), addressFomCaptor.getId());
        Assertions.assertEquals(address.getAddress(), addressFomCaptor.getAddress());
        Assertions.assertEquals(address.getCity(), addressFomCaptor.getCity());
        Assertions.assertEquals(address.getCountry(), addressFomCaptor.getCountry());
        Assertions.assertEquals(address.getPostalCode(), addressFomCaptor.getPostalCode());
    }

    @Test
    void GIVEN_addressDtoId_WHEN_findAddress_THEN_SHOULD_findAddress_from_database() {
        //GIVEN
        Address address = new Address();
        address.setId(1L);
        address.setAddress("Avenue Habib bourbon");
        address.setCity("Manitoba");
        address.setCountry("Tunisia");
        address.setPostalCode("2009");
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(address));

        //WHEN
        AddressDto addressDto = addressService.findAddressById(1L);

        //THEN
        Assertions.assertEquals(address.getAddress(), addressDto.getAddress());
        Assertions.assertEquals(address.getCity(), addressDto.getCity());
        Assertions.assertEquals(address.getCountry(), addressDto.getCountry());
        Assertions.assertEquals(address.getPostalCode(), addressDto.getPostalCode());
    }

    @Test
    void GIVEN_2_Address_saved_WHEN_getAllAddress_then_should_return_address_from_database() {
        //GIVEN
        Address address1 = new Address();
        address1.setAddress("Avenue Hedi Noura");
        address1.setCity("Manual");
        address1.setCountry("Tunisia");
        address1.setPostalCode("2001");
        Address address2 = new Address();
        address1.setAddress("Avenue Med 5");
        address1.setCity("Tunis");
        address1.setCountry("Tunisia");
        address1.setPostalCode("1001");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address1);
        addresses.add(address2);
        Mockito.when(addressRepository.findAll()).thenReturn(addresses);
        //WHEN
        List<AddressDto> addressLists = addressService.listAddressDto();
        //THEN
        Assertions.assertEquals(2, addressLists.size());
    }

    @Test
    void GIVEN_Address_WHEN_updateAddress_THEN_SHOULD_UPDATE_ON_DATABASE() {
        //GIVEN
        Address address = new Address();
        address.setAddress("Avenue Tarek ibn zed");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("3008");

        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Avenue Habib Bourgain ");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("5000");
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(address));
        //WHEN
        addressService.updateAddress(1l, addressDto);
        //THEN
        Mockito.verify(addressRepository).save(addressArgumentCaptor.capture());
        Address addressUpdate = addressArgumentCaptor.getValue();
        Assertions.assertEquals(addressUpdate.getAddress(), addressDto.getAddress());
        Assertions.assertEquals(addressUpdate.getCity(), addressDto.getCity());
        Assertions.assertEquals(addressUpdate.getCountry(), addressDto.getCountry());
        Assertions.assertEquals(addressUpdate.getPostalCode(), addressDto.getPostalCode());
    }
}
