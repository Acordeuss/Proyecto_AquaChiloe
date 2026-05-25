package com.example.ms_ambiental.repository;

import com.example.ms_ambiental.model.LecturaAmbiental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AmbientalRepository extends JpaRepository<LecturaAmbiental, Long> {
    List<LecturaAmbiental> findByCentroIdOrderByFechaLecturaDesc(Long centroId);
}