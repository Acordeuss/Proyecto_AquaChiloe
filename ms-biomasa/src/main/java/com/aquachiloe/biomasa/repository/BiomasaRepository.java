package com.aquachiloe.biomasa.repository;

import com.aquachiloe.biomasa.model.Biomasa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BiomasaRepository extends JpaRepository<Biomasa, Long> {
    // Busca el último registro para la jaula usando la nomenclatura corregida
    Optional<Biomasa> findFirstByIdJaulaOrderByIdRegistroBiomasaDesc(Long idJaula);
}
