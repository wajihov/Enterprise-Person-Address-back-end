package com.example.societepersonnel.person;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.utils.StringUtils;
import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.enterprise.EnterpriseMapper;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.domain.person.*;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import com.example.societepersonnel.dto.PersonDto;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private PersonMapper personMapper;
    @MockBean
    private AddressService addressService;
    @MockBean
    private EnterpriseMapper enterpriseMapper;
    @MockBean
    private EnterpriseService enterpriseService;

    @Autowired
    private PersonService personService;
    @Autowired
    private AddressMapper addressMapper;

    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> personIdArgumentCaptor;

    @Test
    void GIVEN_PersonDto_WHEN_CreatePerson_THEN_Should_save_on_database() {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(2L);
        addressDto.setAddress("Street Ibn Addon");
        addressDto.setCity("Ariana");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("5230");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1L);
        enterpriseDto.setName("arena");
        enterpriseDto.setTaxNumber("768-TY");
        enterpriseDto.setLocalAddress(addressDto);

        var enterprise = new Enterprise();
        enterprise.setId(enterpriseDto.getId());
        enterprise.setName(enterpriseDto.getName());
        enterprise.setTaxNumber(enterpriseDto.getTaxNumber());
        enterprise.setAddress(addressMapper.toEntity(enterpriseDto.getLocalAddress()));

        var personDto = new PersonDto();
        personDto.setId(4L);
        personDto.setName("Hakim");
        personDto.setLastname("Hari");
        personDto.setLocalAddress(addressDto);
        personDto.setPost(PersonDto.PostEnum.valueOf("HRD"));
        personDto.setEnterpriseId(enterpriseDto.getId());

        var person = new Person();
        person.setId(personDto.getId());
        person.setName(personDto.getName());
        person.setLastName(personDto.getLastname());
        person.setAddress(addressMapper.toEntity(personDto.getLocalAddress()));
        person.setPost(Post.HRD);
        person.setEnterprise(enterprise);

        Mockito.when(addressService.createAddress(Mockito.any())).thenReturn(addressDto);
        Mockito.when(enterpriseService.findEnterpriseById(Mockito.anyLong())).thenReturn(enterpriseDto);
        Mockito.when(enterpriseMapper.toEntity(Mockito.any())).thenReturn(enterprise);
        Mockito.when(personMapper.toEntity(personDto, addressDto)).thenReturn(person);
        Mockito.when(personRepository.save(Mockito.any())).thenReturn(person);
        Mockito.when(personMapper.toDto(Mockito.any())).thenReturn(personDto);
        //WHEN
        personDto = personService.createPerson(personDto);
        //THEN
        Mockito.verify(personRepository).save(personArgumentCaptor.capture());
        var personCaptor = personArgumentCaptor.getValue();
        Assertions.assertEquals(personDto.getId(), personCaptor.getId());
        Assertions.assertEquals(personDto.getName(), personCaptor.getName());
        Assertions.assertEquals(personDto.getLastname(), personCaptor.getLastName());
        Assertions.assertEquals(personDto.getPost().toString(), personCaptor.getPost().toString());
        Assertions.assertEquals(personDto.getLocalAddress().getId(), personCaptor.getAddress().getId());
        Assertions.assertEquals(personDto.getLocalAddress().getAddress(), personCaptor.getAddress().getAddress());
        Assertions.assertEquals(personDto.getLocalAddress().getCity(), personCaptor.getAddress().getCity());
        Assertions.assertEquals(personDto.getLocalAddress().getCountry(), personCaptor.getAddress().getCountry());
        Assertions.assertEquals(personDto.getLocalAddress().getPostalCode(), personCaptor.getAddress().getPostalCode());
        Assertions.assertEquals(personDto.getEnterpriseId(), person.getEnterprise().getId());
    }

    @Test
    void GIVEN_Person_saved_WHEN_getAllPersons_THEN_Should_return_that_2_Persons() {
        //GIVEN
        var firstAddress = new Address();
        firstAddress.setId(2L);
        firstAddress.setAddress("Rue El Kolas");
        firstAddress.setCity("La Manitoba");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("3009");

        var firstAddressDto = new AddressDto();
        firstAddressDto.setId(firstAddress.getId());
        firstAddressDto.setAddress(firstAddress.getAddress());
        firstAddressDto.setCity(firstAddress.getCity());
        firstAddressDto.setCountry(firstAddress.getCountry());
        firstAddressDto.setPostalCode(firstAddress.getPostalCode());

        var firstEnterprise = new Enterprise();
        firstEnterprise.setId(1L);
        firstEnterprise.setName("Haziest-YU");
        firstEnterprise.setTaxNumber("OPT-675");
        firstEnterprise.setAddress(firstAddress);

        var firstEnterpriseDto = new EnterpriseDto();
        firstEnterpriseDto.setId(firstEnterprise.getId());
        firstEnterpriseDto.setName(firstEnterprise.getName());
        firstEnterpriseDto.setTaxNumber(firstEnterprise.getTaxNumber());
        firstEnterpriseDto.setLocalAddress(firstAddressDto);

        var secondAddress = new Address();
        secondAddress.setId(3L);
        secondAddress.setAddress("Street Of Gables");
        secondAddress.setCity("City Chara");
        secondAddress.setCountry("Saguaro");
        secondAddress.setPostalCode("852-KL");

        var secondAddressDto = new AddressDto();
        secondAddressDto.setId(secondAddress.getId());
        secondAddressDto.setAddress("Street Of Gables");
        secondAddressDto.setCity("City Chara");
        secondAddressDto.setCountry("Saguaro");
        secondAddressDto.setPostalCode("852-KL");

        var firstPerson = new Person();
        firstPerson.setId(1L);
        firstPerson.setName("Alex");
        firstPerson.setLastName("Ferguson");
        firstPerson.setPost(Post.EMPLOYEE);
        firstPerson.setAddress(firstAddress);
        firstPerson.setEnterprise(firstEnterprise);
        firstPerson.setAddress(firstAddress);

        var firstPersonDto = new PersonDto();
        firstPersonDto.setId(firstPerson.getId());
        firstPersonDto.setName(firstPerson.getName());
        firstPersonDto.setLastname(firstPerson.getLastName());
        firstPersonDto.setPost(PersonDto.PostEnum.EMPLOYEE);
        firstPersonDto.setEnterpriseId(firstPerson.getEnterprise().getId());
        firstPersonDto.setLocalAddress(firstAddressDto);

        var secondPerson = new Person();
        secondPerson.setId(1L);
        secondPerson.setName("Alex");
        secondPerson.setLastName("Ferguson");
        secondPerson.setAddress(firstAddress);
        secondPerson.setPost(Post.PRESIDENT);
        secondPerson.setEnterprise(firstEnterprise);
        secondPerson.setAddress(secondAddress);

        var secondPersonDto = new PersonDto();
        secondPersonDto.setId(secondPerson.getId());
        secondPersonDto.setName(secondPerson.getName());
        secondPersonDto.setLastname(secondPerson.getLastName());
        secondPersonDto.setPost(PersonDto.PostEnum.PRESIDENT);
        secondPersonDto.setEnterpriseId(secondPerson.getEnterprise().getId());
        secondPersonDto.setLocalAddress(secondAddressDto);

        List<Person> persons = new ArrayList<Person>() {
            {
                add(firstPerson);
                add(secondPerson);
            }
        };

        List<PersonDto> personDtoList = new ArrayList<PersonDto>() {
            {
                add(firstPersonDto);
                add(secondPersonDto);
            }
        };
        Mockito.when(personRepository.findAll()).thenReturn(persons);
        Mockito.when(personMapper.toDtoList(Mockito.any())).thenReturn(personDtoList);
        //WHEN
        List<PersonDto> personList = personService.findPersons();
        //THEN
        Assertions.assertEquals(2, personList.size());
        Assertions.assertEquals(personDtoList.size(), personList.size());
        for (int i = 0; i < personDtoList.size(); i++) {
            Assertions.assertEquals(personList.get(i).getId(), persons.get(i).getId());
            Assertions.assertEquals(personList.get(i).getName(), persons.get(i).getName());
            Assertions.assertEquals(personList.get(i).getLastname(), persons.get(i).getLastName());
            Assertions.assertEquals(personList.get(i).getPost().toString(), persons.get(i).getPost().toString());
            Assertions.assertEquals(personList.get(i).getLocalAddress().getId(), persons.get(i).getAddress().getId());
            Assertions.assertEquals(personList.get(i).getLocalAddress().getCity(), persons.get(i).getAddress().getCity());
            Assertions.assertEquals(personList.get(i).getLocalAddress().getAddress(), persons.get(i).getAddress().getAddress());
            Assertions.assertEquals(personList.get(i).getLocalAddress().getCountry(), persons.get(i).getAddress().getCountry());
            Assertions.assertEquals(personList.get(i).getLocalAddress().getPostalCode(), persons.get(i).getAddress().getPostalCode());
            Assertions.assertEquals(personList.get(i).getEnterpriseId(), persons.get(i).getEnterprise().getId());
        }
    }

    @Test
    void GIVEN_Person_saved_WHEN_getAllPersons_THEN_Should_return_EnterprisePersonException() {
        //GIVEN
        List<Person> personList = Collections.emptyList();
        Mockito.when(personRepository.findAll()).thenReturn(personList);
        //THEN
        EnterprisePersonException e = Assertions.assertThrows(EnterprisePersonException.class, () ->
                personService.findPersons());
        Assertions.assertEquals("PERSONS NOT FOUND", e.getMessage());

    }

    @Test
    void GIVEN_Person_update_WHEN_updatePersons_THEN_Should_return_EnterprisePersonException() {
        var personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setName("Alex");
        personDto.setLastname("Ferguson");
        personDto.setPost(PersonDto.PostEnum.EMPLOYEE);
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Tunis");
        personDto.setLocalAddress(addressDto);
       // Mockito.when(addressMapper.toEntity(Mockito.any())).thenReturn(new Address());
        try (MockedStatic<StringUtils> utilsMockedStatic = Mockito.mockStatic(StringUtils.class)) {
            utilsMockedStatic.when(() -> StringUtils.isNotNullOrNotEmpty(personDto.getLocalAddress().getId())).thenReturn(false);
        }
        //THEN
        EnterprisePersonException e = Assertions.assertThrows(EnterprisePersonException.class, () ->
                personService.updatePerson(personDto.getId(), personDto));
        Assertions.assertEquals("ADDRESS NOT VALID", e.getMessage());

    }

    @Test
    void GIVEN_Person_update_WHEN_updatePersons_THEN_Should_update_that_person_on_Database() {
        //GIVEN
        var firstAddressDto = new AddressDto();
        firstAddressDto.setId(1L);
        firstAddressDto.setAddress("Rue El Kolas");
        firstAddressDto.setCity("La Manitoba");
        firstAddressDto.setCountry("Tunisia");
        firstAddressDto.setPostalCode("3009");

        var firstEnterpriseDto = new EnterpriseDto();
        firstEnterpriseDto.setId(2L);
        firstEnterpriseDto.setName("Haziest-YU");
        firstEnterpriseDto.setTaxNumber("OPT-675");
        firstEnterpriseDto.setLocalAddress(firstAddressDto);

        var firstPersonDto = new PersonDto();
        firstPersonDto.setId(3L);
        firstPersonDto.setName("Alex");
        firstPersonDto.setLastname("Ferguson");
        firstPersonDto.setPost(PersonDto.PostEnum.EMPLOYEE);
        firstPersonDto.setLocalAddress(firstAddressDto);
        firstPersonDto.setEnterpriseId(firstEnterpriseDto.getId());

        var secondAddressDto = new AddressDto();
        secondAddressDto.setId(4L);
        secondAddressDto.setAddress("Street 1245");
        secondAddressDto.setCity("Toulouse");
        secondAddressDto.setCountry("FRANCE");
        secondAddressDto.setPostalCode("123Ft-PL");

        var secondEnterpriseDto = new EnterpriseDto();
        secondEnterpriseDto.setId(5L);
        secondEnterpriseDto.setName("YT-76POL");
        secondEnterpriseDto.setTaxNumber("PLO-7890");
        secondEnterpriseDto.setLocalAddress(secondAddressDto);

        var secondEnterprise = new Enterprise();
        secondEnterprise.setId(secondEnterpriseDto.getId());
        secondEnterprise.setName(secondEnterpriseDto.getName());
        secondEnterprise.setTaxNumber(secondEnterpriseDto.getTaxNumber());
        secondEnterprise.setAddress(addressMapper.toEntity(secondEnterpriseDto.getLocalAddress()));

        var secondPersonDto = new PersonDto();
        secondPersonDto.setId(6L);
        secondPersonDto.setName("Alex");
        secondPersonDto.setLastname("Ferguson");
        secondPersonDto.setLocalAddress(secondAddressDto);
        secondPersonDto.setPost(PersonDto.PostEnum.HRD);
        secondPersonDto.setEnterpriseId(secondEnterpriseDto.getId());

        var secondPerson = new Person();
        secondPerson.setId(secondPersonDto.getId());
        secondPerson.setName(secondPersonDto.getName());
        secondPerson.setLastName(secondPersonDto.getLastname());
        secondPerson.setPost(Post.HRD);
        secondPerson.setAddress(addressMapper.toEntity(secondAddressDto));

        Mockito.when(addressService.updateAddress(Mockito.anyLong(), Mockito.any())).thenReturn(secondAddressDto);
        Mockito.when(enterpriseService.findEnterpriseById(Mockito.anyLong())).thenReturn(secondEnterpriseDto);
        Mockito.when(enterpriseMapper.toEntity(Mockito.any())).thenReturn(secondEnterprise);
        Mockito.when(personMapper.toEntity(Mockito.any(), Mockito.any())).thenReturn(secondPerson);
        Mockito.when(personRepository.save(Mockito.any())).thenReturn(secondPerson);
        Mockito.when(personMapper.toDto(Mockito.any())).thenReturn(secondPersonDto);
        //WHEN
        secondPersonDto = personService.updatePerson(6L, firstPersonDto);
        //THEN
        Mockito.verify(personRepository).save(personArgumentCaptor.capture());
        var personUpdated = personArgumentCaptor.getValue();
        Assertions.assertEquals(secondPersonDto.getId(), personUpdated.getId());
        Assertions.assertEquals(secondPersonDto.getName(), personUpdated.getName());
        Assertions.assertEquals(secondPersonDto.getLastname(), personUpdated.getLastName());
        Assertions.assertEquals(secondPersonDto.getLocalAddress().getId(), personUpdated.getAddress().getId());
        Assertions.assertEquals(secondPersonDto.getLocalAddress().getAddress(), personUpdated.getAddress().getAddress());
        Assertions.assertEquals(secondPersonDto.getLocalAddress().getCity(), personUpdated.getAddress().getCity());
        Assertions.assertEquals(secondPersonDto.getLocalAddress().getCountry(), personUpdated.getAddress().getCountry());
        Assertions.assertEquals(secondPersonDto.getLocalAddress().getPostalCode(), personUpdated.getAddress().getPostalCode());
        Assertions.assertEquals(secondPersonDto.getPost().toString(), personUpdated.getPost().toString());
        Assertions.assertEquals(secondPersonDto.getEnterpriseId(), personUpdated.getEnterprise().getId());
    }

    @Test
    void GIVEN_Person_saved_WHEN_getPersonById_THEN_Should_return_Person() {
        //GIVEN
        var personId = 1L;
        var firstAddress = new Address();
        firstAddress.setId(1L);
        firstAddress.setAddress("Street liberty");
        firstAddress.setCity("Raed");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("8500");

        var firstEnterprise = new Enterprise();
        firstEnterprise.setName("Carton-tn");
        firstEnterprise.setTaxNumber("KART-567-YT");
        firstEnterprise.setAddress(firstAddress);

        var firstPerson = new Person();
        firstPerson.setId(1L);
        firstPerson.setName("Alex");
        firstPerson.setLastName("Ferguson");
        firstPerson.setPost(Post.EMPLOYEE);
        firstPerson.setAddress(firstAddress);
        firstPerson.setEnterprise(firstEnterprise);

        var secondPerson = new PersonDto();
        secondPerson.setId(firstPerson.getId());
        secondPerson.setName(firstPerson.getName());
        secondPerson.setLastname(firstPerson.getLastName());
        secondPerson.setPost(PersonDto.PostEnum.EMPLOYEE);
        secondPerson.setLocalAddress(addressMapper.toDto(firstAddress));
        secondPerson.setEnterpriseId(firstEnterprise.getId());


        firstPerson.setEnterprise(firstEnterprise);
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstPerson));
        Mockito.when(personMapper.toDto(firstPerson)).thenReturn(secondPerson);
        //WHEN
        var personDto = personService.findPersonById(personId);
        //THEN
        Assertions.assertEquals(secondPerson.getId(), personDto.getId());
        Assertions.assertEquals(secondPerson.getName(), personDto.getName());
        Assertions.assertEquals(secondPerson.getLastname(), personDto.getLastname());
        Assertions.assertEquals(secondPerson.getPost().toString(), personDto.getPost().toString());
        Assertions.assertEquals(secondPerson.getLocalAddress().getId(), personDto.getLocalAddress().getId());
        Assertions.assertEquals(secondPerson.getLocalAddress().getAddress(), personDto.getLocalAddress().getAddress());
        Assertions.assertEquals(secondPerson.getLocalAddress().getCity(), personDto.getLocalAddress().getCity());
        Assertions.assertEquals(secondPerson.getLocalAddress().getCountry(), personDto.getLocalAddress().getCountry());
        Assertions.assertEquals(secondPerson.getLocalAddress().getPostalCode(), personDto.getLocalAddress().getPostalCode());
        Assertions.assertEquals(secondPerson.getEnterpriseId(), personDto.getEnterpriseId());
    }

    @Test
    void GIVEN_Id_Enterprise_WHEN_deleteEnterpriseById_tHEN_SHOULD_delete_from_database() {
        //GIVEN
        var personId = 3L;
        var address = new Address();
        address.setId(1L);
        address.setAddress("Street Ibn Zohar");
        address.setCity("Reads");
        address.setCountry("Tunisia");
        address.setPostalCode("1240");

        var enterprise = new Enterprise();
        enterprise.setId(2L);
        enterprise.setName("Fasten-Yt");
        enterprise.setTaxNumber("FT4531-RT");
        enterprise.setAddress(address);

        var firstPerson = new Person();
        firstPerson.setId(3L);
        firstPerson.setName("Alex");
        firstPerson.setLastName("Ferguson");
        firstPerson.setPost(Post.EMPLOYEE);
        firstPerson.setAddress(address);
        firstPerson.setEnterprise(enterprise);
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstPerson));
        //WHEN
        personService.deletePerson(personId);
        //THEN
        Mockito.verify(personRepository).deleteById(personIdArgumentCaptor.capture());
        var personDeleted = personIdArgumentCaptor.getValue();
        Assertions.assertEquals(personDeleted, firstPerson.getId());
    }

    @Test
    void GIVEN_Person_saved_WHEN_getPersonById_WHEN_toFindPersonById_THEN_SHOULD_return_EnterprisePersonException() {
        //GIVEN && WHEN
        var person = new Person();
        person.setId(null);
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        EnterprisePersonException e = Assertions.assertThrows(EnterprisePersonException.class, () -> {
            personService.findPersonById(null);
        });
        Assertions.assertEquals("PERSON NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_Person_saved_WHEN_getPersonById_WHEN_toDeleteById_THEN_SHOULD_return_EnterprisePersonException() {
        //GIVEN && WHEN
        var person = new Person();
        person.setId(null);
        Mockito.when(personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(person));
        EnterprisePersonException e = Assertions.assertThrows(EnterprisePersonException.class, () -> {
            personService.deletePerson(null);
        });
        Assertions.assertEquals("PERSON NOT FOUND", e.getMessage());
    }

}
