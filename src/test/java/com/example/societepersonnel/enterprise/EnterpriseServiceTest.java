package com.example.societepersonnel.enterprise;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.enterprise.EnterpriseRepository;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
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
public class EnterpriseServiceTest {

    @MockBean
    private EnterpriseRepository enterpriseRepository;
    @Autowired
    private EnterpriseService enterpriseService;
    @Captor
    private ArgumentCaptor<Enterprise> enterpriseArgumentCaptor;

    @Test
    void GIVEN_EnterpriseDto_WHEN_CreateEnterprise_THEN_Should_save_on_database() {
        //GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Street Med 6");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("3452");

        EnterpriseDto enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1L);
        enterpriseDto.setName("Heatmap-Yu");
        enterpriseDto.setTaxNumber("TRP_678-54");
        enterpriseDto.setLocalAddress(addressDto);
        //WHEN
        enterpriseService.createEnterprise(enterpriseDto);
        //THEN
        Mockito.verify(enterpriseRepository).save(enterpriseArgumentCaptor.capture());
        Enterprise enterprise = enterpriseArgumentCaptor.getValue();
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
    void GIVEN_Id_Enterprise_WHEN_deleteEnterpriseById_tHEN_SHOULD_delete_from_database() {
        //GIVEN
        Address address = new Address();
        address.setId(2L);
        address.setAddress("Street Ibn Zohar");
        address.setCity("Reads");
        address.setCountry("Tunisia");
        address.setPostalCode("");

        Enterprise enterprise = new Enterprise();
        enterprise.setId(1L);
        enterprise.setName("Fasten-Yt");
        enterprise.setTaxNumber("FT4531-RT");
        enterprise.setAddress(address);
        Mockito.when(enterpriseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(enterprise));

        //WHEN
        enterpriseService.deleteEnterprise(1L);

        //THEN
        Mockito.verify(enterpriseRepository).delete(enterpriseArgumentCaptor.capture());
        Enterprise enterpriseDeleted = enterpriseArgumentCaptor.getValue();
        Assertions.assertEquals(enterprise.getId(), enterpriseDeleted.getId());
        Assertions.assertEquals(enterprise.getName(), enterpriseDeleted.getName());
        Assertions.assertEquals(enterprise.getTaxNumber(), enterpriseDeleted.getTaxNumber());

        Assertions.assertEquals(enterprise.getAddress().getId(), enterpriseDeleted.getAddress().getId());
        Assertions.assertEquals(enterprise.getAddress().getAddress(), enterpriseDeleted.getAddress().getAddress());
        Assertions.assertEquals(enterprise.getAddress().getCity(), enterpriseDeleted.getAddress().getCity());
        Assertions.assertEquals(enterprise.getAddress().getCountry(), enterpriseDeleted.getAddress().getCountry());
        Assertions.assertEquals(enterprise.getAddress().getPostalCode(), enterpriseDeleted.getAddress().getPostalCode());
    }

    @Test
    void GIVEN_Enterprise_saved_WHEN_getAllEnterprises_THEN_Should_return_that_2_Enterprises() {
        //GIVEN
        Address firstAddress = new Address();
        firstAddress.setAddress("Street Ibn Zohar");
        firstAddress.setCity("Reads");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode(null);

        Enterprise firstEnterprise = new Enterprise();
        firstEnterprise.setName("Fasten-Yt");
        firstEnterprise.setTaxNumber("FT4531-RT");
        firstEnterprise.setAddress(firstAddress);

        Address secondAddress = new Address();
        secondAddress.setAddress("Street 1245");
        secondAddress.setCity("Toulouse");
        secondAddress.setCountry("FRANCE");
        secondAddress.setPostalCode("123Ft-PL");

        Enterprise secondEnterprise = new Enterprise();
        secondEnterprise.setName("YT-76POL");
        secondEnterprise.setTaxNumber("PLO-7890");
        secondEnterprise.setAddress(secondAddress);

        List<Enterprise> enterprises = new ArrayList<Enterprise>() {
            {
                add(firstEnterprise);
                add(secondEnterprise);
            }
        };
        Mockito.when(enterpriseRepository.findAll()).thenReturn(enterprises);
        //WHEN
        List<EnterpriseDto> enterpriseList = enterpriseService.findEnterprises();
        //THEN
        Assertions.assertEquals(2, enterpriseList.size());
    }

