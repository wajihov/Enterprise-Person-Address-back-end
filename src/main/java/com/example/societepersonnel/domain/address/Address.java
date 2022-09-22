package com.example.societepersonnel.domain.address;

import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.example.societepersonnel.domain.personal.Personal;
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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String city;
    private String country;
    private String postalCode;

    @OneToOne(mappedBy = "address")
    private Personal personal;

    @OneToOne(mappedBy = "address")
    private Enterprise enterprise;
}
