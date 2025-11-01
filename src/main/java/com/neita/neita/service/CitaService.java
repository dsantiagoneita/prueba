package com.neita.neita.service;

import com.neita.neita.entity.Cita;
import java.util.List;
import java.util.Optional;

public interface CitaService {
	List<Cita> findAll();

	Optional<Cita> findById(Integer id);

	Cita save(Cita cita);

	Cita update(Integer id, Cita citaDetails);

	void deleteById(Integer id);
}