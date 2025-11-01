package com.neita.neita.repository;

import com.neita.neita.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
	// Ejemplo personalizado: citas por fecha
	List<Cita> findByFechaHoraAfter(LocalDateTime fecha);

	List<Cita> findByProfesionalIdAndFechaHora(Integer profesionalId, LocalDateTime fechaHora);
}