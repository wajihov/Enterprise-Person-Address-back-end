package com.example.societepersonnel.enterprise;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.enterprise.EnterpriseMapper;
import com.example.societepersonnel.domain.person.Person;
import com.example.societepersonnel.domain.person.PersonMapper;
import com.example.societepersonnel.domain.person.Post;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EnterpriseMapperTest {

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private AddressMapper addressMapper;
    @MockBean
    private PersonMapper personMapper;

    @Test
    void Given_Enterprise_WHEN_ToEnterpriseDto_THEN_SHOULD_return_EnterpriseDto() {
        //GIVEN
        var address = new Address();
        address.setId(1l);
        address.setAddress("Street Saudi");
        address.setCity("Manchester");
        address.setCountry("England");
        address.setPostalCode("34567");

        var enterprise = new Enterprise();
        enterprise.setId(3L);
        enterprise.setName("Ideal School");
        enterprise.setTaxNumber("TR234-TY");
        enterprise.setAddress(address);

        var person = new Person();
        person.setId(null);
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        enterprise.setPersons(personList);
        //WHEN
        var enterpriseDto = enterpriseMapper.toDto(enterprise);
        //THEN
        Assertions.assertEquals(enterpriseDto.getId(), enterprise.getId());
        Assertions.assertEquals(enterpriseDto.getName(), enterprise.getName());
        Assertions.assertEquals(enterpriseDto.getTaxNumber(), enterprise.getTaxNumber());

        Assertions.assertEquals(enterpriseDto.getLocalAddress().getId(), enterprise.getAddress().getId());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getAddress(), enterprise.getAddress().getAddress());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getCity(), enterprise.getAddress().getCity());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getCountry(), enterprise.getAddress().getCountry());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getPostalCode(), enterprise.getAddress().getPostalCode());

    }

    @Test
    void Given_EnterprisesList_WHEN_ToEnterpriseDtoList_THEN_SHOULD_return_EnterpriseDtoList() {
        //GIVEN
        var address = new Address();
        address.setId(1l);
        address.setAddress("Street Saudi");
        address.setCity("Manchester");
        address.setCountry("England");
        address.setPostalCode("34567");

        var enterprise = new Enterprise();
        enterprise.setId(3L);
        enterprise.setName("Ideal School");
        enterprise.setTaxNumber("TR234-TY");
        enterprise.setAddress(address);

        var secondEnterprise = new Enterprise();
        secondEnterprise.setId(4L);
        secondEnterprise.setName("Go Partner");
        secondEnterprise.setTaxNumber("12345Fgt-78");
        secondEnterprise.setAddress(address);


        var person = new Person();
        person.setId(2L);
        person.setName("Garrido");
        person.setLastName("Gregor");
        person.setPost(Post.EMPLOYEE);
        person.setEnterprise(enterprise);
        person.setAddress(address);

        var secondPerson = new Person();
        secondPerson.setId(4L);
        secondPerson.setName("Jorginho");
        secondPerson.setLastName("Groom");
        secondPerson.setPost(Post.EMPLOYEE);
        secondPerson.setEnterprise(secondEnterprise);
        secondPerson.setAddress(address);

        List<Person> personList = new ArrayList<>();
        personList.add(person);

        enterprise.setPersons(personList);
        List<Person> secondPersonList = new ArrayList<>();
        secondPersonList.add(secondPerson);

        // WHEN
        List<Enterprise> enterpriseList = new ArrayList<>();
        enterpriseList.add(enterprise);
        enterpriseList.add(secondEnterprise);
        List<EnterpriseDto> enterpriseDtoList = enterpriseMapper.toDtoList(enterpriseList);

        //THEN
        for (int i = 0; i < enterpriseDtoList.size(); i++) {
            Assertions.assertEquals(enterpriseDtoList.get(i).getId(), enterpriseList.get(i).getId());
            Assertions.assertEquals(enterpriseDtoList.get(i).getName(), enterpriseList.get(i).getName());
            Assertions.assertEquals(enterpriseDtoList.get(i).getTaxNumber(), enterpriseList.get(i).getTaxNumber());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getId(), enterpriseList.get(i).getAddress().getId());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getAddress(), enterpriseList.get(i).getAddress().getAddress());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getCity(), enterpriseList.get(i).getAddress().getCity());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getCountry(), enterpriseList.get(i).getAddress().getCountry());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getPostalCode(), enterpriseList.get(i).getAddress().getPostalCode());
            for (int j = 0; j < enterpriseDtoList.get(i).getPersons().size(); j++) {
                Assertions.assertEquals(enterpriseDtoList.get(i).getPersons().get(j).getId(), enterpriseList.get(i).getPersons().get(j).getId());
                Assertions.assertEquals(enterpriseDtoList.get(i).getPersons().get(j).getName(), enterpriseList.get(i).getPersons().get(j).getName());
                Assertions.assertEquals(enterpriseDtoList.get(i).getPersons().get(j).getLastname(), enterpriseList.get(i).getPersons().get(j).getLastName());
            }

        }

    }

    @Test
    void Given_enterpriseDto_WHEN_toEntity_THEN_SHOULD_return_enterprise() {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Street Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("Gaeta-EN");
        enterpriseDto.setTaxNumber("345-YUP");
        enterpriseDto.setLocalAddress(addressDto);
        //WHEN
        var enterprise = enterpriseMapper.toEntity(enterpriseDto);
        //THEN
        //Assertions.assertEquals(enterpriseDto.getClass(), EnterpriseDto.class);
        Assertions.assertEquals(enterprise.getId(), enterpriseDto.getId());
        Assertions.assertEquals(enterprise.getName(), enterpriseDto.getName());
        Assertions.assertEquals(enterprise.getTaxNumber(), enterpriseDto.getTaxNumber());

        Assertions.assertEquals(enterprise.getAddress().getId(), enterpriseDto.getLocalAddress().getId());
        Assertions.assertEquals(enterprise.getAddress().getAddress(), enterpriseDto.getLocalAddress().getAddress());
        Assertions.assertEquals(enterprise.getAddress().getCity(), enterpriseDto.getLocalAddress().getCity());
        Assertions.assertEquals(enterprise.getAddress().getCountry(), enterpriseDto.getLocalAddress().getCountry());
        Assertions.assertEquals(enterprise.getAddress().getPostalCode(), enterpriseDto.getLocalAddress().getPostalCode());
    }

    @Test
    void Given_enterpriseDto_and_AddressDto_WHEN_toEntity_THEN_SHOULD_return_enterprise() {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Street Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("Gaeta-EN");
        enterpriseDto.setTaxNumber("345-YUP");
        //WHEN
        var enterprise = enterpriseMapper.toEntity(enterpriseDto, addressDto);
        var address = addressMapper.toEntity(addressDto);
        //THEN
        //Assertions.assertEquals(enterpriseDto.getClass(), EnterpriseDto.class);
        Assertions.assertEquals(enterprise.getId(), enterpriseDto.getId());
        Assertions.assertEquals(enterprise.getName(), enterpriseDto.getName());
        Assertions.assertEquals(enterprise.getTaxNumber(), enterpriseDto.getTaxNumber());

        Assertions.assertEquals(enterprise.getAddress().getId(), address.getId());
        Assertions.assertEquals(enterprise.getAddress().getAddress(), address.getAddress());
        Assertions.assertEquals(enterprise.getAddress().getCity(), address.getCity());
        Assertions.assertEquals(enterprise.getAddress().getCountry(), address.getCountry());
        Assertions.assertEquals(enterprise.getAddress().getPostalCode(), address.getPostalCode());
    }

    @Test
    void GIVEN_enterprise_WHEN_toEnterpriseDto_THEN_should_return_Exception() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            enterpriseMapper.toEntity(null);
        });
        Assertions.assertEquals("ENTERPRISE NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_enterprise_and_Address_WHEN_toEnterpriseDto_THEN_should_return_Exception() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            enterpriseMapper.toEntity(null, null);
        });
        Assertions.assertEquals("ENTERPRISE NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_enterpriseDto_WHEN_toEnterprise_THEN_should_return_Exception() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            enterpriseMapper.toDto(null);
        });
        Assertions.assertEquals("ENTERPRISE NOT FOUND", e.getMessage());
    }

    @Test
    void GIVEN_enterprises_WHEN_toEnterpriseDtoList_THEN_should_return_Exception() {
        //GIVEN & WHEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            enterpriseMapper.toDtoList(null);
        });
        Assertions.assertEquals("ENTERPRISES NOT FOUND", e.getMessage());
    }


}
