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
public class Enterprise {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String numFiscale;

    @OneToOne
    private Adresse adresse;
    //@JsonProperty()
    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private List<Personnel> personnels;
}
