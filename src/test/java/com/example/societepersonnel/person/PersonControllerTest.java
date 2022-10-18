package com.example.societepersonnel.person;

import com.example.societepersonnel.core.utils.JsonUtils;
import com.example.societepersonnel.domain.person.PersonService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import com.example.societepersonnel.dto.PersonDto;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class PersonControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void GIVEN_PersonDto_WHEN_createPerson_THEN_SHOULD_Get_Response_OK() throws Exception {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Street Ibn Hani");
        addressDto.setCity("Korea");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("8070");

        var personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setName("Wash");
        personDto.setLastname("Bodied");
        personDto.setPost(PersonDto.PostEnum.valueOf("EMPLOYEE"));
        personDto.setLocalAddress(addressDto);
        personDto.setEnterpriseId(1l);
        Mockito.when(personService.createPerson(Mockito.any())).thenReturn(personDto);
        //WHEN && THEN
        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(personDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Wash"))
                .andExpect(jsonPath("$.lastname").value("Bodied"))
                .andExpect(content().string(JsonUtils.asJsonString(personDto)));
    }

    @Test
    void GIVEN_PersonDto_WHEN_updatePerson_THEN_SHOULD_update_person() throws Exception {
        //GIVEN
        var personId = 1L;
        var addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Street Ibn Hani");
        addressDto.setCity("Korea");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("8070");

        var personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setName("Wash");
        personDto.setLastname("Bodied");
        personDto.setPost(PersonDto.PostEnum.valueOf("EMPLOYEE"));
        personDto.setLocalAddress(addressDto);
        personDto.setEnterpriseId(1l);

        Mockito.when(personService.updatePerson(Mockito.anyLong(), Mockito.any())).thenReturn(personDto);
        //WHEN && THEN
        mockMvc.perform(put("/persons/" + personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(personDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Wash"))
                .andExpect(jsonPath("$.lastname").value("Bodied"))
                .andExpect(content().string(JsonUtils.asJsonString(personDto)));
    }

    @Test
    void
    GIVEN_personId_WHEN_deletePersonById_THEN_should_delete_person_from_database() throws Exception {
        //GIVEN
        var personId = 1L;
        //WHEN && THEN
        mockMvc.perform(delete("/persons/" + personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_personId_WHEN_getPersonById_THEN_should_get_person_from_database() throws Exception {
        //GIVEN
        var personId = 1L;
        var addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Street Ibn Hani");
        addressDto.setCity("Korea");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("8070");

        var personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setName("Wash");
        personDto.setLastname("Bodied");
        personDto.setPost(PersonDto.PostEnum.valueOf("EMPLOYEE"));
        personDto.setLocalAddress(addressDto);
        personDto.setEnterpriseId(1l);
        Mockito.when(personService.findPersonById(Mockito.anyLong())).thenReturn(personDto);

        // WHEN && THEN
        mockMvc.perform(get("/persons/" + personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Wash"))
                .andExpect(jsonPath("$.lastname").value("Bodied"))
                .andExpect(content().string(JsonUtils.asJsonString(personDto)));
    }

    @Test
    void GIVEN_persons_WHEN_getAllPersons_THEN_should_get_AllPersons_from_database() throws Exception {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Street Farhad Hashed");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("Gaeta-EN");
        enterpriseDto.setTaxNumber("345-YUP");
        enterpriseDto.setLocalAddress(addressDto);

        var personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setEnterpriseId(enterpriseDto.getId());
        personDto.setName("Filip");
        personDto.setLastname("Trousers");
        personDto.setLocalAddress(addressDto);
        personDto.setPost(PersonDto.PostEnum.valueOf(("ADMINISTRATOR")));


        var secondAddressDto = new AddressDto();
        secondAddressDto.setId(2L);
        secondAddressDto.setAddress("Avenue Palestine");
        secondAddressDto.setCity("Ariana");
        secondAddressDto.setCountry("Tunisia");
        secondAddressDto.setPostalCode("7800");

        var secondEnterpriseDto = new EnterpriseDto();
        secondEnterpriseDto.setId(2l);
        secondEnterpriseDto.setName("TELETHON");
        secondEnterpriseDto.setTaxNumber("IOP0-98");
        secondEnterpriseDto.setLocalAddress(secondAddressDto);

        var secondPersonDto = new PersonDto();
        secondPersonDto.setId(2L);
        secondPersonDto.setEnterpriseId(secondEnterpriseDto.getId());
        secondPersonDto.setName("Giro");
        secondPersonDto.setLastname("Kimball");
        secondPersonDto.setLocalAddress(secondAddressDto);
        secondPersonDto.setPost(PersonDto.PostEnum.valueOf(("ADMINISTRATOR")));

        List<PersonDto> personDtoList = new ArrayList<PersonDto>() {
            {
                add(personDto);
                add(secondPersonDto);
            }
        };
        Mockito.when(personService.findPersons()).thenReturn(personDtoList);
        // WHEN && THEN
        mockMvc.perform(get("/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(JsonUtils.asJsonString(personDtoList)));
    }
}
