package com.example.societepersonnel.person;

import com.example.societepersonnel.core.utils.JsonUtils;
import com.example.societepersonnel.domain.person.PersonService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.PersonDto;
import org.hamcrest.core.IsAnything;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Street Ibn Hani");
        addressDto.setCity("Korea");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("8070");

        PersonDto personDto = new PersonDto();
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
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_PersonDto_WHEN_updatePerson_THEN_SHOULD_update_person() throws Exception {
        //GIVEN
        Long personId = 1L;
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Street Ibn Hani");
        addressDto.setCity("Korea");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("8070");

        PersonDto personDto = new PersonDto();
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
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void
    GIVEN_personId_WHEN_deletePersonById_THEN_should_delete_person_from_database() throws Exception {
        //GIVEN
        Long persontId = 5l;
        //WHEN && THEN
        mockMvc.perform(delete("/persons/" + persontId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_personId_WHEN_getPersonById_THEN_should_get_person_from_database() throws Exception {
        //GIVEN
        Long personId = 5l;
        mockMvc.perform(get("/persons/" + personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_persons_WHEN_getAllPersons_THEN_should_get_AllPersons_from_database() throws Exception {
        //WHEN && THEN
        mockMvc.perform(get("/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }
}