    @Test
    void GIVEN_Enterprise_WHEN_updateEnterprise_THEN_Should_update_that_Enterprise_on_Database() {
        //GIVEN
        Address firstAddress = new Address();
        firstAddress.setId(1L);
        firstAddress.setAddress("Street Ibn Zohar");
        firstAddress.setCity("Reads");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("1235");

        Enterprise firstEnterprise = new Enterprise();
        firstEnterprise.setName("Fasten-Yt");
        firstEnterprise.setTaxNumber("FT4531-RT");
        firstEnterprise.setAddress(firstAddress);

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
        Mockito.when(enterpriseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstEnterprise));
        //WHEN
        enterpriseService.updateEnterprise(1L, secondEnterprise);
        //THEN
        Mockito.verify(enterpriseRepository).save(enterpriseArgumentCaptor.capture());
        Enterprise enterpriseUpdated = enterpriseArgumentCaptor.getValue();

        Assertions.assertEquals(secondEnterprise.getName(), enterpriseUpdated.getName());
        Assertions.assertEquals(secondEnterprise.getTaxNumber(), enterpriseUpdated.getTaxNumber());
        Assertions.assertEquals(secondEnterprise.getLocalAddress().getId(), enterpriseUpdated.getAddress().getId());
        Assertions.assertEquals(secondEnterprise.getLocalAddress().getAddress(), enterpriseUpdated.getAddress().getAddress());
        Assertions.assertEquals(secondEnterprise.getLocalAddress().getCity(), enterpriseUpdated.getAddress().getCity());
        Assertions.assertEquals(secondEnterprise.getLocalAddress().getCountry(), enterpriseUpdated.getAddress().getCountry());
        Assertions.assertEquals(secondEnterprise.getLocalAddress().getPostalCode(), enterpriseUpdated.getAddress().getPostalCode());

    }

    @Test
    void GIVEN_enterpriseDtoById_WHEN_findAddressById_THEN_SHOULD_findAddress_from_database() {
        //GIVEN
        Address firstAddress = new Address();
        firstAddress.setAddress("Street Ibn Zohar");
        firstAddress.setCity("Reads");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("");

        Enterprise firstEnterprise = new Enterprise();
        firstEnterprise.setName("Fasten-Yt");
        firstEnterprise.setTaxNumber("FT4531-RT");
        firstEnterprise.setAddress(firstAddress);

        Address secondAddress = new Address();
        secondAddress.setAddress("Street 1245");
        secondAddress.setCity("Toulouse");
        secondAddress.setCountry("FRANCE");
        secondAddress.setPostalCode("123Ft-PL");

        Enterprise secondEnterprise = new Enterprise();
        secondEnterprise.setName("YT-76POL");
        secondEnterprise.setTaxNumber("PLO-7890");
        secondEnterprise.setAddress(secondAddress);

        List<Enterprise> enterprises = new ArrayList<Enterprise>() {
            {
                add(firstEnterprise);
                add(secondEnterprise);
            }
        };
        Mockito.when(enterpriseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstEnterprise));
        //WHEN
        EnterpriseDto enterpriseDto = enterpriseService.findEnterpriseById(1L);
        //THEN
        Assertions.assertEquals(firstEnterprise.getName(), enterpriseDto.getName());
        Assertions.assertEquals(firstEnterprise.getTaxNumber(), enterpriseDto.getTaxNumber());
        Assertions.assertEquals(firstEnterprise.getAddress().getId(), enterpriseDto.getLocalAddress().getId());
        Assertions.assertEquals(firstEnterprise.getAddress().getAddress(), enterpriseDto.getLocalAddress().getAddress());
        Assertions.assertEquals(firstEnterprise.getAddress().getCity(), enterpriseDto.getLocalAddress().getCity());
        Assertions.assertEquals(firstEnterprise.getAddress().getCountry(), enterpriseDto.getLocalAddress().getCountry());
        Assertions.assertEquals(firstEnterprise.getAddress().getPostalCode(), enterpriseDto.getLocalAddress().getPostalCode());

    }
}




