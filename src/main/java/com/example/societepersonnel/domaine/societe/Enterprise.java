package com.example.societepersonnel.domaine.societe;

import com.example.societepersonnel.domaine.adresse.Adresse;
import com.example.societepersonnel.domaine.personnel.Personnel;
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
    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private List<Personnel> personnels;
}
