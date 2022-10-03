package com.example.societepersonnel.person;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.person.Person;
import com.example.societepersonnel.domain.person.PersonMapper;
import com.example.societepersonnel.domain.person.Post;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import com.example.societepersonnel.dto.PersonDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    void Given_PersonDto_WHEN_ToDto_THEN_SHOULD_return_Person() {
        //GIVEN
        Address address = new Address();
        address.setId(1l);
        address.setAddress("Street MontPlainer");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("5022");

        Enterprise enterprise = new Enterprise();
        enterprise.setId(1L);
        enterprise.setTaxNumber("RT-5671");
        enterprise.setName("Partner");

        Person person = new Person();
        person.setId(1L);
        person.setName("Saki");
        person.setLastName("Arrigo");
        person.setPost(Post.valueOf("EMPLOYEE"));
        person.setAddress(address);
        person.setEnterprise(enterprise);
        //WHEN
        PersonDto personDto = personMapper.toDto(person);
        //THEN
        //Assertions.assertEquals(personDto.getClass(), PersonDto.class);
        Assertions.assertEquals(personDto.getId(), person.getId());
        Assertions.assertEquals(personDto.getName(), person.getName());
        Assertions.assertEquals(personDto.getLastname(), person.getLastName());
        Assertions.assertEquals(personDto.getLocalAddress().getId(), person.getAddress().getId());
        Assertions.assertEquals(personDto.getLocalAddress().getAddress(), person.getAddress().getAddress());
        Assertions.assertEquals(personDto.getLocalAddress().getCity(), person.getAddress().getCity());
        Assertions.assertEquals(personDto.getLocalAddress().getCountry(), person.getAddress().getCountry());
        Assertions.assertEquals(personDto.getLocalAddress().getPostalCode(), person.getAddress().getPostalCode());
        Assertions.assertEquals(personDto.getEnterpriseId(), person.getEnterprise().getId());
    }

    @Test
    void Given_PersonDto_WHEN_toPerson_THEN_SHOULD_return_Person() {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(2L);
        addressDto.setAddress("Street Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("Gaeta-EN");
        enterpriseDto.setTaxNumber("345-YUP");
        enterpriseDto.setLocalAddress(addressDto);

        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setEnterpriseId(1L);
        personDto.setName("Filip");
        personDto.setLastname("Trousers");
        personDto.setLocalAddress(addressDto);
        personDto.setPost(PersonDto.PostEnum.valueOf("ADMINISTRATOR"));

        //WHEN
        Person person = personMapper.toEntity(personDto, addressDto);

        // THEN

        // Assertions.assertEquals(person, personDto);

        //Assertions.assertEquals(personDto.getClass(), PersonDto.class);

        Assertions.assertEquals(person.getId(), personDto.getId());
        Assertions.assertEquals(person.getName(), personDto.getName());
        Assertions.assertEquals(person.getLastName(), personDto.getLastname());
        Assertions.assertEquals(person.getPost().toString(), personDto.getPost().toString());
        Assertions.assertEquals(person.getAddress().getId(), personDto.getLocalAddress().getId());
        Assertions.assertEquals(person.getAddress().getAddress(), personDto.getLocalAddress().getAddress());
        Assertions.assertEquals(person.getAddress().getCity(), personDto.getLocalAddress().getCity());
        Assertions.assertEquals(person.getAddress().getCountry(), personDto.getLocalAddress().getCountry());
        Assertions.assertEquals(person.getAddress().getPostalCode(), personDto.getLocalAddress().getPostalCode());
        //Assertions.assertEquals(person.getEnterprise().getId(), personDto.getEnterpriseId());
    }
}
