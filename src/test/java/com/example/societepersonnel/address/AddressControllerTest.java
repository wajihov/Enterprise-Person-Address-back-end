package com.example.societepersonnel.address;

import com.example.societepersonnel.core.utils.JsonUtils;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.dto.AddressDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        addressDto.setId(1L);
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
                .andExpect(content().string(JsonUtils.asJsonString(addressDto)))
                .andExpect(jsonPath("$.address").value("Avenue Farhad Hatched"));
    }

    @Test
    void GIVEN_addressId_WHEN_deleteAddressById_THEN_should_delete_address_from_database() throws Exception {
        //GIVEN
        Long addressId = 1l;
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Avenue Farhad Hatched");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("2001");
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
        addressDto.setId(5L);
        addressDto.setAddress("Rue Alain Savare");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("6020");
        Mockito.when(addressService.updateAddress(Mockito.anyLong(), Mockito.any())).thenReturn(addressDto);
        //WHEN && THEN
        mockMvc.perform(put("/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(addressDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().string(JsonUtils.asJsonString(addressDto)))
                .andExpect(jsonPath("$.address").value("Rue Alain Savare"));
    }

    @Test
    void GIVEN_addressId_WHEN_getAddressById_THEN_should_get_address_from_database() throws Exception {
        //GIVEN
        Long addressId = 2l;
        AddressDto addressDto = new AddressDto();
        addressDto.setId(2L);
        addressDto.setAddress("Rue Alain Savare");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("6020");

        AddressDto secondAddressDto = new AddressDto();
        secondAddressDto.setId(3L);
        secondAddressDto.setAddress("Rue Ali Data");
        secondAddressDto.setCity("Nabeel");
        secondAddressDto.setCountry("Tunisia");
        secondAddressDto.setPostalCode("5600");

        Mockito.when(addressService.findAddressById(Mockito.anyLong())).thenReturn(addressDto);
        // WHEN && THEN
        mockMvc.perform(get("/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(JsonUtils.asJsonString(addressDto)));
    }

    @Test
    void GIVEN_addresses_WHEN_getAddresses_THEN_should_get_addresses_from_database() throws Exception {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Rue Alain Savare");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("6020");

        AddressDto secondAddressDto = new AddressDto();
        secondAddressDto.setId(2L);
        secondAddressDto.setAddress("Rue Ali Data");
        secondAddressDto.setCity("Nabeel");
        secondAddressDto.setCountry("Tunisia");
        secondAddressDto.setPostalCode("5600");
        List<AddressDto> addressDtoList = new ArrayList<AddressDto>() {
            {
                add(addressDto);
                add(secondAddressDto);
            }
        };
        Mockito.when(addressService.listAddressDto()).thenReturn(addressDtoList);
        //WHEN && THEN
        mockMvc.perform(get("/addresses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(JsonUtils.asJsonString(addressDtoList)));
    }
}
