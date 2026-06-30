package com.example.ms_alimentacion.repository;

import com.example.ms_alimentacion.model.Alimentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
}
