package com.example.societepersonnel.domain.personal;

import com.example.societepersonnel.core.exception.EnterprisePersonalException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.address.AddressMapper;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.enterprise.EnterpriseMapper;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import com.example.societepersonnel.dto.PersonalDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Slf4j
public class PersonalService {

    private final PersonalRepository personalRepository;
    private final PersonalMapper personalMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final EnterpriseMapper enterpriseMapper;
    private final EnterpriseService enterpriseService;

    public PersonalService(PersonalRepository personalRepository, PersonalMapper personalMapper, AddressMapper addressMapper, AddressService addressService, EnterpriseMapper enterpriseMapper, EnterpriseService enterpriseService) {
        this.personalRepository = personalRepository;
        this.personalMapper = personalMapper;
        this.addressMapper = addressMapper;
        this.addressService = addressService;
        this.enterpriseMapper = enterpriseMapper;
        this.enterpriseService = enterpriseService;
    }

    private Address addAddress(Long id) {
        AddressDto addressDto = addressService.findAddressById(id);
        Address address = addressMapper.toEntity(addressDto);
        return address;
    }

    private Enterprise addEnterprise(Long id) {
        EnterpriseDto enterpriseDto = enterpriseService.findEnterpriseById(id);
        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto);
        return enterprise;
    }

    private Personal addAddressEnterpriseToPersonal(Address address, Enterprise enterprise) {
        Personal personal = new Personal();

        return personal;
    }

    private Personal searchPersonalById(Long id) {
        return personalRepository.findById(id).orElseThrow(()
                -> new EnterprisePersonalException(Codes.ERR_PERSONNEL_NOT_FOUND));
    }

    public PersonalDto createPersonal(PersonalDto personalDto) {
        Long address_id = personalDto.getAddressId();
        Long enterprise_id = personalDto.getEnterpriseId();
        Address address = addAddress(address_id);
        Enterprise enterprise = addEnterprise(enterprise_id);
        Personal personal = personalMapper.toEntity(personalDto);
        personal.setAddress(address);
        personal.setEnterprise(enterprise);
        personal = personalRepository.save(personal);
        log.info("Adding a person {}", personalDto.getName());
        return personalMapper.toDto(personal);
    }

    public PersonalDto findPersonById(Long id) {
        Personal personal = searchPersonalById(id);
        log.info("Searching for staff with the id {} ", id);
        return personalMapper.toDto(personal);
    }

    public List<PersonalDto> findPersonals() {
        List<Personal> personals = personalRepository.findAll();
        log.info("the list of staff is {}", personals.size());
        return personalMapper.toDtos(personals);
    }

    public PersonalDto updatePersonal(Long id, PersonalDto personalDto) {
        Long address_id = personalDto.getAddressId();
        AddressDto addressDto = addressService.findAddressById(address_id);
        Address address = addressMapper.toEntity(addressDto);

        Long enterprise_id = personalDto.getEnterpriseId();
        EnterpriseDto enterpriseDto = enterpriseService.findEnterpriseById(enterprise_id);
        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto);

        Personal personal = personalMapper.toEntity(personalDto);
        personal.setId(id);
        personal.setAddress(address);
        personal.setEnterprise(enterprise);

        personal = personalRepository.save(personal);
        log.info("the person {} is successfully modified", personal.getName());
        return personalMapper.toDto(personal);
    }

    public void deleteEnterprise(Long id) {
        personalRepository.deleteById(id);
        log.info("deletion of staff {} is done successfully ", id);
    }

}
