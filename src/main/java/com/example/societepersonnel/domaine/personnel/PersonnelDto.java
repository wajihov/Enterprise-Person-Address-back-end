package com.example.societepersonnel.domaine.personnel;

import com.example.societepersonnel.domaine.adresse.AdresseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonnelDto {

    private Long id;
    private String name;
    private String lastName;
    private Post post;

    private AdresseDto adresse;
    private Long enterpriseId;
}
