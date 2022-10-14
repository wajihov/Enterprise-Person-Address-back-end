package com.example.societepersonnel.domain.person;

import com.example.societepersonnel.domain.address.Address;
import com.example.societepersonnel.domain.enterprise.Enterprise;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Post post;

    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enterprise_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Enterprise enterprise;

}
