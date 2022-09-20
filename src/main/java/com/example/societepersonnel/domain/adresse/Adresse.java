package com.example.societepersonnel.domain.adresse;

import com.example.societepersonnel.domain.entreprise.Entreprise;
import com.example.societepersonnel.domain.personnel.Personnel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Adresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String adresse;
    private String ville;
    private String pays;
    private String codePostal;

    @OneToOne(mappedBy = "adresse", cascade = CascadeType.ALL)
    private Personnel personnel;
    //@OneToOne(mappedBy = "adresse")
    @OneToOne(mappedBy = "adresse", cascade = CascadeType.ALL)
    private Entreprise entreprise;
}
