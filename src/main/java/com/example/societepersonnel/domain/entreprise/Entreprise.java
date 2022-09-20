package com.example.societepersonnel.domain.entreprise;

import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.domain.personnel.Personnel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entreprise {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String numFiscale;

    @OneToOne
    private Adresse adresse;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "entreprise", fetch = FetchType.LAZY)
    private List<Personnel> personnels;
}
