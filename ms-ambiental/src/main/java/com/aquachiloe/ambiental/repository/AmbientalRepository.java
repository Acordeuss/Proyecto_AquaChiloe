package com.aquachiloe.ambiental.repository;

import com.aquachiloe.ambiental.model.LecturaAmbiental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AmbientalRepository extends JpaRepository<LecturaAmbiental, Long> {
    List<com.aquachiloe.ambiental.model.LecturaAmbiental> findByCentroIdOrderByFechaLecturaDesc(Long centroId);
}
