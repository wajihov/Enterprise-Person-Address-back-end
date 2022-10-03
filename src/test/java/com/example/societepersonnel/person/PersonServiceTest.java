package com.example.societepersonnel.person;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.person.Person;
import com.example.societepersonnel.domain.person.PersonRepository;
import com.example.societepersonnel.domain.person.PersonService;
import com.example.societepersonnel.domain.person.Post;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import com.example.societepersonnel.dto.PersonDto;
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


@SpringBootTest
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;
    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;

    @Test
    void GIVEN_PersonDto_WHEN_CreatePerson_THEN_Should_save_on_database() {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(2L);
        addressDto.setAddress("Street Ibn Addon");
        addressDto.setCity("Ariana");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("5230");

        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1L);
        enterpriseDto.setName("arena");
        enterpriseDto.setTaxNumber("768-TY");
        enterpriseDto.setLocalAddress(addressDto);

        PersonDto personDto = new PersonDto();
        personDto.setId(4L);
        personDto.setName("Hakim");
        personDto.setLastname("Hari");
        personDto.setLocalAddress(addressDto);
        personDto.setPost(PersonDto.PostEnum.valueOf("HRD"));
        personDto.setEnterpriseId(enterpriseDto.getId());

        //WHEN
        personService.createPerson(personDto);
        //THEN
        Mockito.verify(personRepository).save(personArgumentCaptor.capture());
        Person person = personArgumentCaptor.getValue();
        Assertions.assertEquals(personDto.getId(), person.getId());
        Assertions.assertEquals(personDto.getName(), person.getName());
        Assertions.assertEquals(personDto.getLastname(), person.getLastName());
        Assertions.assertEquals(personDto.getPost().toString(), person.getPost().toString());
        Assertions.assertEquals(personDto.getLocalAddress().getId(), person.getAddress().getId());
        Assertions.assertEquals(personDto.getLocalAddress().getAddress(), person.getAddress().getAddress());
        Assertions.assertEquals(personDto.getLocalAddress().getCity(), person.getAddress().getCity());
        Assertions.assertEquals(personDto.getLocalAddress().getCountry(), person.getAddress().getCountry());
        Assertions.assertEquals(personDto.getLocalAddress().getPostalCode(), person.getAddress().getPostalCode());
        //Assertions.assertEquals(personDto.getEnterpriseId(), person.getEnterprise().getId());
    }

    @Test
    void GIVEN_Person_saved_WHEN_getAllPersons_THEN_Should_return_that_2_Persons() {
        //GIVEN
        Address firstAddress = new Address();
        firstAddress.setAddress("Rue El Kolas");
        firstAddress.setCity("La Manitoba");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("3009");

        Enterprise firstEnterprise = new Enterprise();
        firstEnterprise.setId(1L);
        firstEnterprise.setName("Haziest-YU");
        firstEnterprise.setTaxNumber("OPT-675");
        firstEnterprise.setAddress(firstAddress);

        Address secondAddress = new Address();
        secondAddress.setAddress("Street Of Gables");
        secondAddress.setCity("City Chara");
        secondAddress.setCountry("Saguaro");
        secondAddress.setPostalCode("852-KL");

        Person firstPerson = new Person();
        firstPerson.setId(1L);
        firstPerson.setName("Alex");
        firstPerson.setLastName("Ferguson");
        firstPerson.setPost(Post.EMPLOYEE);
        firstPerson.setAddress(firstAddress);
        firstPerson.setEnterprise(firstEnterprise);
        firstPerson.setAddress(firstAddress);

        Person secondPerson = new Person();
        secondPerson.setId(1L);
        secondPerson.setName("Alex");
        secondPerson.setLastName("Ferguson");
        secondPerson.setAddress(firstAddress);
        secondPerson.setPost(Post.PRESIDENT);
        secondPerson.setEnterprise(firstEnterprise);
        secondPerson.setAddress(secondAddress);


        List<Person> persons = new ArrayList<Person>() {
            {
                add(firstPerson);
                add(secondPerson);
            }
        };
        Mockito.when(personRepository.findAll()).thenReturn(persons);
        //WHEN
        List<PersonDto> personList = personService.findPersons();
        //THEN
        Assertions.assertEquals(2, personList.size());
    }

    @Test
    void GIVEN_Person_update_WHEN_updatePersons_THEN_Should_update_that_person_on_Database() {
        //GIVEN
        Address firstAddress = new Address();
        firstAddress.setAddress("Rue El Kolas");
        firstAddress.setCity("La Manitoba");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("3009");

        Enterprise firstEnterprise = new Enterprise();
        firstEnterprise.setId(1L);
        firstEnterprise.setName("Haziest-YU");
        firstEnterprise.setTaxNumber("OPT-675");
        firstEnterprise.setAddress(firstAddress);

        Person firstPerson = new Person();
        firstPerson.setId(1L);
        firstPerson.setName("Alex");
        firstPerson.setLastName("Ferguson");
        firstPerson.setPost(Post.EMPLOYEE);
        firstPerson.setAddress(firstAddress);
        firstPerson.setEnterprise(firstEnterprise);
        firstPerson.setAddress(firstAddress);

        AddressDto secondAddress = new AddressDto();
        secondAddress.setId(2L);
        secondAddress.setAddress("Street 1245");
        secondAddress.setCity("Toulouse");
        secondAddress.setCountry("FRANCE");
        secondAddress.setPostalCode("123Ft-PL");

        EnterpriseDto secondEnterprise = new EnterpriseDto();
        secondEnterprise.setName("YT-76POL");
        secondEnterprise.setTaxNumber("PLO-7890");
        secondEnterprise.setLocalAddress(secondAddress);

        PersonDto secondPerson = new PersonDto();
        secondPerson.setId(1L);
        secondPerson.setName("Alex");
        secondPerson.setLastname("Ferguson");
        secondPerson.setLocalAddress(secondAddress);
        secondPerson.setPost(PersonDto.PostEnum.HRD);
        secondPerson.setEnterpriseId(firstEnterprise.getId());
        secondPerson.setLocalAddress(secondAddress);
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstPerson));
        //WHEN
        personService.updatePerson(1L, secondPerson);
        //THEN
        Mockito.verify(personRepository).save(personArgumentCaptor.capture());
        Person personUpdated = personArgumentCaptor.getValue();
        Assertions.assertEquals(secondPerson.getId(), personUpdated.getId());
        Assertions.assertEquals(secondPerson.getName(), personUpdated.getName());
        Assertions.assertEquals(secondPerson.getLastname(), personUpdated.getLastName());
        Assertions.assertEquals(secondPerson.getLocalAddress(), personUpdated.getAddress());
        Assertions.assertEquals(secondPerson.getPost().toString(), personUpdated.getPost().toString());
        Assertions.assertEquals(secondPerson.getEnterpriseId(), personUpdated.getEnterprise().getId());


    }

    @Test
    void GIVEN_Person_saved_WHEN_getPersonById_THEN_Should_return_Person() {
        //GIVEN
        Address firstAddress = new Address();
        firstAddress.setId(1L);
        firstAddress.setAddress("Street liberty");
        firstAddress.setCity("Raed");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("8500");

        Enterprise firstEnterprise = new Enterprise();
        firstEnterprise.setName("Carton-tn");
        firstEnterprise.setTaxNumber("KART-567-YT");
        firstEnterprise.setAddress(firstAddress);

        Person firstPerson = new Person();
        firstPerson.setId(1L);
        firstPerson.setName("Alex");
        firstPerson.setLastName("Ferguson");
        firstPerson.setPost(Post.EMPLOYEE);
        firstPerson.setAddress(firstAddress);
        firstPerson.setEnterprise(firstEnterprise);
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstPerson));
        //WHEN
        PersonDto personDto = personService.findPersonById(1L);
        //THEN
        Assertions.assertEquals(firstPerson.getId(), personDto.getId());
        Assertions.assertEquals(firstPerson.getName(), personDto.getName());
        Assertions.assertEquals(firstPerson.getLastName(), personDto.getLastname());
        Assertions.assertEquals(firstPerson.getPost().toString(), personDto.getPost().toString());
        Assertions.assertEquals(firstPerson.getAddress().getId(), personDto.getLocalAddress().getId());
        Assertions.assertEquals(firstPerson.getAddress().getAddress(), personDto.getLocalAddress().getAddress());
        Assertions.assertEquals(firstPerson.getAddress().getCity(), personDto.getLocalAddress().getCity());
        Assertions.assertEquals(firstPerson.getAddress().getCountry(), personDto.getLocalAddress().getCountry());
        Assertions.assertEquals(firstPerson.getAddress().getPostalCode(), personDto.getLocalAddress().getPostalCode());
    }

    @Test
    void GIVEN_Id_Enterprise_WHEN_deleteEnterpriseById_tHEN_SHOULD_delete_from_database() {
        //GIVEN
        Address address = new Address();
        address.setId(2L);
        address.setAddress("Street Ibn Zohar");
        address.setCity("Reads");
        address.setCountry("Tunisia");
        address.setPostalCode("1240");

        Enterprise enterprise = new Enterprise();
        enterprise.setId(2L);
        enterprise.setName("Fasten-Yt");
        enterprise.setTaxNumber("FT4531-RT");
        enterprise.setAddress(address);

        Person firstPerson = new Person();
        firstPerson.setId(1L);
        firstPerson.setName("Alex");
        firstPerson.setLastName("Ferguson");
        firstPerson.setPost(Post.EMPLOYEE);
        firstPerson.setAddress(address);
        firstPerson.setEnterprise(enterprise);
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstPerson));
        //WHEN
        personService.deletePerson(1L);

        //THEN
        Mockito.verify(personRepository).delete(personArgumentCaptor.capture());
        Person personDeleted = personArgumentCaptor.getValue();
        Assertions.assertEquals(personDeleted.getId(), firstPerson.getId());
        Assertions.assertEquals(personDeleted.getName(), firstPerson.getName());
        Assertions.assertEquals(personDeleted.getLastName(), firstPerson.getLastName());
        Assertions.assertEquals(personDeleted.getAddress().getId(), firstPerson.getAddress().getId());


    }

}
