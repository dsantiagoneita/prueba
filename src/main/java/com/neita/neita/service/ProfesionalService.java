package com.neita.neita.service;

import com.neita.neita.entity.Profesional;
import java.util.List;
import java.util.Optional;

public interface ProfesionalService {
	List<Profesional> findAll();

	Optional<Profesional> findById(Integer id);

	Profesional save(Profesional profesional);

	Profesional update(Integer id, Profesional profesionalDetails);

	void deleteById(Integer id);
}