package com.example.societepersonnel.domain.person;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.StringUtils;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.enterprise.EnterpriseMapper;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.dto.AddressDto;
import com.example.societepersonnel.dto.EnterpriseDto;
import com.example.societepersonnel.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final AddressService addressService;
    private final EnterpriseMapper enterpriseMapper;
    private final EnterpriseService enterpriseService;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, AddressService addressService, EnterpriseMapper enterpriseMapper, EnterpriseService enterpriseService) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.addressService = addressService;
        this.enterpriseMapper = enterpriseMapper;
        this.enterpriseService = enterpriseService;
    }

    private Enterprise findEnterpriseWithId(Long id) {
        EnterpriseDto enterpriseDto = enterpriseService.findEnterpriseById(id);
        Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto);
        return enterprise;
    }

    private Person searchPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(()
                -> new EnterprisePersonException(Codes.ERR_PERSONNEL_NOT_FOUND));
    }

    public PersonDto createPerson(PersonDto personDto) {
        AddressDto addressDto = personDto.getLocalAddress();
        addressDto = addressService.createAddress(addressDto);

        Long enterprise_id = personDto.getEnterpriseId();
        Enterprise enterprise = findEnterpriseWithId(enterprise_id);

        Person person = personMapper.toEntity(personDto, addressDto);
        person.setEnterprise(enterprise);
        person = personRepository.save(person);
        log.info("Adding a person {}", personDto.getName());
        return personMapper.toDto(person);
    }

    public PersonDto findPersonById(Long id) {
        Person person = searchPersonById(id);
        log.info("Searching for staff with the id {} ", id);
        return personMapper.toDto(person);
    }

    public List<PersonDto> findPersons() {
        List<Person> persons = personRepository.findAll();
        log.info("the list of staff is {}", persons.size());
        return personMapper.toDtos(persons);
    }

    public PersonDto updatePerson(Long id, PersonDto personDto) {
        AddressDto addressDto = personDto.getLocalAddress();
        if (StringUtils.isNotNullOrNotEmpty(addressDto.getId())) {
            addressDto = addressService.updateAddress(addressDto.getId(), addressDto);
            Long enterprise_id = personDto.getEnterpriseId();
            EnterpriseDto enterpriseDto = enterpriseService.findEnterpriseById(enterprise_id);
            Enterprise enterprise = enterpriseMapper.toEntity(enterpriseDto);

            Person person = personMapper.toEntity(personDto, addressDto);
            person.setId(id);
            person.setEnterprise(enterprise);
            person = personRepository.save(person);
            log.info("the person {} is successfully modified", person.getName());
            return personMapper.toDto(person);
        } else throw new EnterprisePersonException(Codes.ERR_ADRESS_NOT_VAlID);

    }

    public void deletePerson(Long id) {
        Person person = searchPersonById(id);
        personRepository.delete(person);
        log.info("deletion of staff {} is done successfully ", id);
    }

}
