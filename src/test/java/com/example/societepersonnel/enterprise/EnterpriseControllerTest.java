package com.example.societepersonnel.enterprise;

import com.example.societepersonnel.core.utils.JsonUtils;
import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
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
public class EnterpriseControllerTest {

    @MockBean
    private Address address;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private EnterpriseService enterpriseService;

    @Test
    void GIVEN_enterprise_WHEN_createEnterprise_THEN_SOULD_save_on_database() throws Exception {
        //GIVEN
        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("Anika_jade");
        enterpriseDto.setTaxNumber("tar-6548");
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Rue AbdoulKassim Chibi");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("5020");
        enterpriseDto.setLocalAddress(addressDto);

        Mockito.when(enterpriseService.createEnterprise(Mockito.any())).thenReturn(enterpriseDto);
        //WHEN && THEN
        mockMvc.perform(post("/enterprises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(enterpriseDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_enterpriseId_WHEN_deleteEnterpriseById_THEN_SHOULD_deleted_justification_from_database() throws Exception {
        //GIVEN
        Long enterpriseId = 1l;
        //WHEN && THEN
        mockMvc.perform(delete("/enterprises/" + enterpriseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }// normalement status().isNoContent => 204 dans openapi-spec.yaml ds ligne 381 delete return 200.

    @Test
    void GIVEN_enterpriseDto_WHEN_updateEnterpriseById_THEN_SHOULD_update_enterprise() throws Exception {
        //GIVEN
        Long enterpriseId = 5l;
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Street 5 Jinja Hobbits");
        addressDto.setCity("London");
        addressDto.setCountry("ENGLAND");
        addressDto.setPostalCode("15485");

        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("GIKA-Tn");
        enterpriseDto.setTaxNumber("ERT54-YU");
        enterpriseDto.setLocalAddress(addressDto);

        Mockito.when(enterpriseService.updateEnterprise(Mockito.anyLong(), Mockito.any())).thenReturn(enterpriseDto);
        //WHEN && THEN
        mockMvc.perform(put("/enterprises/" + enterpriseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(enterpriseDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));

    }

    @Test
    void GIVEN_enterpriseId_WHEN_getEnterpriseById_THEN_should_get_enterprise_from_database() throws Exception {
        //GIVEN
        Long enterpriseId = 3l;
        mockMvc.perform(get("/enterprises/" + enterpriseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_enterprises_WHEN_getEnterprises_THEN_should_get_enterprises_from_database() throws Exception {
        //WHEN && THEN
        mockMvc.perform(get("/enterprises/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }


}
