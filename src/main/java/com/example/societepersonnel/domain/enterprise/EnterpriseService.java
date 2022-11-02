package com.example.societepersonnel.domain.enterprise;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.StringUtils;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.dto.EnterpriseDto;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Slf4j
public class EnterpriseService {

    private final EnterpriseMapper enterpriseMapper;
    private final EnterpriseRepository enterpriseRepository;
    private final AddressService addressService;

    public EnterpriseService(EnterpriseMapper enterpriseMapper, EnterpriseRepository enterpriseRepository, AddressService addressService) {
        this.enterpriseMapper = enterpriseMapper;
        this.enterpriseRepository = enterpriseRepository;
        this.addressService = addressService;
    }

    private Enterprise searchEnterpriseById(Long id) {
        return enterpriseRepository.findById(id).orElseThrow(()
                -> new EnterprisePersonException(Codes.ERR_ENTERPRISE_NOT_FOUND));
    }

    public EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto) {
        var addressDto = enterpriseDto.getLocalAddress();
        var addressEnterpriseSaved = addressService.createAddress(addressDto);
        var enterprise = enterpriseMapper.toEntity(enterpriseDto, addressEnterpriseSaved);
        enterprise = enterpriseRepository.save(enterprise);
        log.info("the addition of the company {}", enterpriseDto.getName());
        return enterpriseMapper.toDto(enterprise);
    }

    public List<EnterpriseDto> findEnterprises() {
        var enterprises = enterpriseRepository.findAll();
        if (enterprises.isEmpty()) {
            throw new EnterprisePersonException(Codes.ERR_ENTERPRISES_NOT_FOUND);
        }
        log.info("list the companies {}", enterprises.size());
        return enterpriseMapper.toDtoList(enterprises);
    }

    public EnterpriseDto findEnterpriseById(Long id) {
        var enterprise = searchEnterpriseById(id);
        log.info("The company searched with id {}", id);
        return enterpriseMapper.toDto(enterprise);
    }

    public EnterpriseDto updateEnterprise(Long id, EnterpriseDto enterpriseDto) {
        var addressDto = enterpriseDto.getLocalAddress();
        if (StringUtils.isNotNullOrNotEmpty(addressDto.getId())) {
            addressDto = addressService.updateAddress(addressDto.getId(), enterpriseDto.getLocalAddress());

            var enterprise = enterpriseMapper.toEntity(enterpriseDto, addressDto);
            enterprise.setId(id);
            var enterpriseSaved = enterpriseRepository.save(enterprise);
            log.info("The company is successfully modified {}", enterpriseSaved.getName());
            return enterpriseMapper.toDto(enterpriseSaved);
        } else
            throw new EnterprisePersonException(Codes.ERR_ADDRESS_NOT_VAlID);
    }

    public void deleteEnterprise(Long id) {
        var enterprise = searchEnterpriseById(id);
        enterpriseRepository.deleteById(id);
        log.info("The company is successfully deleted with name {}", enterprise.getName());
    }

}
