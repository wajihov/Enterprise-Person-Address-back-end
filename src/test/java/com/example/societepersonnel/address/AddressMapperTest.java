package com.example.societepersonnel.address;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.dto.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void Given_AddressDto_WHEN_ToDto_THEN_SHOULD_return_Address() {
        //GIVEN
        Address address = new Address();
        address.setAddress("Avenue Farhat Hached");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("2001");
        //WHEN
        AddressDto addressDto = addressMapper.toDto(address);
        //THEN
        Assertions.assertEquals(addressDto.getClass(), AddressDto.class);
    }

    @Test
    void Given_AddressDto_WHEN_toAddress_THEN_SHOULD_return_Address() {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Avenue Farhat Hached");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");
        //WHEN
        addressMapper.toEntity(addressDto);
        //THEN
        Assertions.assertEquals(addressDto.getClass(), AddressDto.class);

    }


}
