package com.example.societepersonnel.domain.personnel;

import com.example.societepersonnel.domain.adresse.Adresse;
import com.example.societepersonnel.domain.entreprise.Entreprise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "adresse_id", nullable = false)
    private Adresse adresse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entreprise_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Entreprise entreprise;

}
