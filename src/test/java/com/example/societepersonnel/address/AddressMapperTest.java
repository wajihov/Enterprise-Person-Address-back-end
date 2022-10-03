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
        address.setAddress("Avenue Farhad Hashed");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("2001");
        //WHEN
        AddressDto addressDto = addressMapper.toDto(address);
        //THEN
        //Assertions.assertEquals(addressDto.getClass(), AddressDto.class);
        Assertions.assertEquals(addressDto.getId(), address.getId());
        Assertions.assertEquals(addressDto.getAddress(), address.getAddress());
        Assertions.assertEquals(addressDto.getCity(), address.getCity());
        Assertions.assertEquals(addressDto.getCountry(), address.getCountry());
        Assertions.assertEquals(addressDto.getPostalCode(), address.getPostalCode());
    }

    @Test
    void Given_AddressDto_WHEN_toAddress_THEN_SHOULD_return_Address() {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Avenue Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");
        //WHEN
        Address address = addressMapper.toEntity(addressDto);
        //THEN
        //Assertions.assertEquals(addressDto.getClass(), AddressDto.class);
        Assertions.assertEquals(address.getId(), addressDto.getId());
        Assertions.assertEquals(address.getAddress(), addressDto.getAddress());
        Assertions.assertEquals(address.getCountry(), addressDto.getCountry());
        Assertions.assertEquals(address.getCity(), addressDto.getCity());
        Assertions.assertEquals(address.getPostalCode(), addressDto.getPostalCode());
    }


}
