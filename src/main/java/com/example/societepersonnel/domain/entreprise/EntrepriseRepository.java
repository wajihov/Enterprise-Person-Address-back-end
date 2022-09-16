package com.example.societepersonnel.domain.entreprise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseRepository extends JpaRepository<Enterprise, Long> {
}
