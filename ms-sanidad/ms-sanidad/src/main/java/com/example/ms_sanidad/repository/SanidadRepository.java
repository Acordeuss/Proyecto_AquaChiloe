package com.example.ms_sanidad.repository;

import com.example.ms_sanidad.model.Sanidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SanidadRepository extends JpaRepository<Sanidad, Long> {
    List<Sanidad> findByJaulaId(Long jaulaId);
}