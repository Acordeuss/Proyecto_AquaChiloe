package com.example.ms_biomasa.repository;

import com.example.ms_biomasa.model.Biomasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BiomasaRepository extends JpaRepository<Biomasa, Long> {
    Optional<Biomasa> findFirstByJaulaIdOrderByIdDesc(Long jaulaId);
}