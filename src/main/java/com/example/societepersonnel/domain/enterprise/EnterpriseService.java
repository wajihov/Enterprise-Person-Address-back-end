package com.example.societepersonnel.domain.enterprise;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.StringUtils;
import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Slf4j
public class EnterpriseService {

    private final EnterpriseMapper enterpriseMapper;
    private final EnterpriseRepository enterpriseRepository;
    private final AddressMapper addressMapper;
    private final AddressService addressService;

    public EnterpriseService(EnterpriseMapper enterpriseMapper, EnterpriseRepository enterpriseRepository, AddressMapper addressMapper,
                             AddressService addressService) {
        this.enterpriseMapper = enterpriseMapper;
        this.enterpriseRepository = enterpriseRepository;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
    }

    private Address searchEnterpriseAddress(Long id_address) {
        AddressDto addressDto = addressService.findAddressById(id_address);
        Address address = addressMapper.toEntity(addressDto);
        return address;
    }

    private Enterprise searchEnterpriseById(Long id) {
        return enterpriseRepository.findById(id).orElseThrow(()
                -> new EnterprisePersonException(Codes.ERR_ENTREPRESE_NOT_FOUND));
    }

    public EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto) {

        AddressDto addressDto = enterpriseDto.getLocalAddress();
        addressDto = addressService.createAddress(addressDto);
        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto, addressDto);
        //Address address = addressMapper.toEntity(addressDto);
        //enterprise.setAddress(address);

        enterprise = enterpriseRepository.save(enterprise);
        log.info("the addition of the company {}", enterpriseDto.getName());
        return enterpriseMapper.toDto(enterprise);
    }

    public List<EnterpriseDto> findEnterprises() {
        List<Enterprise> enterprises = enterpriseRepository.findAll();
        log.info("list the companies {}", enterprises.size());
        return enterpriseMapper.toDtos(enterprises);
    }

    public EnterpriseDto findEnterpriseById(Long id) {
        Enterprise enterprise = searchEnterpriseById(id);
        log.info("The company searched with id {}", id);
        return enterpriseMapper.toDto(enterprise);
    }

    public EnterpriseDto updateEnterprise(Long id, EnterpriseDto enterpriseDto) {

        AddressDto addressDto = enterpriseDto.getLocalAddress();
        if (StringUtils.isNotNullOrNotEmpty(addressDto.getId())) {
            addressDto = addressService.updateAddress(addressDto.getId(), enterpriseDto.getLocalAddress());
            Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto, addressDto);
            enterprise.setId(id);
            enterprise = enterpriseRepository.save(enterprise);
            //log.info("The company is successfully modified {}", enterprise.getName());
            EnterpriseDto dto = enterpriseMapper.toDto(enterprise);
            return dto;
        } else
            throw new EnterprisePersonException(Codes.ERR_ADRESS_NOT_VAlID);
    }

    public void deleteEnterprise(Long id) {
        enterpriseRepository.deleteById(id);
        log.info("The company is successfully deleted with the id {}", id);
    }

}
