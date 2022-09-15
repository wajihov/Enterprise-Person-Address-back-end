package com.example.societepersonnel.domaine.adresse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdresseDto {

    private Long id;
    private String adresse;
    private String ville;
    private String pays;
    private String codePostal;
}
