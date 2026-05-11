package com.aquachiloe.alimentacion.repository;

import com.aquachiloe.alimentacion.model.Alimentacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentacionRepository extends JpaRepository<Alimentacion, Long> {
}
