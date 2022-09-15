package com.example.societepersonnel.domaine.personnel;

import com.example.societepersonnel.domaine.adresse.Adresse;
import com.example.societepersonnel.domaine.societe.Enterprise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Personnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private Post post;

    @OneToOne
    private Adresse adresse;
    @ManyToOne
    private Enterprise enterprise;

}
