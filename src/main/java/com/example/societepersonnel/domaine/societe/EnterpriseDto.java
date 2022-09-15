package com.example.societepersonnel.domaine.societe;

import com.example.societepersonnel.domaine.adresse.AdresseDto;
import com.example.societepersonnel.domaine.personnel.PersonnelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnterpriseDto {

    private Long id;
    private String name;
    private String numFiscale;

    private AdresseDto adresse;
    private List<PersonnelDto> personnels;
}
