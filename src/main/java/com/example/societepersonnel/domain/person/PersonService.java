package com.example.societepersonnel.domain.person;

import com.example.societepersonnel.core.exception.EnterprisePersonException;
import com.example.societepersonnel.core.rest.Codes;
import com.example.societepersonnel.core.utils.StringUtils;
import com.example.societepersonnel.domain.address.AddressService;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.enterprise.EnterpriseMapper;
import com.example.societepersonnel.domain.enterprise.EnterpriseService;
import com.example.societepersonnel.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
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
        var enterpriseDto = enterpriseService.findEnterpriseById(id);
        return enterpriseMapper.toEntity(enterpriseDto);
    }

    private Person searchPersonById(Long id) {
        return personRepository.findById(id).orElseThrow(()
                -> new EnterprisePersonException(Codes.ERR_PERSON_NOT_FOUND));
    }

    public PersonDto createPerson(PersonDto personDto) {
        var addressDto = personDto.getLocalAddress();
        var addressPersonSaved = addressService.createAddress(addressDto);

        var enterpriseId = personDto.getEnterpriseId();
        var enterprise = findEnterpriseWithId(enterpriseId);

        var person = personMapper.toEntity(personDto, addressPersonSaved);
        person.setEnterprise(enterprise);
        var personSaved = personRepository.save(person);
        log.info("Adding a person {}", personSaved.getName());
        return personMapper.toDto(personSaved);
    }

    public PersonDto findPersonById(Long id) {
        var person = searchPersonById(id);
        log.info("Searching for staff with the id {} ", id);
        return personMapper.toDto(person);
    }

    public List<PersonDto> findPersons() {
        var persons = personRepository.findAll();
        if (persons.isEmpty()) {
            throw new EnterprisePersonException(Codes.ERR_PERSONS_NOT_FOUND);
        }
        log.info("the list of staff is {}", persons.size());
        return personMapper.toDtoList(persons);
    }

    public PersonDto updatePerson(Long id, PersonDto personDto) {
        var addressDto = personDto.getLocalAddress();
        if (StringUtils.isNotNullOrNotEmpty(addressDto.getId())) {
            addressDto = addressService.updateAddress(addressDto.getId(), addressDto);
            var enterpriseId = personDto.getEnterpriseId();
            var enterpriseDto = enterpriseService.findEnterpriseById(enterpriseId);
            var enterprise = enterpriseMapper.toEntity(enterpriseDto);

            var person = personMapper.toEntity(personDto, addressDto);
            person.setId(id);
            person.setEnterprise(enterprise);
            var personSaved = personRepository.save(person);
            log.info("the person {} is successfully modified", personSaved.getName());
            return personMapper.toDto(personSaved);
        } else throw new EnterprisePersonException(Codes.ERR_ADDRESS_NOT_VAlID);

    }

    public void deletePerson(Long id) {
        var person = searchPersonById(id);
        personRepository.deleteById(id);
        log.info("deletion of staff {} is done successfully ", person.getName());
    }

}
