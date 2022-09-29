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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressServicesTest {

    @MockBean
    AddressRepository addressRepository;
    @Autowired
    AddressService addressService;

    @Captor
    private ArgumentCaptor<Address> addressArgumentCaptor;

    @Test
    void Given_Address_WHEN_CreateAddress_THEN_Should_save_database() {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Avenue Farhat Hached");
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
    void GIVEN_addressDtoId_WHEN_deleteAddressRequest_THEN_SHOULD_delete_from_database() {
        //GIVEN
        Address address = new Address();
        address.setId(1l);
        address.setAddress("Rue Hedi chaker");
        address.setCity("Manouba");
        address.setCountry("Tunisia");
        address.setPostalCode("3008");
        Mockito.when(addressRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(address));
        //WHEN
        addressService.deleteAddress(1l);

        //THEN
        Mockito.verify(addressRepository).delete(addressArgumentCaptor.capture());

        Address addressFomCaptor = addressArgumentCaptor.capture();
        Assertions.assertEquals(addressFomCaptor.getAddress(), addressFomCaptor.getAddress());
        Assertions.assertEquals(addressFomCaptor.getCity(), addressFomCaptor.getCity());
        Assertions.assertEquals(addressFomCaptor.getCountry(), addressFomCaptor.getCountry());
        Assertions.assertEquals(addressFomCaptor.getPostalCode(), addressFomCaptor.getPostalCode());
    }


}
