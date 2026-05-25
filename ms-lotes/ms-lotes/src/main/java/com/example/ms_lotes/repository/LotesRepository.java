package com.example.ms_lotes.repository;

import com.example.ms_lotes.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LotesRepository extends JpaRepository<Lote, Long> {
    Optional<Lote> findFirstByJaulaIdOrderByIdDesc(Long jaulaId);
}