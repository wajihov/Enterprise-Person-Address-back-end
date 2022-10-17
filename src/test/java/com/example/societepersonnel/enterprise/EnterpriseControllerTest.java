package com.example.societepersonnel.enterprise;

import com.example.societepersonnel.core.utils.JsonUtils;
import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
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
        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("Anika_jade");
        enterpriseDto.setTaxNumber("tar-6548");

        var addressDto = new AddressDto();
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
                .andExpect(content().string(JsonUtils.asJsonString(enterpriseDto)))
                .andExpect(jsonPath("$.name").value("Anika_jade"))
                .andExpect(jsonPath("$.tax_number").value("tar-6548"));
    }

    @Test
    void GIVEN_enterpriseId_WHEN_deleteEnterpriseById_THEN_SHOULD_deleted_justification_from_database() throws Exception {
        //GIVEN
        var enterpriseId = 1l;
        //WHEN && THEN
        mockMvc.perform(delete("/enterprises/" + enterpriseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void GIVEN_enterpriseDto_WHEN_updateEnterpriseById_THEN_SHOULD_update_enterprise() throws Exception {
        //GIVEN
        var enterpriseId = 5l;
        var addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Street 5 Jinja Hobbits");
        addressDto.setCity("London");
        addressDto.setCountry("ENGLAND");
        addressDto.setPostalCode("15485");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("MIKA-Tn");
        enterpriseDto.setTaxNumber("ERT54-YU");
        enterpriseDto.setLocalAddress(addressDto);

        Mockito.when(enterpriseService.updateEnterprise(Mockito.anyLong(), Mockito.any())).thenReturn(enterpriseDto);
        //WHEN && THEN
        mockMvc.perform(put("/enterprises/" + enterpriseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(enterpriseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MIKA-Tn"))
                .andExpect(jsonPath("$.tax_number").value("ERT54-YU"))
                .andExpect(content().string(JsonUtils.asJsonString(enterpriseDto)));
    }

    @Test
    void GIVEN_enterpriseId_WHEN_getEnterpriseById_THEN_should_get_enterprise_from_database() throws Exception {
        //GIVEN
        var enterpriseId = 5l;
        var addressDto = new AddressDto();
        addressDto.setId(1l);
        addressDto.setAddress("Street 5 Jinja Hobbits");
        addressDto.setCity("London");
        addressDto.setCountry("ENGLAND");
        addressDto.setPostalCode("15485");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1l);
        enterpriseDto.setName("Fortuna-Tn");
        enterpriseDto.setTaxNumber("KYO976-34");
        enterpriseDto.setLocalAddress(addressDto);

        Mockito.when(enterpriseService.findEnterpriseById(Mockito.any())).thenReturn(enterpriseDto);
        mockMvc.perform(get("/enterprises/" + enterpriseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fortuna-Tn"))
                .andExpect(jsonPath("$.tax_number").value("KYO976-34"))
                .andExpect(content().string(JsonUtils.asJsonString(enterpriseDto)));
    }

    @Test
    void GIVEN_enterprises_WHEN_getEnterprises_THEN_should_get_enterprises_from_database() throws Exception {
        //GIVEN
        var firstAddressDto = new AddressDto();
        firstAddressDto.setId(1l);
        firstAddressDto.setAddress("Street 5 Jinja Hobbits");
        firstAddressDto.setCity("London");
        firstAddressDto.setCountry("ENGLAND");
        firstAddressDto.setPostalCode("15485");

        var firstEnterpriseDto = new EnterpriseDto();
        firstEnterpriseDto.setId(1l);
        firstEnterpriseDto.setName("Fortuna-Tn");
        firstEnterpriseDto.setTaxNumber("KYO976-34");
        firstEnterpriseDto.setLocalAddress(firstAddressDto);

        var secondAddressDto = new AddressDto();
        secondAddressDto.setId(2L);
        secondAddressDto.setAddress("Street Ibn Zohar");
        secondAddressDto.setCity("Reads");
        secondAddressDto.setCountry("Tunisia");
        secondAddressDto.setPostalCode("1235");

        var secondEnterprise = new EnterpriseDto();
        secondEnterprise.setId(2L);
        secondEnterprise.setName("Fasten-Yt");
        secondEnterprise.setTaxNumber("FT4531-RT");
        secondEnterprise.setLocalAddress(secondAddressDto);

        List<EnterpriseDto> enterpriseDtoList = new ArrayList<EnterpriseDto>() {
            {
                add(firstEnterpriseDto);
                add(secondEnterprise);
            }
        };
        Mockito.when(enterpriseService.findEnterprises()).thenReturn(enterpriseDtoList);
        //WHEN && THEN
        mockMvc.perform(get("/enterprises/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(JsonUtils.asJsonString(enterpriseDtoList)));
    }
}
