package com.neita.neita.service;

import com.neita.neita.entity.Servicio;
import java.util.List;
import java.util.Optional;

public interface ServicioService {
	List<Servicio> findAll();

	Optional<Servicio> findById(Integer id);

	Servicio save(Servicio servicio);

	Servicio update(Integer id, Servicio servicioDetails);

	void deleteById(Integer id);
}