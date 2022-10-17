package com.example.societepersonnel.address;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.dto.AddressDto;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    void GIVEN_address_WHEN_toAddressDto_THEN_should_return_addressDto() {
        //GIVEN
        var address = new Address();
        address.setId(1L);
        address.setAddress("Avenue Farhad Hashed");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("2001");
        //WHEN
        var addressDto = addressMapper.toDto(address);
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
        var addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Avenue Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");
        //WHEN
        var address = addressMapper.toEntity(addressDto);
        //THEN
        //Assertions.assertEquals(addressDto.getClass(), AddressDto.class);
        Assertions.assertEquals(address.getId(), addressDto.getId());
        Assertions.assertEquals(address.getAddress(), addressDto.getAddress());
        Assertions.assertEquals(address.getCountry(), addressDto.getCountry());
        Assertions.assertEquals(address.getCity(), addressDto.getCity());
        Assertions.assertEquals(address.getPostalCode(), addressDto.getPostalCode());
    }


    @Test
    void Given_AddressDtoList_WHEN_ToDto_THEN_SHOULD_return_Addresses() {
        //GIVEN
        var firstAddress = new Address();
        firstAddress.setId(1L);
        firstAddress.setAddress("Avenue Farhad Hashed");
        firstAddress.setCity("Tunis");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("2001");
        var secondAddress = new Address();
        secondAddress.setAddress("Avenue Habib Bourgeois");
        secondAddress.setId(2L);
        secondAddress.setCity("Manitoba");
        secondAddress.setCountry("Tunisia");
        secondAddress.setPostalCode("8007");

        List<Address> addressList = new ArrayList<Address>() {
            {
                add(firstAddress);
                add(secondAddress);
            }
        };
        //WHEN
        List<AddressDto> addressDtoList = addressMapper.toDtos(addressList);
        //THEN
        //Assertions.assertEquals(addressDto.getClass(), AddressDto.class);
        for (int i = 0; i < addressDtoList.size(); i++) {
            Assertions.assertEquals(addressList.get(i).getId(), addressDtoList.get(i).getId());
            Assertions.assertEquals(addressList.get(i).getAddress(), addressDtoList.get(i).getAddress());
            Assertions.assertEquals(addressList.get(i).getCity(), addressDtoList.get(i).getCity());
            Assertions.assertEquals(addressList.get(i).getCountry(), addressDtoList.get(i).getCountry());
            Assertions.assertEquals(addressList.get(i).getPostalCode(), addressDtoList.get(i).getPostalCode());
        }
    }

    @Test
    void GIVEN_address_WHEN_toAddressDto_THEN_should_return_Exception() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            addressMapper.toEntity(null);
        });
        Assertions.assertEquals("ADDRESS NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_addressDto_WHEN_toAddress_THEN_should_return_Exception() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            addressMapper.toDto(null);
        });
        Assertions.assertEquals("ADDRESS NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_addresses_WHEN_toAddressesDto_THEN_should_return_Exception() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            addressMapper.toDtos(null);
        });
        Assertions.assertEquals("ADDRESSES NOT FOUND", e.getMessage());

    }

}
