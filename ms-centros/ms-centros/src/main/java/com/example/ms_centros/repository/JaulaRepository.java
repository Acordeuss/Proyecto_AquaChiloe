package com.example.ms_centros.repository;

import com.example.ms_centros.model.Jaula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JaulaRepository extends JpaRepository<Jaula, Long> {
}
