package com.example.societepersonnel.domain.personnel;

import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.domain.entreprise.Enterprise;
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
    @Enumerated(EnumType.STRING)
    private Post post;

    @OneToOne
    private Adresse adresse;
    @ManyToOne
    private Enterprise enterprise;

}
