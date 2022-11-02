package com.example.societepersonnel.person;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.person.Person;
import com.example.societepersonnel.domain.person.PersonMapper;
import com.example.societepersonnel.domain.person.Post;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import com.example.societepersonnel.dto.PersonDto;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    void Given_Person_WHEN_ToDto_THEN_SHOULD_return_PersonDto() {
        //GIVEN
        var address = new Address();
        address.setId(1L);
        address.setAddress("Street MontPlainer");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("5022");

        var enterprise = new Enterprise();
        enterprise.setId(1L);
        enterprise.setTaxNumber("RT-5671");
        enterprise.setName("Partner");
        enterprise.setAddress(address);

        var person = new Person();
        person.setId(1L);
        person.setName("Saki");
        person.setLastName("Arrigo");
        person.setPost(Post.valueOf("EMPLOYEE"));
        person.setAddress(address);
        person.setEnterprise(enterprise);
        //WHEN
        var personDto = personMapper.toDto(person);
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
    void Given_Person_WHEN_ToDto_THEN_SHOULD_return_null() {
        //GIVEN & WHEN
        var personDto = personMapper.toDto(null);
        //THEN
        Assertions.assertNull(personDto);
    }

    @Test
    void Given_Person_WHEN_ToEntity_THEN_SHOULD_return_null() {
        //GIVEN & WHEN
        var person = personMapper.toEntity(null, null);
        //THEN
        Assertions.assertNull(person);
    }

    @Test
    void Given_Person_WHEN_ToEntity_THEN_SHOULD_return_null_2() {
        //GIVEN & WHEN
        var person = personMapper.toEntity(null);
        //THEN
        Assertions.assertNull(person);
    }

    @Test
    void Given_ListPerson_WHEN_ToListDtoPerson_THEN_SHOULD_return_null() {
        //GIVEN & WHEN
        List<PersonDto> persons = personMapper.toDtoList(null);
        //THEN
        Assertions.assertNull(persons);
    }


    @Test
    void Given_PersonDto_WHEN_toPerson_THEN_SHOULD_return_Person() {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(2L);
        addressDto.setAddress("Street Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1L);
        enterpriseDto.setName("Gaeta-EN");
        enterpriseDto.setTaxNumber("345-YUP");
        enterpriseDto.setLocalAddress(addressDto);

        var personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setEnterpriseId(enterpriseDto.getId());
        personDto.setName("Filip");
        personDto.setLastname("Trousers");
        personDto.setLocalAddress(addressDto);
        personDto.setPost(PersonDto.PostEnum.valueOf("ADMINISTRATOR"));

        //WHEN
        var person = personMapper.toEntity(personDto, addressDto);
        // THEN
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
    }

    @Test
    void Given_PersonDto_WHEN_toPerson_THEN_SHOULD_return_Person_2() {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(2L);
        addressDto.setAddress("Street Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1L);
        enterpriseDto.setName("Gaeta-EN");
        enterpriseDto.setTaxNumber("345-YUP");
        enterpriseDto.setLocalAddress(addressDto);

        var personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setEnterpriseId(enterpriseDto.getId());
        personDto.setName("Filip");
        personDto.setLastname("Trousers");
        personDto.setLocalAddress(addressDto);
        personDto.setPost(PersonDto.PostEnum.valueOf("ADMINISTRATOR"));

        //WHEN
        var person = personMapper.toEntity(personDto);
        // THEN
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
    }


    @Test
    void Given_PersonEntities_WHEN_toPersonList_THEN_SHOULD_return_PersonDtoList() {
        //GIVEN
        var address = new Address();
        address.setId(1L);
        address.setAddress("Street Farhad Hashed");
        address.setCity("Tunis");
        address.setCountry("Tunisia");
        address.setPostalCode("2001");

        var enterprise = new Enterprise();
        enterprise.setId(1L);
        enterprise.setName("Gaeta-EN");
        enterprise.setTaxNumber("345-YUP");
        enterprise.setAddress(address);

        var person = new Person();
        person.setId(1L);
        person.setEnterprise(enterprise);
        person.setName("Filip");
        person.setLastName("Trousers");
        person.setAddress(address);
        person.setPost(Post.valueOf("ADMINISTRATOR"));


        var secondAddress = new Address();
        secondAddress.setId(2L);
        secondAddress.setAddress("Avenue Palestine");
        secondAddress.setCity("Ariana");
        secondAddress.setCountry("Tunisia");
        secondAddress.setPostalCode("7800");

        var secondEnterprise = new Enterprise();
        secondEnterprise.setId(2L);
        secondEnterprise.setName("TELETHON");
        secondEnterprise.setTaxNumber("IOP0-98");
        secondEnterprise.setAddress(secondAddress);

        var secondPerson = new Person();
        secondPerson.setId(2L);
        secondPerson.setEnterprise(enterprise);
        secondPerson.setName("Giro");
        secondPerson.setLastName("Kimball");
        secondPerson.setAddress(secondAddress);
        secondPerson.setPost(Post.valueOf("ADMINISTRATOR"));

        List<Person> personList = new ArrayList<Person>() {
            {
                add(person);
                add(secondPerson);
            }
        };
        //WHEN
        List<PersonDto> personDtoList = personMapper.toDtoList(personList);
        //THEN
        Assertions.assertEquals(personDtoList.size(), personList.size());
        for (int i = 0; i < personDtoList.size(); i++) {
            Assertions.assertEquals(personDtoList.get(i).getId(), personList.get(i).getId());
            Assertions.assertEquals(personDtoList.get(i).getName(), personList.get(i).getName());
            Assertions.assertEquals(personDtoList.get(i).getLastname(), personList.get(i).getLastName());
            Assertions.assertEquals(personDtoList.get(i).getPost().toString(), personList.get(i).getPost().toString());
            Assertions.assertEquals(personDtoList.get(i).getEnterpriseId(), personList.get(i).getEnterprise().getId());
            Assertions.assertEquals(personDtoList.get(i).getLocalAddress().getId(), personList.get(i).getAddress().getId());
            Assertions.assertEquals(personDtoList.get(i).getLocalAddress().getAddress(), personList.get(i).getAddress().getAddress());
            Assertions.assertEquals(personDtoList.get(i).getLocalAddress().getCountry(), personList.get(i).getAddress().getCountry());
            Assertions.assertEquals(personDtoList.get(i).getLocalAddress().getCity(), personList.get(i).getAddress().getCity());
            Assertions.assertEquals(personDtoList.get(i).getLocalAddress().getPostalCode(), personList.get(i).getAddress().getPostalCode());
        }
    }
}
