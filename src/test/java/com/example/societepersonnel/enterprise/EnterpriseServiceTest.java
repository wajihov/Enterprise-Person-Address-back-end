package com.example.societepersonnel.enterprise;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.enterprise.EnterpriseMapper;
import com.example.societepersonnel.domain.enterprise.EnterpriseRepository;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import lombok.var;
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
    @MockBean
    private EnterpriseMapper enterpriseMapper;
    @MockBean
    private AddressService addressService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private EnterpriseService enterpriseService;
    @Captor
    private ArgumentCaptor<Enterprise> enterpriseArgumentCaptor;

    @Test
    void GIVEN_EnterpriseDto_WHEN_CreateEnterprise_THEN_Should_save_on_database() {
        //GIVEN
        var addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setAddress("Street Med 6");
        addressDto.setCity("Tunis");
        addressDto.setCountry("Tunisia");
        addressDto.setPostalCode("3452");

        var enterpriseDto = new EnterpriseDto();
        enterpriseDto.setId(1L);
        enterpriseDto.setName("Heatmap-Yu");
        enterpriseDto.setTaxNumber("TRP_678-54");
        enterpriseDto.setLocalAddress(addressDto);

        var enterprise = new Enterprise();
        enterprise.setId(enterpriseDto.getId());
        enterprise.setName(enterpriseDto.getName());
        enterprise.setTaxNumber(enterpriseDto.getTaxNumber());
        enterprise.setAddress(addressMapper.toEntity(enterpriseDto.getLocalAddress()));


        Mockito.when(enterpriseMapper.toEntity(enterpriseDto, addressDto)).thenReturn(enterprise);
        Mockito.when(enterpriseRepository.save(enterprise)).thenReturn(enterprise);
        Mockito.when(enterpriseMapper.toDto(enterprise)).thenReturn(enterpriseDto);
        //WHEN
        enterpriseDto = enterpriseService.createEnterprise(enterpriseDto);
        //THEN
        Mockito.verify(enterpriseRepository).save(enterpriseArgumentCaptor.capture());
        var enterpriseSaved = enterpriseArgumentCaptor.getValue();
        Assertions.assertEquals(enterpriseDto.getId(), enterpriseSaved.getId());
        Assertions.assertEquals(enterpriseDto.getName(), enterpriseSaved.getName());
        Assertions.assertEquals(enterpriseDto.getTaxNumber(), enterpriseSaved.getTaxNumber());

        Assertions.assertEquals(enterpriseDto.getLocalAddress().getId(), enterpriseSaved.getAddress().getId());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getAddress(), enterpriseSaved.getAddress().getAddress());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getCity(), enterpriseSaved.getAddress().getCity());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getCountry(), enterpriseSaved.getAddress().getCountry());
        Assertions.assertEquals(enterpriseDto.getLocalAddress().getPostalCode(), enterpriseSaved.getAddress().getPostalCode());
    }

    @Test
    void GIVEN_Id_Enterprise_WHEN_deleteEnterpriseById_tHEN_SHOULD_delete_from_database() {
        //GIVEN
        var address = new Address();
        address.setId(2L);
        address.setAddress("Street Ibn Zohar");
        address.setCity("Reads");
        address.setCountry("Tunisia");
        address.setPostalCode("");

        var enterprise = new Enterprise();
        enterprise.setId(1L);
        enterprise.setName("Fasten-Yt");
        enterprise.setTaxNumber("FT4531-RT");
        enterprise.setAddress(address);
        Mockito.when(enterpriseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(enterprise));

        //WHEN
        var enterpriseId = 1L;
        enterpriseService.deleteEnterprise(enterpriseId);
        //THEN
        Mockito.verify(enterpriseRepository).delete(enterpriseArgumentCaptor.capture());
        var enterpriseDeleted = enterpriseArgumentCaptor.getValue();
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
        var firstAddressDto = new AddressDto();
        firstAddressDto.setId(4L);
        firstAddressDto.setAddress("Street Ibn Zohar");
        firstAddressDto.setCity("Reads");
        firstAddressDto.setCountry("Tunisia");
        firstAddressDto.setPostalCode(null);

        var firstEnterpriseDto = new EnterpriseDto();
        firstEnterpriseDto.setId(5L);
        firstEnterpriseDto.setName("Fasten-Yt");
        firstEnterpriseDto.setTaxNumber("FT4531-RT");
        firstEnterpriseDto.setLocalAddress(firstAddressDto);

        var firstEnterprise = new Enterprise();
        firstEnterprise.setId(firstEnterpriseDto.getId());
        firstEnterprise.setName(firstEnterpriseDto.getName());
        firstEnterprise.setTaxNumber(firstEnterpriseDto.getTaxNumber());
        firstEnterprise.setAddress(addressMapper.toEntity(firstEnterpriseDto.getLocalAddress()));

        var secondAddressDto = new AddressDto();
        secondAddressDto.setId(6L);
        secondAddressDto.setAddress("Street 1245");
        secondAddressDto.setCity("Toulouse");
        secondAddressDto.setCountry("FRANCE");
        secondAddressDto.setPostalCode("123Ft-PL");

        var secondEnterpriseDto = new EnterpriseDto();
        secondEnterpriseDto.setId(7L);
        secondEnterpriseDto.setName("YT-76POL");
        secondEnterpriseDto.setTaxNumber("PLO-7890");
        secondEnterpriseDto.setLocalAddress(secondAddressDto);

        var secondEnterprise = new Enterprise();
        secondEnterprise.setId(secondEnterpriseDto.getId());
        secondEnterprise.setName(secondEnterpriseDto.getName());
        secondEnterprise.setTaxNumber(secondEnterpriseDto.getTaxNumber());
        secondEnterprise.setAddress(addressMapper.toEntity(secondEnterpriseDto.getLocalAddress()));

        List<Enterprise> enterprises = new ArrayList<Enterprise>() {
            {
                add(firstEnterprise);
                add(secondEnterprise);
            }
        };

        List<EnterpriseDto> enterpriseDtoList = new ArrayList<EnterpriseDto>() {
            {
                add(firstEnterpriseDto);
                add(secondEnterpriseDto);
            }
        };
        Mockito.when(enterpriseRepository.findAll()).thenReturn(enterprises);
        Mockito.when(enterpriseMapper.toDtos(enterprises)).thenReturn(enterpriseDtoList);
        //WHEN
        List<EnterpriseDto> enterpriseList = enterpriseService.findEnterprises();
        //THEN
        Assertions.assertEquals(2, enterpriseList.size());
        Assertions.assertEquals(enterpriseDtoList.size(), enterprises.size());
        for (int i = 0; i < enterpriseDtoList.size(); i++) {
            Assertions.assertEquals(enterpriseDtoList.get(i).getId(), enterpriseList.get(i).getId());
            Assertions.assertEquals(enterpriseDtoList.get(i).getName(), enterpriseList.get(i).getName());
            Assertions.assertEquals(enterpriseDtoList.get(i).getTaxNumber(), enterpriseList.get(i).getTaxNumber());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getId(), enterpriseList.get(i).getLocalAddress().getId());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getAddress(), enterpriseList.get(i).getLocalAddress().getAddress());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getCity(), enterpriseList.get(i).getLocalAddress().getCity());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getCountry(), enterpriseList.get(i).getLocalAddress().getCountry());
            Assertions.assertEquals(enterpriseDtoList.get(i).getLocalAddress().getPostalCode(), enterpriseList.get(i).getLocalAddress().getPostalCode());

        }
    }

    @Test
    void GIVEN_Enterprise_WHEN_updateEnterprise_THEN_Should_update_that_Enterprise_on_Database() {
        //GIVEN
        var firstAddressDto = new AddressDto();
        firstAddressDto.setId(1L);
        firstAddressDto.setAddress("Street Ibn Zohar");
        firstAddressDto.setCity("Reads");
        firstAddressDto.setCountry("Tunisia");
        firstAddressDto.setPostalCode("1235");

        var firstEnterpriseDto = new EnterpriseDto();
        firstEnterpriseDto.setId(2L);
        firstEnterpriseDto.setName("Fasten-Yt");
        firstEnterpriseDto.setTaxNumber("FT4531-RT");
        firstEnterpriseDto.setLocalAddress(firstAddressDto);

        var secondAddressDto = new AddressDto();
        secondAddressDto.setId(2L);
        secondAddressDto.setAddress("Street 1245");
        secondAddressDto.setCity("Toulouse");
        secondAddressDto.setCountry("FRANCE");
        secondAddressDto.setPostalCode("123Ft-PL");

        var secondEnterpriseDto = new EnterpriseDto();
        secondEnterpriseDto.setId(3L);
        secondEnterpriseDto.setName("YT-76POL");
        secondEnterpriseDto.setTaxNumber("PLO-7890");
        secondEnterpriseDto.setLocalAddress(secondAddressDto);

        var secondEnterprise = new Enterprise();
        secondEnterprise.setId(secondEnterpriseDto.getId());
        secondEnterprise.setName(secondEnterpriseDto.getName());
        secondEnterprise.setTaxNumber(secondEnterpriseDto.getTaxNumber());
        secondEnterprise.setAddress(addressMapper.toEntity(secondEnterpriseDto.getLocalAddress()));

        Mockito.when(addressService.updateAddress(Mockito.anyLong(), Mockito.any())).thenReturn(secondAddressDto);
        Mockito.when(enterpriseMapper.toEntity(firstEnterpriseDto, secondAddressDto)).thenReturn(secondEnterprise);
        Mockito.when(enterpriseRepository.save(Mockito.any())).thenReturn(secondEnterprise);
        Mockito.when(enterpriseMapper.toDto(secondEnterprise)).thenReturn(secondEnterpriseDto);
        var enterpriseId = 3L;
        //WHEN
        enterpriseService.updateEnterprise(enterpriseId, firstEnterpriseDto);
        //THEN
        Mockito.verify(enterpriseRepository).save(enterpriseArgumentCaptor.capture());
        var enterpriseUpdated = enterpriseArgumentCaptor.getValue();

        Assertions.assertEquals(secondEnterpriseDto.getId(), enterpriseUpdated.getId());
        Assertions.assertEquals(secondEnterpriseDto.getName(), enterpriseUpdated.getName());
        Assertions.assertEquals(secondEnterpriseDto.getTaxNumber(), enterpriseUpdated.getTaxNumber());
        Assertions.assertEquals(secondEnterpriseDto.getLocalAddress().getId(), enterpriseUpdated.getAddress().getId());
        Assertions.assertEquals(secondEnterpriseDto.getLocalAddress().getAddress(), enterpriseUpdated.getAddress().getAddress());
        Assertions.assertEquals(secondEnterpriseDto.getLocalAddress().getCity(), enterpriseUpdated.getAddress().getCity());
        Assertions.assertEquals(secondEnterpriseDto.getLocalAddress().getCountry(), enterpriseUpdated.getAddress().getCountry());
        Assertions.assertEquals(secondEnterpriseDto.getLocalAddress().getPostalCode(), enterpriseUpdated.getAddress().getPostalCode());
    }

    @Test
    void GIVEN_Enterprise_WHEN_updateEnterprise_THEN_Should_Return_RuntimeException() {
        //GIVEN & WHEN
        var firstAddressDto = new AddressDto();
        firstAddressDto.setId(null);

        var firstEnterpriseDto = new EnterpriseDto();
        firstEnterpriseDto.setId(2L);
        firstEnterpriseDto.setLocalAddress(firstAddressDto);

        //WHEN && THEN
        RuntimeException e = Assertions.assertThrows(RuntimeException.class, () -> {
            enterpriseService.updateEnterprise(firstEnterpriseDto.getId(), firstEnterpriseDto);
        });
        Assertions.assertEquals("ADDRESS NOT VALID", e.getMessage());
    }

    @Test
    void GIVEN_enterpriseDtoById_WHEN_findAddressById_THEN_SHOULD_findAddress_from_database() {
        //GIVEN
        var firstAddress = new Address();
        firstAddress.setId(9L);
        firstAddress.setAddress("Street Ibn Zohar");
        firstAddress.setCity("Reads");
        firstAddress.setCountry("Tunisia");
        firstAddress.setPostalCode("");

        var firstEnterprise = new Enterprise();
        firstEnterprise.setId(8L);
        firstEnterprise.setName("Fasten-Yt");
        firstEnterprise.setTaxNumber("FT4531-RT");
        firstEnterprise.setAddress(firstAddress);

        var firstEnterpriseDto = new EnterpriseDto();
        firstEnterpriseDto.setId(firstEnterprise.getId());
        firstEnterpriseDto.setName(firstEnterprise.getName());
        firstEnterpriseDto.setTaxNumber(firstEnterprise.getTaxNumber());
        firstEnterpriseDto.setLocalAddress(addressMapper.toDto(firstEnterprise.getAddress()));

        var secondAddress = new Address();
        secondAddress.setAddress("Street 1245");
        secondAddress.setCity("Toulouse");
        secondAddress.setCountry("FRANCE");
        secondAddress.setPostalCode("123Ft-PL");

        var secondEnterprise = new Enterprise();
        secondEnterprise.setName("YT-76POL");
        secondEnterprise.setTaxNumber("PLO-7890");
        secondEnterprise.setAddress(secondAddress);

        List<Enterprise> enterprises = new ArrayList<Enterprise>() {
            {
                add(firstEnterprise);
                add(secondEnterprise);
            }
        };
        Mockito.when(enterpriseMapper.toEntity(firstEnterpriseDto)).thenReturn(firstEnterprise);
        Mockito.when(enterpriseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(firstEnterprise));
        Mockito.when(enterpriseMapper.toDto(firstEnterprise)).thenReturn(firstEnterpriseDto);
        //WHEN
        var enterpriseId = 8L;
        var enterpriseDto = enterpriseService.findEnterpriseById(enterpriseId);
        //THEN
        Assertions.assertEquals(firstEnterprise.getId(), enterpriseDto.getId());
        Assertions.assertEquals(firstEnterprise.getName(), enterpriseDto.getName());
        Assertions.assertEquals(firstEnterprise.getTaxNumber(), enterpriseDto.getTaxNumber());
        Assertions.assertEquals(firstEnterprise.getAddress().getId(), enterpriseDto.getLocalAddress().getId());
        Assertions.assertEquals(firstEnterprise.getAddress().getAddress(), enterpriseDto.getLocalAddress().getAddress());
        Assertions.assertEquals(firstEnterprise.getAddress().getCity(), enterpriseDto.getLocalAddress().getCity());
        Assertions.assertEquals(firstEnterprise.getAddress().getCountry(), enterpriseDto.getLocalAddress().getCountry());
        Assertions.assertEquals(firstEnterprise.getAddress().getPostalCode(), enterpriseDto.getLocalAddress().getPostalCode());
    }
}




