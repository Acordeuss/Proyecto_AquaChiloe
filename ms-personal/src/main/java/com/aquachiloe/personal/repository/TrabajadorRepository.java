package com.aquachiloe.personal.repository;

import com.aquachiloe.personal.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    Optional<Trabajador> findByRut(String rut);
}
