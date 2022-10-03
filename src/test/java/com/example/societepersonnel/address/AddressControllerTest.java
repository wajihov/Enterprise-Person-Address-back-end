package com.example.societepersonnel.address;

import com.example.societepersonnel.core.utils.JsonUtils;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.dto.AddressDto;
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
public class AddressControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    void GIVEN_addressDto_WHEN_createAddress_THEN_SHOULD_Get_Response_OK() throws Exception {
        // GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Avenue Farhad Hatched");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");
        Mockito.when(addressService.createAddress(Mockito.any())).thenReturn(addressDto);
        //WHEN && THEN
        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(addressDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_addressId_WHEN_deleteAddressById_THEN_should_delete_address_from_database() throws Exception {
        //GIVEN
        Long addressId = 5l;
        //WHEN && THEN
        mockMvc.perform(delete("/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void GIVEN_addressDto_WHEN_updateAddress_THEN_should_update_Address() throws Exception {
        //GIVEN
        Long addressId = 5l;
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress("Rue Alain Savari");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("6020");

        Mockito.when(addressService.updateAddress(Mockito.anyLong(), Mockito.any())).thenReturn(addressDto);
        //WHEN && THEN
        mockMvc.perform(put("/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(addressDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_addressId_WHEN_getAddressById_THEN_should_get_address_from_database() throws Exception {
        //GIVEN
        Long addressId = 2l;
        mockMvc.perform(get("/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }

    @Test
    void GIVEN_addresses_WHEN_getAddresses_THEN_should_get_addresses_from_database() throws Exception {
        //WHEN && THEN
        mockMvc.perform(get("/addresses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(IsAnything.anything()));
    }
}
