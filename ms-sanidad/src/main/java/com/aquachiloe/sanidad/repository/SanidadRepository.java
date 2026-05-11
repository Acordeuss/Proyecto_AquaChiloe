package com.aquachiloe.sanidad.repository;

import com.aquachiloe.sanidad.model.Sanidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SanidadRepository extends JpaRepository<Sanidad, Long> {
    // Busca todos los tratamientos de una jaula para verificar carencias
    List<Sanidad> findByJaulaId(Long jaulaId);
}
